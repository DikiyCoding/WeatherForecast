package example.homework10.apis.open_weather_map.interfaces;

import example.homework10.apis.ApiURLHelper;
import example.homework10.apis.open_weather_map.pojos.Cities;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET(ApiURLHelper.CITIES_DATA)
    Call<Cities> getData(@Query("lat") double latitude, @Query("lon") double longitude, @Query("cnt") int count, @Query("APPID") String KEY);
}