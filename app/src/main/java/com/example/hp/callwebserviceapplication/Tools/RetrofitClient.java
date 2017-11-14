package com.example.hp.callwebserviceapplication.Tools;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

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