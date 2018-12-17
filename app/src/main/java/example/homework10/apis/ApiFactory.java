package example.homework10.apis;

import android.support.annotation.NonNull;

import example.homework10.apis.open_weather_map.interfaces.WeatherService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {

    private static OkHttpClient client;

    private static volatile WeatherService weatherService;

    private ApiFactory() {
    }

    @NonNull
    public static WeatherService getWeatherService() {
        WeatherService service = weatherService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = weatherService;
                if (service == null) {
                    service = weatherService = buildRetrofit().create(WeatherService.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        client = null;
        client = getClient();
        weatherService = buildRetrofit().create(WeatherService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())                          //RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = ApiFactory.client;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = ApiFactory.client;
                if (client == null) {
                    client = ApiFactory.client = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                /*.addInterceptor(ApiKeyInterceptor.create())
                .addInterceptor(LoggingInterceptor.create())
                .addInterceptor(new StethoInterceptor())*/
                .build();
    }
}
