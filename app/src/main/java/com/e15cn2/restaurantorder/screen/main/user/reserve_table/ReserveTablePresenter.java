package com.e15cn2.restaurantorder.screen.main.user.reserve_table;

import com.e15cn2.restaurantorder.data.repository.FCMRepository;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveTablePresenter implements ReserveTableContract.Presenter {
    private TableRepository mTableRepository;
    private FCMRepository mFCMRepository;
    private ReserveTableContract.View mView;

    public ReserveTablePresenter(TableRepository tableRepository,
                                 FCMRepository fcmRepository,
                                 ReserveTableContract.View view) {
        mTableRepository = tableRepository;
        mFCMRepository = fcmRepository;
        mView = view;
    }

    @Override
    public void reserveTable(String number, String timeBooking, String userId, String phoneBooking) {
        mTableRepository.reserveTable(number, timeBooking, userId, phoneBooking)
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
        mFCMRepository.userPushSmallNotification(title, msg).enqueue(new Callback<ResponseBody>() {
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
