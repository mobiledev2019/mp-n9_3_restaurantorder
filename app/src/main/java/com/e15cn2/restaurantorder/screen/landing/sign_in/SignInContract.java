package com.e15cn2.restaurantorder.screen.landing.sign_in;

import com.e15cn2.restaurantorder.data.model.User;

public interface SignInContract {
    interface Presenter {
        void signIn(String email,
                    String password);

        void signUpSocialAccount(String id,
                                 String name,
                                 String dob,
                                 String email,
                                 String phone,
                                 String password);
    }

    interface View {
        void showUser(User user);

        void showMessage(String msg);

        void showError(Exception e);
    }
}
