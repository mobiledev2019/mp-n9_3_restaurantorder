package com.e15cn2.restaurantorder.screen.main.user.item;

import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.repository.ItemRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemPresenter implements UserItemContract.Presenter {
    private ItemRepository mRepository;
    private UserItemContract.View mView;

    public UserItemPresenter(ItemRepository repository, UserItemContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getItems() {
        mRepository.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    mView.showItems(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });
    }
}
