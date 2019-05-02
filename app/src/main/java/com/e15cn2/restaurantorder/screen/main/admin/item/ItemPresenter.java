package com.e15cn2.restaurantorder.screen.main.admin.item;

import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.repository.ItemRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPresenter implements ItemContract.Presenter {
    private ItemRepository mRepository;
    private ItemContract.View mView;

    public ItemPresenter(ItemRepository repository, ItemContract.View view) {
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

    @Override
    public void updateItemStatus(int id, int status) {
        mRepository.updateItemStatus(id, status).enqueue(new Callback<ResponseBody>() {
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
