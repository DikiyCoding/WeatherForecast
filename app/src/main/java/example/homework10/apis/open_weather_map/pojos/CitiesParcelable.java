package example.homework10.apis.open_weather_map.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CitiesParcelable implements Parcelable {

    private final String TAG = "Logs";

    /**
     * Variables
     */

    private final double degreesDifference = 273.15;

    private final int id, datetime, pressure, humidity, windSpeed, windDegrees, cloudiness;
    private double temperature, latitude, longitude;
    private String name, country;
    public static final Creator<CitiesParcelable> CREATOR = new Creator<CitiesParcelable>() {

        @Override
        public CitiesParcelable createFromParcel(Parcel parcel) {
            return new CitiesParcelable(parcel);
        }

        @Override
        public CitiesParcelable[] newArray(int size) {
            return new CitiesParcelable[size];
        }
    };

    /**
     * Constructors
     */

    public CitiesParcelable(int id,
                            int datetime,
                            int pressure,
                            int humidity,
                            int windSpeed,
                            int windDegrees,
                            int cloudiness,
                            double temperature,
                            double latitude,
                            double longitude,
                            String name,
                            String country) {
        this.id = id;
        this.datetime = datetime;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.cloudiness = cloudiness;
        this.temperature = temperature - degreesDifference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.country = country;

        Log.i(TAG, "\n" +
                "ID: " + this.id + "\n" +
                "DateTime: " + this.datetime + "\n" +
                "Pressure: " + this.pressure + "\n" +
                "Humidity: " + this.humidity + "\n" +
                "Wind Speed: " + this.windSpeed + "\n" +
                "Wind Degrees: " + this.windDegrees + "\n" +
                "Cloudiness: " + this.cloudiness + "\n" +
                "Latitude: " + this.latitude + "\n" +
                "Longitude: " + this.longitude + "\n" +
                "Temperature: " + this.temperature + "\n" +
                "City name: " + this.name + "\n" +
                "Country: " + this.country);
    }

    public CitiesParcelable(Parcel parcel) {
        id = parcel.readInt();
        datetime = parcel.readInt();
        pressure = parcel.readInt();
        humidity = parcel.readInt();
        windSpeed = parcel.readInt();
        windDegrees = parcel.readInt();
        cloudiness = parcel.readInt();
        temperature = parcel.readDouble();
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
        name = parcel.readString();
        country = parcel.readString();
    }

    /**
     * Override Methods
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(datetime);
        parcel.writeInt(pressure);
        parcel.writeInt(humidity);
        parcel.writeInt(windSpeed);
        parcel.writeInt(windDegrees);
        parcel.writeInt(cloudiness);
        parcel.writeDouble(temperature);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(name);
        parcel.writeString(country);
    }

    /**
     * Getters
     */

    public int getID() {
        return id;
    }

    public int getDatetime() {
        return datetime;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDegrees() {
        return windDegrees;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}