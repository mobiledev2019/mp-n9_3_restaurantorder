package com.e15cn2.restaurantorder.screen.main.admin.statistics;

import com.e15cn2.restaurantorder.data.model.Cart;

import java.util.List;

public interface AdminStatisticsContract {
    interface Presenter {
        void getCartsByYear(int year);
    }

    interface View {
        void onSuccess(List<Cart> carts);

        void showError(Exception e);

        void showMessage(String msg);
    }
}
