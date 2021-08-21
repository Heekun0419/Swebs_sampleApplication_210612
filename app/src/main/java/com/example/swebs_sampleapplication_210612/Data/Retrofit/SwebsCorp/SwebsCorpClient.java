package com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwebsCorpClient {
    private static String baseUrl = "https://swebs.co.kr/";
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
