package com.e15cn2.restaurantorder.screen.main.user.table;

import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.repository.TableRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTablePresenter implements UserTableContract.Presenter {
    private TableRepository mRepository;
    private UserTableContract.View mView;

    public UserTablePresenter(TableRepository repository, UserTableContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getTables() {
        mRepository.getTables().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                if (response.isSuccessful()) {
                    mView.onGetTablesSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                mView.showFailureMessage(t.getMessage());
            }
        });
    }
}
