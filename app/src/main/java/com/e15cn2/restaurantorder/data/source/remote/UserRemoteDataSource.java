package com.e15cn2.restaurantorder.data.source.remote;

import com.e15cn2.restaurantorder.data.source.UserDataSource;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserRemoteDataSource implements UserDataSource.Remote {
    private static UserRemoteDataSource sInstance;

    private UserRemoteDataSource() {
    }

    public static UserRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UserRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> signUp(String id, String name, String dob, String email, String phone, String password) {
        return signUpUser(id, name, dob, email, phone, password);
    }

    @Override
    public Call<ResponseBody> signIn(String email, String password, String token) {
        return signInUser(email, password, token);
    }


    private Call<ResponseBody> signUpUser(String id, String name, String dob, String email, String phone, String password) {
        Call<ResponseBody> call = AppConfig.getApiConfig().signUp(id, name, dob, email, phone, password);
        return call;
    }

    private Call<ResponseBody> signInUser(String email, String password, String token) {
        Call<ResponseBody> call = AppConfig.getApiConfig().signIn(email, password, token);
        return call;
    }
}
