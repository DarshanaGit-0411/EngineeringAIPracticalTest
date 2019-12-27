package com.app.engineeringaipracticaltest.network;


import com.google.gson.GsonBuilder;

import com.app.engineeringaipracticaltest.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {


    private static Retrofit retrofitBaseApi = null;

    public static BaseApiMethods getBaseApiMethods() {
        return createRetrofitBase().create(BaseApiMethods.class);
    }

    private static Retrofit createRetrofitBase() {
        if (retrofitBaseApi == null) {

            retrofitBaseApi = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation().create()))
                .client(getOkHttpClient())
                .build();
        }
        return retrofitBaseApi;
    }

    private static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        if (BuildConfig.DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }

}
