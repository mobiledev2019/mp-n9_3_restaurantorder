package com.e15cn2.restaurantorder.screen.main.admin.table.table_details;

public interface TableDetailsContract {
    interface Presenter {
        void updateTableStatus(String number, int status);
    }

    interface View {
        void showMessage(String msg);

        void showError(Exception e);
    }
}
