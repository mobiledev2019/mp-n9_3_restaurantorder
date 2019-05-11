package com.e15cn2.restaurantorder.screen.main.user.reserve_table;

public interface ReserveTableContract {
    interface Presenter {
        void reserveTable(String number,
                          String timeBooking,
                          String userName,
                          String userEmail,
                          String userPhone);
    }

    interface View {
        void showMessage(String msg);

        void showError(Exception e);
    }
}
