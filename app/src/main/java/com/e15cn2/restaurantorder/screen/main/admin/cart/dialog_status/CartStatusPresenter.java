package com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_status;

import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartStatusPresenter implements CartStatusContract.Presenter {
    private CartRepository mCartRepository;
    private CartStatusContract.View mView;

    public CartStatusPresenter(CartRepository cartRepository, CartStatusContract.View view) {
        mCartRepository = cartRepository;
        mView = view;
    }


    @Override
    public void updateCartStatus(long cartId, int status) {
        mCartRepository.updateCartStatus(cartId, status)
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
