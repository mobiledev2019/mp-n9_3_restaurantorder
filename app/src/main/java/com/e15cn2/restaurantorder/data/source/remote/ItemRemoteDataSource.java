package com.e15cn2.restaurantorder.data.source.remote;

import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.source.ItemDataSouce;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ItemRemoteDataSource implements ItemDataSouce.Remote {
    private static ItemRemoteDataSource sInstance;

    public static ItemRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new ItemRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> addItem(String name, String menu, double price,String desc, String imageName, String imageCode) {
        return addItemAdmin(name, menu, price, desc, imageName, imageCode);
    }

    @Override
    public Call<List<Item>> getItems() {
        return getItemAdmin();
    }

    private Call<ResponseBody> addItemAdmin(String name, String menu, double price, String desc, String imageName, String imageCode) {
        Call<ResponseBody> call = AppConfig.getApiConfig().addItem(name, menu, price, desc, imageName, imageCode);
        return call;
    }

    private Call<List<Item>> getItemAdmin() {
        Call<List<Item>> call = AppConfig.getApiConfig().getItemAdmin();
        return call;
    }
}
