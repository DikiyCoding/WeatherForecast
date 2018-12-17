package example.homework10.database;

import android.arch.persistence.room.*;

import java.util.List;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;
import io.reactivex.Flowable;

@Dao
public interface CityWeatherDAO {

    @Query("SELECT * FROM city_weather")
    Flowable<List<CitiesParcelable>> getAll();

    @Query("SELECT * FROM city_weather WHERE id = :id")
    Flowable<CitiesParcelable> getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CitiesParcelable> cities);

    @Update
    void update(CitiesParcelable cities);

    @Delete
    void delete(CitiesParcelable cities);
}
