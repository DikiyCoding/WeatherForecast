package example.homework10.apis.open_weather_map;

import android.os.AsyncTask;
import android.util.Log;

import example.homework10.apis.open_weather_map.pojos.Cities;

/**
 * Класс не используется, чисто для экспериментов ^_^
 */
public class OpenWeatherMapConnection extends AsyncTask<Double, Void, Cities> {

    private final String TAG = "Logs";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "* * * \"OpenWeatherMapConnection\" AsyncTask is starting * * *");
    }

    @Override
    protected Cities doInBackground(Double... coordinates) {
        Log.d(TAG, "* * * \"OpenWeatherMapConnection\" AsyncTask is working * * *");
        return null;
    }

    @Override
    protected void onPostExecute(Cities cities) {
        super.onPostExecute(cities);
        Log.d(TAG, "* * * \"OpenWeatherMapConnection\" AsyncTask is finishing * * *");
    }
}
