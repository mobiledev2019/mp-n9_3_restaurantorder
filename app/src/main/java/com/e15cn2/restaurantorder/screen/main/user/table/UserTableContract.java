package com.e15cn2.restaurantorder.screen.main.user.table;

import com.e15cn2.restaurantorder.data.model.Table;

import java.util.List;

public interface UserTableContract {
    interface Presenter {
        void getTables();
    }

    interface View {
        void onGetTablesSuccess(List<Table> tables);

        void showFailureMessage(String msg);
    }
}
