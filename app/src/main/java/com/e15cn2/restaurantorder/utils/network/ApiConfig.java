package com.e15cn2.restaurantorder.utils.network;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.model.Table;
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
    Call<ResponseBody> signUp(@Field(Constants.JsonUserKey.ID) String id,
                              @Field(Constants.JsonUserKey.NAME) String name,
                              @Field(Constants.JsonUserKey.DOB) String dob,
                              @Field(Constants.JsonUserKey.EMAIL) String email,
                              @Field(Constants.JsonUserKey.PHONE) String phone,
                              @Field(Constants.JsonUserKey.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Constants.Network.SIGN_IN)
    Call<ResponseBody> signIn(@Field(Constants.JsonUserKey.EMAIL) String identity,
                              @Field(Constants.JsonUserKey.PASSWORD) String password,
                              @Field(Constants.JsonUserKey.TOKEN) String token);

    @FormUrlEncoded
    @POST(Constants.Network.ADD_MENU)
    Call<ResponseBody> addMenu(@Field(Constants.JsonMenuKey.NAME) String name,
                               @Field(Constants.JsonMenuKey.IMAGE_NAME) String imageName,
                               @Field(Constants.JsonMenuKey.IMAGE_CODE) String imageCode);

    @GET(Constants.Network.ADMIN_GET_MENU)
    Call<List<Menu>> getMenusAdmin();

    @FormUrlEncoded
    @POST(Constants.Network.ADD_ITEM)
    Call<ResponseBody> addItem(@Field(Constants.JsonItemKey.NAME) String name,
                               @Field(Constants.JsonItemKey.MENU) String menu,
                               @Field(Constants.JsonItemKey.PRICE) double price,
                               @Field(Constants.JsonItemKey.DESCRIPTION) String description,
                               @Field(Constants.JsonItemKey.IMAGE_NAME) String imageName,
                               @Field(Constants.JsonItemKey.IMAGE_CODE) String imageCode);

    @GET(Constants.Network.ADMIN_GET_ITEMS)
    Call<List<Item>> getItemsAdmin();

    @FormUrlEncoded
    @POST(Constants.Network.UPDATE_ITEM_STATUS)
    Call<ResponseBody> updateItemStatus(@Field(Constants.JsonItemKey.ID) int id,
                                        @Field(Constants.JsonItemKey.ITEM_STATUS) int status);

    @FormUrlEncoded
    @POST(Constants.Network.ADD_TABLE)
    Call<ResponseBody> addTable(@Field(Constants.JsonTableKey.NUMBER) String number,
                                @Field(Constants.JsonTableKey.TYPE) String type);

    @GET(Constants.Network.ADMIN_GET_TABLES)
    Call<List<Table>> getTablesAdmin();

    @FormUrlEncoded
    @POST(Constants.Network.RESERVE_TABLE)
    Call<ResponseBody> reserveTable(@Field(Constants.JSonTableBookingKey.TABLE_NUMBER) String number,
                                    @Field(Constants.JSonTableBookingKey.TIME_BOOKING) String timeBooking,
                                    @Field(Constants.JSonTableBookingKey.USER_ID) String userId,
                                    @Field(Constants.JSonTableBookingKey.PHONE_BOOKING) String phoneBooking);

    @FormUrlEncoded
    @POST(Constants.Network.UPDATE_TABLE_STATUS)
    Call<ResponseBody> updateTableStatus(@Field(Constants.JsonTableKey.NUMBER) String number,
                                         @Field(Constants.JsonTableKey.TABLE_STATUS) int status);

    @FormUrlEncoded
    @POST(Constants.Network.PUSH_NOTIFICATION_TO_ADMIN)
    Call<ResponseBody> userPushBigNotification(@Field(Constants.JsonNotificationKey.TITLE) String title,
                                               @Field(Constants.JsonNotificationKey.MESSAGE) String message,
                                               @Field(Constants.JsonNotificationKey.IMAGE) String image);

    @FormUrlEncoded
    @POST(Constants.Network.PUSH_NOTIFICATION_TO_ADMIN)
    Call<ResponseBody> userPushSmallNotification(@Field(Constants.JsonNotificationKey.TITLE) String title,
                                                 @Field(Constants.JsonNotificationKey.MESSAGE) String message);

    @FormUrlEncoded
    @POST(Constants.Network.UPLOAD_CART)
    Call<ResponseBody> uploadCart(@Field(Constants.JsonCartKey.ID) long cartId,
                                  @Field(Constants.JsonCartKey.USER_ID) String userId,
                                  @Field(Constants.JsonCartKey.TABLE_NUMBER) String tableNumber,
                                  @Field(Constants.JsonCartKey.PRICE) double price);

    @FormUrlEncoded
    @POST(Constants.Network.UPLOAD_CART_ITEM)
    Call<ResponseBody> uploadCartItem(@Field(Constants.JsonCartItemKey.ITEM_ID) int itemId,
                                      @Field(Constants.JsonCartItemKey.QUANTITY) int quantity,
                                      @Field(Constants.JsonCartItemKey.PRICE) double price,
                                      @Field(Constants.JsonCartItemKey.CART_ID) long cartId);

    @GET(Constants.Network.ADMIN_GET_CARTS)
    Call<List<Cart>> getCartsAdmin();

    @FormUrlEncoded
    @POST(Constants.Network.USER_GET_CARTS)
    Call<ResponseBody> getCartsUser(@Field(Constants.JsonUserKey.USER_ID) String userId);

    @FormUrlEncoded
    @POST(Constants.Network.UPDATE_CART_STATUS)
    Call<ResponseBody> updateCartStatus(@Field(Constants.JsonCartKey.ID) long id,
                                        @Field(Constants.JsonCartKey.CART_STATUS) int status);

    @FormUrlEncoded
    @POST(Constants.Network.DELETE_CART)
    Call<ResponseBody> deleteCart(@Field(Constants.JsonCartKey.ID) long id);
}
