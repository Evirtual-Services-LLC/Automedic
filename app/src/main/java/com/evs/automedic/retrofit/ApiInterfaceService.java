package com.evs.automedic.retrofit;


import com.android.volley.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInterfaceService {

    private static final String BASE_URL = "http://demo2.evirtualservices.co/cuckoo/services/index/";

    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS);

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    private static Retrofit retrofit = null;

    private static OkHttpClient.Builder getHttpClient() {
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            okHttpClient.addInterceptor(new LogInterceptor());
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            okHttpClient.addInterceptor(interceptor);
        }
        return okHttpClient;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.addConverterFactory(GsonConverterFactory.create());
            retrofit = builder
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient().build())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
