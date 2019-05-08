package com.e15cn2.restaurantorder.screen.main.admin.add_table;

public interface AddTableContract {
    interface Presenter {
        void addTable(String number, String type);
    }

    interface View {
        void showMessage(String msg);

        void showError(Exception e);
    }
}
