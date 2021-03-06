package com.e15cn2.restaurantorder.screen.landing.sign_in;

import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.UserRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPresenter implements SignInContract.Presenter {
    private UserRepository mRepository;
    private SignInContract.View mView;

    public SignInPresenter(UserRepository repository, SignInContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void signIn(String email, String password, String token) {
        mRepository.signIn(email, password, token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String message = jsonObject.getString(Constants.JSonKey.MESSAGE);
                        if (message.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
                            User user = new User(jsonObject);
                            mView.showUser(user);
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
    public void signUpSocialAccount(String id, String name, String dob, String email, String phone, String password) {
        mRepository.signUp(id, name, dob, email, phone, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Do nothing
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Do nothing
            }
        });
    }
}
