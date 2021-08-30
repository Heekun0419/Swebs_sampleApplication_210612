package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwebsClient {

    private static String baseUrl = "http://3.35.249.81/php/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static OkHttpClient createOkHttpClient() {
        // 네트워크 통신 로그(서버로 보내는 파라미터 및 받는 파라미터) 보기
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }
}

