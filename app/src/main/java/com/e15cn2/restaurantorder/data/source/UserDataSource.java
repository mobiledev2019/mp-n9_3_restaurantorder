package com.e15cn2.restaurantorder.data.source;


import okhttp3.ResponseBody;
import retrofit2.Call;

public interface UserDataSource {
    interface Local {

    }

    interface Remote {
        Call<ResponseBody> signUp(String id,
                                  String name,
                                  String dob,
                                  String email,
                                  String phone,
                                  String password);

        Call<ResponseBody> signIn(String email,
                                  String password);
    }
}
