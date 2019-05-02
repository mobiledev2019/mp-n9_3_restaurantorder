package com.e15cn2.restaurantorder.screen.main.admin.add_item;

import android.graphics.Bitmap;
import android.util.Base64;

import com.e15cn2.restaurantorder.data.repository.ItemRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemPresenter implements AddItemContract.Presenter {
    private ItemRepository mRepository;
    private AddItemContract.View mView;

    public AddItemPresenter(ItemRepository repository, AddItemContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void addItem(String name, String menu, double price, String desc, String imageName, String imageCode) {
        mRepository.addItem(name, menu, price, desc, imageName, imageCode).enqueue(new Callback<ResponseBody>() {
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
    public String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
