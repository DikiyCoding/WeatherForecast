package example.homework10.apis.open_weather_map.interfaces;

import example.homework10.apis.Constants;
import example.homework10.apis.open_weather_map.pojos.Cities;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET(Constants.CITIES_DATA)
    Observable<Cities> getData(@Query("lat") double latitude, @Query("lon") double longitude, @Query("cnt") int count, @Query("APPID") String KEY);
}