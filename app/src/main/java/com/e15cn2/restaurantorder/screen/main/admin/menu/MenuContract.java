package com.e15cn2.restaurantorder.screen.main.admin.menu;

import com.e15cn2.restaurantorder.data.model.Menu;

import java.util.List;

public interface MenuContract {
    interface Presenter {
        void getMenus();
    }

    interface View {
        void showMenus(List<Menu> menus);

        void showMessage(String msg);
    }
}
