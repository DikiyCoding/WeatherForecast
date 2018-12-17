package example.homework10.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private ApiKeyInterceptor(){}

    public static Interceptor create() {
        return new ApiKeyInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        return null;/*chain.proceed(original.newBuilder().url(getUrl(original)).build());*/
    }
}
