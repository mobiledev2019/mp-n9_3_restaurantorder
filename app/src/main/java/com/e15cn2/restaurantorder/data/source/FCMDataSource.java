package com.e15cn2.restaurantorder.data.source;

import okhttp3.ResponseBody;
import retrofit2.Call;

public interface FCMDataSource {
    interface Remote {
        Call<ResponseBody> userPushSmallNotification(String title, String message);

        Call<ResponseBody> userPushBigNotification(String title, String message, String image);
    }
}
