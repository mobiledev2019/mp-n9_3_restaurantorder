package com.e15cn2.restaurantorder.utils.network;

import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiConfig {
    @FormUrlEncoded
    @POST(Constants.Network.SIGN_UP)
    Call<ResponseBody> signUp(@Field(Constants.JsonUserKey.NAME) String name,
                              @Field(Constants.JsonUserKey.DOB) String dob,
                              @Field(Constants.JsonUserKey.EMAIL) String email,
                              @Field(Constants.JsonUserKey.PHONE) String phone,
                              @Field(Constants.JsonUserKey.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Constants.Network.SIGN_IN)
    Call<ResponseBody> signIn(@Field(Constants.JsonUserKey.EMAIL) String identity,
                              @Field(Constants.JsonUserKey.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Constants.Network.ADD_MENU)
    Call<ResponseBody> addMenu(@Field(Constants.JsonMenuKey.NAME) String name,
                               @Field(Constants.JsonMenuKey.IMAGE_NAME) String imageName,
                               @Field(Constants.JsonMenuKey.IMAGE_CODE) String imageCode);

    @GET(Constants.Network.ADMIN_GET_MENU)
    Call<List<Menu>> getMenuAdmin();
}
