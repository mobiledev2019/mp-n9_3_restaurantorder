package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.source.MenuDataSource;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class MenuRepository implements MenuDataSource.Remote {
    private static MenuRepository sInstance;
    private MenuDataSource.Remote mRemote;

    private MenuRepository(MenuDataSource.Remote remote) {
        mRemote = remote;
    }

    public static MenuRepository getInstance(MenuDataSource.Remote remote) {
        if (sInstance == null) {
            sInstance = new MenuRepository(remote);
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> addMenu(String name, String imageName, String imageCode) {
        return mRemote.addMenu(name, imageName, imageCode);
    }

    @Override
    public Call<List<Menu>> getMenus() {
        return mRemote.getMenus();
    }
}
