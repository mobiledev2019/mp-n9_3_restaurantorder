package com.e15cn2.restaurantorder.data.source;

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
    }
}
