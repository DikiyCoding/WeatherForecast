package example.homework10.apis;

import example.homework10.apis.open_weather_map.interfaces.OpenWeatherMapApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connections {

    public static OpenWeatherMapApi openWeatherMapAPI;

    public Connections() {
        openWeatherMapAPI = new Retrofit.Builder()
                                        .baseUrl(ApiURLHelper.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                        .create(OpenWeatherMapApi.class);
    }
}
