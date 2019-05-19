package com.e15cn2.restaurantorder.screen.main.user.cart;

public interface CartContract {
    interface Presenter {
        void uploadCart(long cartId,
                        String userId,
                        String tableNumber,
                        double price);

        void uploadCartItem(int itemId,
                            int quantity,
                            double price,
                            long cartId);
    }

    interface View {
        void showMessage(String msg);

        void showError(Exception e);
    }
}
