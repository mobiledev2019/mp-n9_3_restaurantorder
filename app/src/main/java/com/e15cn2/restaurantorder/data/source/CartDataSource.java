package com.e15cn2.restaurantorder.data.source;

import com.e15cn2.restaurantorder.data.model.Cart;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public interface CartDataSource {
    interface Remote {
        Call<ResponseBody> uploadCart(long cartId,
                                      String userId,
                                      String tableNumber,
                                      double price);

        Call<ResponseBody> uploadCartItem(int itemId,
                                          int quantity,
                                          double price,
                                          long cartId);

        Call<List<Cart>> getCartsAdmin();

        Call<ResponseBody> getCartsUser(String userId);

        Call<ResponseBody> updateCartStatus(long cartId, int status);

        Call<ResponseBody> deleteCart(long cartId);

        Call<ResponseBody> getCartByYear(int year);
    }
}
