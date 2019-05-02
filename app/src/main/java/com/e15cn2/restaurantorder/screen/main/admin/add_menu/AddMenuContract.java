package com.e15cn2.restaurantorder.screen.main.admin.add_menu;

import android.graphics.Bitmap;

public interface AddMenuContract {
    interface Presenter {
        void addMenu(String name, String imageName, String imageCode);

        String encodeImage(Bitmap bitmap);
    }

    interface View {
        void showError(Exception e);

        void showMessage(String msg);
    }
}
