package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.source.ItemDataSouce;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ItemRepository implements ItemDataSouce.Remote {
    private static ItemRepository sInstance;
    private ItemDataSouce.Remote mRemote;

    private ItemRepository(ItemDataSouce.Remote remote) {
        mRemote = remote;
    }

    public static ItemRepository getInstance(ItemDataSouce.Remote remote) {
        if (sInstance == null) {
            sInstance = new ItemRepository(remote);
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> addItem(String name, String menu, double price, String desc, String imageName, String imageCode) {
        return mRemote.addItem(name, menu, price, desc, imageName, imageCode);
    }

    @Override
    public Call<List<Item>> getItems() {
        return mRemote.getItems();
    }

    @Override
    public Call<ResponseBody> updateItemStatus(int id, int status) {
        return mRemote.updateItemStatus(id,status);
    }
}
