package com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_status;

public interface CartStatusContract {
    interface Presenter {
        void updateCartStatus(long cartId, int status);

        void deleteCart(long cartId);
    }

    interface View {
        void showMessage(String msg);

        void showError(Exception e);
    }
}
