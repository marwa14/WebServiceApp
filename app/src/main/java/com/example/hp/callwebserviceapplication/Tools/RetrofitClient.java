package com.example.hp.callwebserviceapplication.Tools;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 09/11/2017.
 */

public class RetrofitClient {

    private static Retrofit mRetrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}