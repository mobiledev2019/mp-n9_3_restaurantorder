package com.e15cn2.restaurantorder.utils;

public class Constants {
    public class Network {
        public static final String URL_BASE = "http://192.168.0.73:80/restaurant/";
        public static final String SIGN_IN = "sign_in.php";
        public static final String SIGN_UP = "sign_up.php";
    }

    public static class JSonKey {
        public static final String MESSAGE = "message";
        public static final String MESSAGE_SUCCESS = "SUCCESS";
        public static final String MESSAGE_EXISTED = "EXISTED";
    }

    public class JsonUserKey {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DOB = "dob";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String IS_ADMIN = "is_admin";
    }
}
