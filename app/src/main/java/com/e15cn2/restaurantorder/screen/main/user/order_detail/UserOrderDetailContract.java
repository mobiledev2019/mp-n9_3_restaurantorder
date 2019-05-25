package com.e15cn2.restaurantorder.screen.main.user.order_detail;

import com.e15cn2.restaurantorder.data.model.Cart;

import java.util.List;

public interface UserOrderDetailContract {
    interface Presenter {
        void getOrders(String userId);

        void deleteCart(long cartId);
    }

    interface View {
        void onSuccess(List<Cart> carts);

        void showError(Exception e);

        void showMessage(String msg);
    }
}
