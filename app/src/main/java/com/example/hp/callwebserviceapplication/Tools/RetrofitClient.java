package com.example.hp.callwebserviceapplication.Tools;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by HP on 09/11/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}