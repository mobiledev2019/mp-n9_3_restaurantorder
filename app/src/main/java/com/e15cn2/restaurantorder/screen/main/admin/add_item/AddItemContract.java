package com.e15cn2.restaurantorder.screen.main.admin.add_item;

import android.graphics.Bitmap;

public interface AddItemContract {
    interface Presenter {
        void addItem(String name,
                     String menu,
                     double price,
                     String desc,
                     String imageName,
                     String imageCode);

        String encodeImage(Bitmap bitmap);
    }

    interface View {
        void showError(Exception e);

        void showMessage(String msg);
    }
}
