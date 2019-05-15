package com.e15cn2.restaurantorder.utils.network;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
    private static Retrofit sRetrofit = null;

    private static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (sRetrofit == null)
            return new Retrofit.Builder()
                    .baseUrl(Constants.Network.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        else return sRetrofit;
    }

    public static ApiConfig getApiConfig() {
        return getRetrofit().create(ApiConfig.class);
    }
}
