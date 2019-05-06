package com.e15cn2.restaurantorder.screen.main.admin.item;

import com.e15cn2.restaurantorder.data.model.Item;

import java.util.List;

public interface AdminItemContract {
    interface Presenter {
        void getItems();
        void updateItemStatus(int id, int status);
    }

    interface View {
        void showItems(List<Item> items);
        void showError(Exception e);
        void showMessage(String msg);
    }
}
