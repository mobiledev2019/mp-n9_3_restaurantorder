package com.e15cn2.restaurantorder.screen.main.user.cart;

import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.repository.FCMRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartContract.Presenter {
    private CartRepository mCartRepository;
    private FCMRepository mFCMRepository;
    private CartContract.View mView;

    public CartPresenter(CartRepository cartRepository, FCMRepository fcmRepository, CartContract.View view) {
        mCartRepository = cartRepository;
        mFCMRepository = fcmRepository;
        mView = view;
    }

    @Override
    public void uploadCart(long cartId, String userId, String tableNumber, double price) {
        mCartRepository.uploadCart(cartId, userId, tableNumber, price)
                .enqueue(new Callback<ResponseBody>() {
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

    @Override
    public void uploadCartItem(int itemId, int quantity, double price, long cartId) {
        mCartRepository.uploadCartItem(itemId, quantity, price, cartId)
                .enqueue(new Callback<ResponseBody>() {
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

    @Override
    public void pushSmallNotification(String title, String msg) {
        mFCMRepository.userPushSmallNotification(title, msg)
                .enqueue(new Callback<ResponseBody>() {
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
