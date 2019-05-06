package com.e15cn2.restaurantorder.screen.main.user.item;

import com.e15cn2.restaurantorder.data.model.Item;

import java.util.List;

public interface UserItemContract {
    interface Presenter {
        void getItems();
    }

    interface View {
        void showItems(List<Item> items);

        void showError(Exception e);

        void showMessage(String msg);
    }
}
