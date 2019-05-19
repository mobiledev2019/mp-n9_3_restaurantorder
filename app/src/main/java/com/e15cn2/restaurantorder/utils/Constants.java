package com.e15cn2.restaurantorder.utils;

public class Constants {
    public class Network {
        public static final String URL_BASE = "http://192.168.0.73:80/restaurant/";
        //      public static final String URL_BASE = "http://192.168.43.100:80/restaurant/";
        public static final String SIGN_IN = "sign_in.php";
        public static final String SIGN_UP = "sign_up.php";
        public static final String ADD_MENU = "add_menu.php";
        public static final String ADMIN_GET_MENU = "get_menu.php";
        public static final String ADD_ITEM = "add_item.php";
        public static final String ADMIN_GET_ITEMS = "admin_get_items.php";
        public static final String UPDATE_ITEM_STATUS = "update_item_status.php";
        public static final String ADD_TABLE = "add_table.php";
        public static final String ADMIN_GET_TABLES = "admin_get_tables.php";
        public static final String RESERVE_TABLE = "reserve_table.php";
        public static final String UPDATE_TABLE_STATUS = "update_table_status.php";
        public static final String PUSH_NOTIFICATION_TO_ADMIN = "push_notification_to_admin.php";
        public static final String PUSH_NOTIFICATION_TO_USER = "push_notification_to_user.php";
        public static final String UPLOAD_CART = "upload_cart.php";
        public static final String UPLOAD_CART_ITEM= "upload_cart_item.php";
    }

    public class JSonKey {
        public static final String MESSAGE = "message";
        public static final String MESSAGE_SUCCESS = "SUCCESS";
        public static final String MESSAGE_EXISTED = "EXISTED";
        public static final String PARAMETERS_MISSING = "PARAMETERS MISSING";
        public static final String INVALID_REQUEST = "INVALID_REQUEST";
    }

    public class JsonCommonKey {
        public static final String ID = "id";
        public static final String STATUS = "status";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String IMAGE_NAME = "image_name";
        public static final String IMAGE_CODE = "image_code";
        public static final int IS_ON = 1;
        public static final int IS_OFF = 0;
    }

    public class JsonUserKey extends JsonCommonKey {
        public static final String DOB = "dob";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String IS_ADMIN = "is_admin";
        public static final String TOKEN = "token";
    }

    public class UserKey {
        public static final int IS_ADMIN = 1;
    }

    public class JsonMenuKey extends JsonCommonKey {

    }

    public class JsonItemKey extends JsonCommonKey {
        public static final String MENU = "menu";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
    }

    public class JsonTableKey extends JsonCommonKey {
        public static final String TABLE_BOOKINGS = "table_bookings";
        public static final String TYPE = "type";
        public static final String NUMBER = "number";
    }

    public class JSonTableBookingKey extends JsonCommonKey {
        public static final String TABLE_NUMBER = "table_number";
        public static final String TIME_BOOKING = "time_booking";
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_ID = "user_id";
        public static final String PHONE_BOOKING = "phone_booking";
    }

    public static class TableKey {
        public static final String TYPE_6_PEOPLES = "Table 6 People";
        public static final String TYPE_4_PEOPLES = "Table 4 People";
    }

    public static class JsonNotificationKey {
        public static final String DATA = "data";
        public static final String TITLE = "title";
        public static final String MESSAGE = "message";
        public static final String IMAGE = "image";
        public static final String RES_NULL = "null";
    }

    public static class JsonCartKey {
        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String USER = "user";
        public static final String TABLE = "table";
        public static final String TABLE_NUMBER = "table_number";
        public static final String PRICE = "price";
        public static final String TIME = "time";
    }

    public static class JsonCartItemKey {
        public static final String ITEM_ID = "item_id";
        public static final String ITEM = "item";
        public static final String QUANTITY = "quantity";
        public static final String PRICE = "price";
        public static final String CART_ID = "cart_id";
    }
}
