package com.e15cn2.restaurantorder.utils;

public class Constants {
    public class Network {
        public static final String URL_BASE = "http://192.168.0.73:80/restaurant/";
        public static final String SIGN_IN = "sign_in.php";
        public static final String SIGN_UP = "sign_up.php";
        public static final String ADD_MENU = "add_menu.php";
        public static final String ADMIN_GET_MENU = "get_menu.php";
        public static final String ADD_ITEM = "add_item.php";
        public static final String ADMIN_GET_ITEM = "admin_get_item";
    }

    public class JSonKey {
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
        public static final String IMAGE = "image";
        public static final String IS_ADMIN = "is_admin";
    }

    public class UserKey {
        public static final int IS_ADMIN = 1;
    }

    public class JsonMenuKey {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String IMAGE_NAME = "image_name";
        public static final String IMAGE_CODE = "image_code";
    }

    public class JsonItemKey {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String MENU = "menu";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
        public static final String STATUS = "status";
        public static final String IMAGE = "image";
        public static final String IMAGE_NAME = "image_name";
        public static final String IMAGE_CODE = "image_code";
    }
}
