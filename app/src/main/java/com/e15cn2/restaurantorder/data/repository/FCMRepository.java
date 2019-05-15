package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.source.FCMDataSource;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class FCMRepository implements FCMDataSource.Remote {
    private static FCMRepository sInstance;
    private FCMDataSource.Remote mRemote;

    private FCMRepository(FCMDataSource.Remote remote) {
        mRemote = remote;
    }

    public static FCMRepository getInstance(FCMDataSource.Remote remote) {
        if (sInstance == null) {
            sInstance = new FCMRepository(remote);
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> userPushSmallNotification(String title, String message) {
        return mRemote.userPushSmallNotification(title, message);
    }

    @Override
    public Call<ResponseBody> userPushBigNotification(String title, String message, String image) {
        return mRemote.userPushBigNotification(title, message, image);
    }
}
