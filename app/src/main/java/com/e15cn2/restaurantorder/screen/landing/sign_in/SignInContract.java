package com.e15cn2.restaurantorder.screen.landing.sign_in;

import com.e15cn2.restaurantorder.data.model.User;

public interface SignInContract {
    interface Presenter {
        void signIn(String email,
                    String password);
    }

    interface View {
        void showUser(User user);

        void showMessage(String msg);

        void showError(Exception e);
    }
}
