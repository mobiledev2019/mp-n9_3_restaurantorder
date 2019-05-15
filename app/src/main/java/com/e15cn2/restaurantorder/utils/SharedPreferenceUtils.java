package com.e15cn2.restaurantorder.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.e15cn2.restaurantorder.data.model.User;
import com.google.gson.Gson;

public class SharedPreferenceUtils {
    private static final String PREF_NAME = "PREF_NAME";
    private static final String PREF_SIGN_IN = "PREF_SIGN_IN";
    private static final String PREF_USER = "PREF_USER";
    private static final String PREF_DEVICE_TOKEN = "PREF_DEVICE_TOKEN";
    private static SharedPreferenceUtils sInstance;
    private static SharedPreferences sSharedPreferences;
    private Gson mGson;

    private SharedPreferenceUtils(Context context) {
        sSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    public static synchronized SharedPreferenceUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferenceUtils(context);
        }
        return sInstance;
    }

    public void saveSignInState(boolean isSignIn) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putBoolean(PREF_SIGN_IN, isSignIn);
        editor.apply();
    }

    public void saveUser(User user) {
        String jsonUser = mGson.toJson(user);
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(PREF_USER, jsonUser);
        editor.apply();
    }

    public void saveDeviceToken(String token) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(PREF_DEVICE_TOKEN, token);
        editor.apply();
    }

    public String getDeviceToken() {
        return sSharedPreferences.getString(PREF_DEVICE_TOKEN, null);
    }

    public boolean getSignInState() {
        return sSharedPreferences.getBoolean(PREF_SIGN_IN, false);
    }

    public User getUser() {
        String jsonUser = sSharedPreferences.getString(PREF_USER, null);
        return mGson.fromJson(jsonUser, User.class);
    }
}
