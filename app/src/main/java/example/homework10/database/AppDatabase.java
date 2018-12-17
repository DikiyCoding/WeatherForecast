package example.homework10.database;

import android.arch.persistence.room.*;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;

@Database(entities = {CitiesParcelable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityWeatherDAO cityWeatherDAO();
}
