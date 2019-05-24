package com.e15cn2.restaurantorder.screen.main.admin.cart;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.repository.CartRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCartPresenter implements AdminCartContract.Presenter {
    private CartRepository mCartRepository;
    private AdminCartContract.View mView;

    public AdminCartPresenter(CartRepository cartRepository, AdminCartContract.View view) {
        mCartRepository = cartRepository;
        mView = view;
    }

    @Override
    public void getCarts() {
        mCartRepository.getCartsAdmin().enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()) {
                    mView.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });
    }
}
