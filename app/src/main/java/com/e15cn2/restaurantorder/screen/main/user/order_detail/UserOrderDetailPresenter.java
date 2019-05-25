package com.e15cn2.restaurantorder.screen.main.user.order_detail;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Table;
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

public class UserOrderDetailPresenter implements UserOrderDetailContract.Presenter {
    private CartRepository mCartRepository;
    private UserOrderDetailContract.View mView;

    public UserOrderDetailPresenter(CartRepository cartRepository, UserOrderDetailContract.View view) {
        mCartRepository = cartRepository;
        mView = view;
    }

    @Override
    public void getOrders(String userId) {
        mCartRepository.getCartsUser(userId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                List<Cart> carts = new ArrayList<>();
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString(Constants.JSonKey.MESSAGE);
                                JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSonKey.MESSAGE_OUTPUT);
                                if (message.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject objectCart = jsonArray.getJSONObject(i);
                                        long cartId = objectCart.getLong(Constants.JsonCartKey.ID);
                                        Table table = new Table(objectCart.getJSONObject(Constants.JsonCartKey.TABLE));
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
                                        Cart cart = new Cart(cartId, table, cartItems, cartPrice, cartStatus, time);
                                        carts.add(cart);
                                    }
                                    mView.onSuccess(carts);

                                }
                                mView.showMessage(message);
                            } catch (JSONException e) {
                                mView.showError(e);
                            } catch (IOException e) {
                                mView.showError(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mView.showMessage(t.getMessage());
                    }
                });
    }

    @Override
    public void deleteCart(long cartId) {
        mCartRepository.deleteCart(cartId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String message = jsonObject.getString(Constants.JSonKey.MESSAGE);
                        mView.showMessage(message);
                    } catch (JSONException e) {
                        mView.showError(e);
                    } catch (IOException e) {
                        mView.showError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });
    }
}
