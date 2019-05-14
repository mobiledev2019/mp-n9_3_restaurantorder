package com.e15cn2.restaurantorder.screen.landing.sign_up;

import com.e15cn2.restaurantorder.data.repository.UserRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter implements SignUpContract.Presenter {
    private UserRepository mRepository;
    private SignUpContract.View mView;

    public SignUpPresenter(UserRepository repository, SignUpContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void signUp(String id,
                       String name,
                       String dob,
                       String email,
                       String phone,
                       String password) {
        mRepository.signUp(id, name, dob, email, phone, password).enqueue(new Callback<ResponseBody>() {
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
