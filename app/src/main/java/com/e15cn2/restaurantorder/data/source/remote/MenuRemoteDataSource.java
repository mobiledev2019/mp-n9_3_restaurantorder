package com.e15cn2.restaurantorder.data.source.remote;

import android.util.Log;

import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.source.MenuDataSource;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class MenuRemoteDataSource implements MenuDataSource.Remote {
    private static MenuRemoteDataSource sInstance;

    public static MenuRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new MenuRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> addMenu(String name, String imageName, String imageCode) {
        return addMenuAdmin(name, imageName, imageCode);
    }

    @Override
    public Call<List<Menu>> getMenus() {
        return getMenusAdmin();
    }

    private Call<ResponseBody> addMenuAdmin(String name, String imageName, String imageCode) {
        Call<ResponseBody> call = AppConfig.getApiConfig().addMenu(name, imageName, imageCode);
        return call;
    }

    private Call<List<Menu>> getMenusAdmin(){
        Call<List<Menu>> call = AppConfig.getApiConfig().getMenusAdmin();
        return call;
    }
}
