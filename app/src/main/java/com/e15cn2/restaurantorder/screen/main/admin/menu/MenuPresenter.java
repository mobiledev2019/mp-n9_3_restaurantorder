package com.e15cn2.restaurantorder.screen.main.admin.menu;

import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.repository.MenuRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPresenter implements MenuContract.Presenter {
    private MenuRepository mRepository;
    private MenuContract.View mView;

    public MenuPresenter(MenuRepository repository, MenuContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getMenus() {
        mRepository.getMenus().enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if (response.isSuccessful()) {
                    mView.showMenus(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });
    }
}
