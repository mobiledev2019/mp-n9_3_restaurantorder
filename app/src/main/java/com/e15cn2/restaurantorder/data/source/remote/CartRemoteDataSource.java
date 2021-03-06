package com.e15cn2.restaurantorder.data.source.remote;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.source.CartDataSource;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import java.util.List;

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

    @Override
    public Call<List<Cart>> getCartsAdmin() {
        return AppConfig.getApiConfig().getCartsAdmin();
    }

    @Override
    public Call<ResponseBody> getCartsUser(String userId) {
        return getCartsDetailUser(userId);
    }

    @Override
    public Call<ResponseBody> updateCartStatus(long cartId, int status) {
        return AppConfig.getApiConfig().updateCartStatus(cartId, status);
    }

    @Override
    public Call<ResponseBody> deleteCart(long cartId) {
        return AppConfig.getApiConfig().deleteCart(cartId);
    }

    @Override
    public Call<ResponseBody> getCartByYear(int year) {
        return AppConfig.getApiConfig().getCartsByYear(year);
    }

    private Call<ResponseBody> uploadCartUser(long cartId, String userId, String tableNumber, double price) {
        return AppConfig.getApiConfig().uploadCart(cartId, userId, tableNumber, price);
    }


    private Call<ResponseBody> uploadCartItemUser(int itemId, int quantity, double price, long cartId) {
        return AppConfig.getApiConfig().uploadCartItem(itemId, quantity, price, cartId);
    }

    private Call<ResponseBody> getCartsDetailUser(String userId) {
        return AppConfig.getApiConfig().getCartsUser(userId);
    }
}
