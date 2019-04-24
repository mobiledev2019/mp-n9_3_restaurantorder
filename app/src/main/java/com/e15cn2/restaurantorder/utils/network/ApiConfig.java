package com.e15cn2.restaurantorder.utils.network;

import com.e15cn2.restaurantorder.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConfig {
    @FormUrlEncoded
    @POST(Constants.Network.SIGN_UP)
    Call<ResponseBody> signUp(@Field("name") String name,
                              @Field("dob") String dob,
                              @Field("email") String email,
                              @Field("phone") String phone,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.Network.SIGN_IN)
    Call<ResponseBody> signIn(@Field("email") String identity,
                              @Field("password") String password);

}
