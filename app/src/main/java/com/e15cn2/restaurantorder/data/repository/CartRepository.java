package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.source.CartDataSource;

import java.util.List;

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

    @Override
    public Call<List<Cart>> getCartsAdmin() {
        return mRemote.getCartsAdmin();
    }

    @Override
    public Call<ResponseBody> getCartsUser(String userId) {
        return mRemote.getCartsUser(userId);
    }

    @Override
    public Call<ResponseBody> updateCartStatus(long cartId, int status) {
        return mRemote.updateCartStatus(cartId, status);
    }

    @Override
    public Call<ResponseBody> deleteCart(long cartId) {
        return mRemote.deleteCart(cartId);
    }

    @Override
    public Call<ResponseBody> getCartByYear(int year) {
        return mRemote.getCartByYear(year);
    }
}
