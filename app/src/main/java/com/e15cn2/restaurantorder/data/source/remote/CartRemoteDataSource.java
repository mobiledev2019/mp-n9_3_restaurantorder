package com.e15cn2.restaurantorder.data.source.remote;

import com.e15cn2.restaurantorder.data.source.CartDataSource;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class CartRemoteDataSource implements CartDataSource.Remote {
    private static CartRemoteDataSource sInstance;

    public static CartRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new CartRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> uploadCart(long cartId, String userId, String tableNumber, double price) {
        return uploadCartUser(cartId, userId, tableNumber, price);
    }

    @Override
    public Call<ResponseBody> uploadCartItem(int itemId, int quantity, double price, long cartId) {
        return uploadCartItemUser(itemId, quantity, price, cartId);
    }

    private Call<ResponseBody> uploadCartUser(long cartId, String userId, String tableNumber, double price) {
        return AppConfig.getApiConfig().uploadCart(cartId, userId, tableNumber, price);
    }


    private Call<ResponseBody> uploadCartItemUser(int itemId, int quantity, double price, long cartId) {
        return AppConfig.getApiConfig().uploadCartItem(itemId, quantity, price, cartId);
    }
}
