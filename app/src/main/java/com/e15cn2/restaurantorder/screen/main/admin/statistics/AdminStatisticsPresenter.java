package com.e15cn2.restaurantorder.screen.main.admin.statistics;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminStatisticsPresenter implements AdminStatisticsContract.Presenter {
    private CartRepository mCartRepository;
    private AdminStatisticsContract.View mView;

    public AdminStatisticsPresenter(CartRepository cartRepository, AdminStatisticsContract.View view) {
        mCartRepository = cartRepository;
        mView = view;
    }

    @Override
    public void getCartsByYear(int year) {
        mCartRepository.getCartByYear(year)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                List<Cart> carts = new ArrayList<>();
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString(Constants.JSonKey.MESSAGE);
                                JSONArray jsonArrayCart = jsonObject.getJSONArray(Constants.JSonKey.MESSAGE_OUTPUT);
                                if (message.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
                                    for (int i = 0; i < jsonArrayCart.length(); i++) {
                                        JSONObject objectCart = jsonArrayCart.getJSONObject(i);
                                        long cartId = objectCart.getLong(Constants.JsonCartKey.ID);
                                        User user = new User(objectCart.getJSONObject(Constants.JsonCartKey.USER));
                                        Table table = new Table(objectCart.getJSONObject(Constants.JsonCartKey.TABLE));
//                                        List<TableBooking> tableBookings = new ArrayList<>();
//                                        JSONArray arrTableBookings = objectCart.getJSONArray(Constants.JsonTableKey.TABLE_BOOKINGS);
//                                        for (int l = 0; l<arrTableBookings.length();l++){
//                                            JSONObject jSonTableBooking = arrTableBookings.getJSONObject(l);
//                                            TableBooking  tableBooking = new TableBooking(jSonTableBooking);
//                                            tableBookings.add(tableBooking);
//                                        }
                                        double cartPrice = objectCart.getDouble(Constants.JsonCartKey.PRICE);
                                        int cartStatus = objectCart.getInt(Constants.JsonCartKey.CART_STATUS);
                                        String time = objectCart.getString(Constants.JsonCartKey.TIME);
                                        List<CartItem> cartItems = new ArrayList<>();
                                        JSONArray arrCartItems = objectCart.getJSONArray(Constants.JsonCartKey.CART_ITEMS);
                                        for (int j = 0; j < arrCartItems.length(); j++) {
                                            JSONObject objectCartItem = arrCartItems.getJSONObject(j);
                                            Item item = new Item(objectCartItem.getJSONObject(Constants.JsonCartItemKey.ITEM));
                                            int quantity = objectCartItem.getInt(Constants.JsonCartItemKey.QUANTITY);
                                            double cartItemPrice = objectCartItem.getDouble(Constants.JsonCartItemKey.PRICE);
                                            CartItem cartItem = new CartItem(item, quantity, cartItemPrice);
                                            cartItems.add(cartItem);
                                        }
                                        Cart cart = new Cart.Builder()
                                                .setId(cartId)
                                                .setUser(user)
                                                .setTable(table)
                                                .setCartItems(cartItems)
                                                .setPrice(cartPrice)
                                                .setStatus(cartStatus)
                                                .setTime(time)
                                                .build();
                                        carts.add(cart);
                                    }
                                    mView.onSuccess(carts);
                                }
                            } catch (JSONException e) {
                                mView.showError(e);
                            } catch (IOException e) {
                                mView.showError(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
