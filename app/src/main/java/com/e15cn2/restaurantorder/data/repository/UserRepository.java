package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.source.UserDataSource;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserRepository implements UserDataSource.Remote {
    private static UserRepository sInstance;
    private UserDataSource.Remote mRemote;

    private UserRepository(UserDataSource.Remote remote) {
        mRemote = remote;
    }

    public static UserRepository getInstance(UserDataSource.Remote remote) {
        if (sInstance == null) {
            sInstance = new UserRepository(remote);
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> signUp(String id, String name, String dob, String email, String phone, String password) {
        return mRemote.signUp(id, name, dob, email, phone, password);
    }

    @Override
    public Call<ResponseBody> signIn(String email, String password,String token) {
        return mRemote.signIn(email, password, token);
    }
}
