package com.example.exchangetoys.Services;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoNetworkClient {
    public static final String KEY_API="aec009a699ffe96618efbafe5bc81b17";
    private static final String BASE_URL = "https://api.imgbb.com/1/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
