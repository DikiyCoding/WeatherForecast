package example.homework10.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import example.homework10.R;
import example.homework10.adapters.CityListAdapter;
import example.homework10.apis.ApiURLHelper;
import example.homework10.apis.Connections;
import example.homework10.apis.open_weather_map.pojos.Cities;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;
import example.homework10.apis.open_weather_map.pojos.List;
import example.homework10.permissons.PermissionCallback;
import example.homework10.permissons.PermissionChecker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.homework10.permissons.PermissionChecker.RuntimePermissions.PERMISSION_REQUEST_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements PermissionCallback {

    private final String TAG = "Logs";

    private java.util.List<CitiesParcelable> cities;
    private LocationManager locationManager;
    private CityListAdapter adapter;
    private RecyclerView list;
    private double latitude, longitude;
    private int numberOfCities;
    private boolean isListSetted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Connections();
        cities = new ArrayList<>();
        list = findViewById(R.id.list);
        adapter = new CityListAdapter(this, cities);
        list.setAdapter(adapter);

        numberOfCities = 20;
        isListSetted = false;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCoordinates();
        if(getCoordinates() && !isListSetted) getCities();
        Log.i(TAG, "Latitude is " + latitude);
        Log.i(TAG, "Longitude is " + longitude);
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
    public void permissionGranted(PermissionChecker.RuntimePermissions permission) {
        Log.i(TAG, "Разрешения получены");
    }

    @Override
    public void permissionDenied(PermissionChecker.RuntimePermissions permission) {
        Log.i(TAG, "Разрешения отклонены");
    }

    //TODO Permissions better way
    private void checkPermissions() {
        PermissionChecker permissionChecker = new PermissionChecker();
        permissionChecker.checkForPermissions(this, PERMISSION_REQUEST_FINE_LOCATION, this);
    }

    private boolean getCoordinates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        //TODO solution between Network and GPS
        latitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
        longitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
        return true;
    }

    //TODO Retrofit RX
    //TODO Interceptors
    private void getCities() {
        Call<Cities> call = Connections.openWeatherMapAPI.getData(latitude, longitude, numberOfCities, ApiURLHelper.API_KEY);
        call.enqueue(new Callback<Cities>() {

            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                if(response.code() != 200) {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                if(response.body() != null) {
                    setList(response.body());
                    updateList();
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Log.e(TAG, "Something goes wrong");
            }
        });
    }

    private void setList(Cities cities) {
        for (List list : cities.getList()) {
            this.cities.add(new CitiesParcelable(
                list.getId(),
                list.getDt(),
                list.getMain().getPressure(),
                list.getMain().getHumidity(),
                list.getWind().getSpeed(),
                list.getWind().getDeg(),
                list.getClouds().getAll(),
                list.getMain().getTemp(),
                list.getCoord().getLat(),
                list.getCoord().getLon(),
                list.getName(),
                list.getSys().getCountry())
            );
        }
        isListSetted = true;
    }

    private void updateList() {
        adapter.notifyDataSetChanged();
    }
}
