package com.e15cn2.restaurantorder.screen.main.admin.cart;

import com.e15cn2.restaurantorder.data.model.Cart;

import java.util.List;

public interface AdminCartContract {
    interface Presenter {
        void getCarts();
    }

    interface View {
        void onSuccess(List<Cart> carts);

        void showMessage(String msg);
    }
}
