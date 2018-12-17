package example.homework10.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.homework10.R;
import example.homework10.adapters.CityListAdapter;
import example.homework10.adapters.ItemClickCallback;
import example.homework10.apis.ApiFactory;
import example.homework10.apis.Constants;
import example.homework10.apis.open_weather_map.pojos.Cities;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;
import example.homework10.apis.open_weather_map.pojos.CityList;
import example.homework10.application.App;
import example.homework10.permissons.PermissionCallback;
import example.homework10.permissons.PermissionCheckerUtils;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static example.homework10.permissons.PermissionCheckerUtils.RuntimePermissions.PERMISSION_REQUEST_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements PermissionCallback, ItemClickCallback {

    private final String TAG = "Logs";
    private final String timeKey = "current_time";

    private List<CitiesParcelable> cities;
    private LocationManager locationManager;
    private CompletableObserver dbOutputObserver;
    private Observer<Cities> netObserver;
    private CityListAdapter adapter;
    private RecyclerView list;
    private Intent intent;

    private double latitude, longitude;
    private long hour;
    private int numberOfCities;
    private boolean isListSetted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        hour = 3600;
        numberOfCities = 20;

        cities = new ArrayList<>();
        list = findViewById(R.id.list);
        adapter = new CityListAdapter(this,this, cities);
        list.setAdapter(adapter);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkPermissions();

        isRefreshTime();
        setObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getCoordinates() && !isListSetted) getCities();
        Log.i(TAG, "Latitude is " + latitude);
        Log.i(TAG, "Longitude is " + longitude);
    }

    @Override
    public void onItemClick(int position) {
        if (position != RecyclerView.NO_POSITION) {
            intent = new Intent(this, DetailActivity.class);
            intent.putExtra("values", cities.get(position));
            this.startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_FINE_LOCATION.VALUE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted(PERMISSION_REQUEST_FINE_LOCATION);
            } else {
                permissionDenied(PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    @Override
    public void permissionGranted(PermissionCheckerUtils.RuntimePermissions permission) {
        Log.i(TAG, "Разрешения получены");
    }

    @Override
    public void permissionDenied(PermissionCheckerUtils.RuntimePermissions permission) {
        Log.i(TAG, "Разрешения отклонены");
    }

    private void checkPermissions() {
        PermissionCheckerUtils permissionChecker = new PermissionCheckerUtils();
        permissionChecker.checkForPermissions(this, PERMISSION_REQUEST_FINE_LOCATION, this);
    }

    private boolean getCoordinates() {

        Location bestLocation = null;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;

        for (String provider : locationManager.getProviders(true)) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location == null) continue;
            if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) bestLocation = location;
        }

        latitude = bestLocation.getLatitude();
        longitude = bestLocation.getLongitude();

        return true;
    }

    //TODO Interceptors
    private void getCities() {
        ApiFactory.getWeatherService()
                .getData(latitude, longitude, numberOfCities, Constants.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netObserver);
    }

    private void getСache() {
        Log.d(TAG, "Дёргаем инфу из базы данных");
        App.getInstance()
                .getDatabase()
                .cityWeatherDAO()
                .getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(citiesParcelables -> {
                    cities.addAll(citiesParcelables);
                    updateList();
                });
    }

    private void setCache() {
        Log.d(TAG, "Сохраняем инфу в базу данных");
        Completable.fromAction(() -> App.getInstance()
                .getDatabase()
                .cityWeatherDAO()
                .insert(cities))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(dbOutputObserver);
    }

    private boolean checkElapsedTime() {
        if (getSavedTime() == 0)
            return true;
        else
            return (getCurrentTime() - getSavedTime()) >= hour;
    }

    private void isRefreshTime() {
        if (checkElapsedTime()) {
            isListSetted = false;
        } else {
            isListSetted = true;
            getСache();
        }
    }

    private void setSavedTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(timeKey, getCurrentTime());
        editor.apply();
    }

    private long getSavedTime() {
        return getPreferences(MODE_PRIVATE).getLong(timeKey, 0);
    }

    private long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    private void setList(Cities cities) {
        for (CityList city : cities.getList()) {
            this.cities.add(new CitiesParcelable(
                    city.getId(),
                    city.getDt(),
                    city.getMain().getPressure(),
                    city.getMain().getHumidity(),
                    city.getWind().getSpeed(),
                    city.getWind().getDeg(),
                    city.getClouds().getAll(),
                    city.getMain().getTemp(),
                    city.getCoord().getLat(),
                    city.getCoord().getLon(),
                    city.getName(),
                    city.getSys().getCountry())
            );
        }
        isListSetted = true;
    }

    private void updateList() {
        adapter.notifyDataSetChanged();
    }

    private void setObservers() {

        netObserver = new Observer<Cities>() {

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(Cities cities) {
                Log.d(TAG, "Успешно сходили в инет и дёргаем инфу с городов :)");
                setList(cities);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Обновляем рекуклер :)");
                updateList();
                setSavedTime();
                setCache();
            }

            @Override
            public void onError(Throwable exception) {
                Log.e(TAG, "Не получилось сходить в инет :(");
                Log.e(TAG, "Вот почему: " + exception.getMessage());
                getСache();
            }
        };

        dbOutputObserver = new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Успешно сохранили инфу в базу данных :)");
            }

            @Override
            public void onError(Throwable exception) {
                Log.i(TAG, "Не получилось сохранить инфу в базу данных :(");
                Log.i(TAG, "Вот почему: " + exception.getMessage());
            }
        };
    }
}
