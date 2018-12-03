package example.homework10.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import example.homework10.R;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;
import example.homework10.handlers.UnixHandler;

public class DetailActivity extends AppCompatActivity {

    private TextView city, country, datetime, temperature, pressure, humidity, cloudiness, latitude, longitude, windSpeed, windDegrees;
    private CitiesParcelable values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        viewsInit();
        setValues();
    }

    private void viewsInit() {
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        datetime = findViewById(R.id.datetime);
        temperature = findViewById(R.id.temperature);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        cloudiness = findViewById(R.id.cloudiness);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        windSpeed = findViewById(R.id.wind_speed);
        windDegrees = findViewById(R.id.wind_degrees);
    }

    private void setValues () {
        values = getIntent().getParcelableExtra("values");
        city.setText(values.getName());
        country.setText(values.getCountry());
        datetime.setText(UnixHandler.fromUNIXtoReadable(values.getDatetime()));
        temperature.setText(values.getTemperature() + "°С");
        pressure.setText(values.getPressure() + " hpa");
        humidity.setText(values.getHumidity() + "%");
        cloudiness.setText(values.getCloudiness() + "%");
        latitude.setText(values.getLatitude() + "º");
        longitude.setText(values.getLongitude() + "º");
        windSpeed.setText(values.getWindSpeed() + " m/s");
        windDegrees.setText(values.getWindDegrees() + "º");
    }
}
