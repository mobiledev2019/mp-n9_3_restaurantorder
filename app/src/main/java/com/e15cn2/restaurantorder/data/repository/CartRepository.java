package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.source.CartDataSource;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class CartRepository implements CartDataSource.Remote {
    private static CartRepository sInstance;
    private CartDataSource.Remote mRemote;

    private CartRepository(CartDataSource.Remote remote) {
        mRemote = remote;
    }

    public static CartRepository getInstance(CartDataSource.Remote remote) {
        if (sInstance == null) {
            sInstance = new CartRepository(remote);
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> uploadCart(long cartId, String userId, String tableNumber, double price) {
        return mRemote.uploadCart(cartId, userId, tableNumber, price);
    }

    @Override
    public Call<ResponseBody> uploadCartItem(int itemId, int quantity, double price, long cartId) {
        return mRemote.uploadCartItem(itemId, quantity, price, cartId);
    }
}
