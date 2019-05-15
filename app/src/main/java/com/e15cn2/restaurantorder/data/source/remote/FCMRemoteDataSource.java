package com.e15cn2.restaurantorder.data.source.remote;

import com.e15cn2.restaurantorder.data.source.FCMDataSource;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class FCMRemoteDataSource implements FCMDataSource.Remote {
    private static FCMRemoteDataSource sInstance;

    public static FCMRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new FCMRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> userPushSmallNotification(String title, String message) {
        return AppConfig.getApiConfig().userPushSmallNotification(title, message);
    }

    @Override
    public Call<ResponseBody> userPushBigNotification(String title, String message, String image) {
        return AppConfig.getApiConfig().userPushBigNotification(title, message, image);
    }
}
