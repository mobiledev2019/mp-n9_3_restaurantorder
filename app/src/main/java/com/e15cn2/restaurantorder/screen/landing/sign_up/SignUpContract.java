package com.e15cn2.restaurantorder.screen.landing.sign_up;

public interface SignUpContract {
    interface Presenter {
        void signUp(String name,
                    String dob,
                    String email,
                    String phone,
                    String password);
    }

    interface View {
        void showMessage(String msg);

        void showError(Exception e);
    }
}
