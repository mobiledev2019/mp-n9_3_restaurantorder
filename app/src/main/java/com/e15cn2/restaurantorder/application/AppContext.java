package com.e15cn2.restaurantorder.application;

import android.app.Application;

public class AppContext extends Application {
    private static AppContext sApplication;

    public static AppContext getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}