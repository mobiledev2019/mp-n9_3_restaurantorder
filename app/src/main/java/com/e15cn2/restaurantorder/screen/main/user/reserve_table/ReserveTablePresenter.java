package com.e15cn2.restaurantorder.screen.main.user.reserve_table;

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
    private TableRepository mRepository;
    private ReserveTableContract.View mView;

    public ReserveTablePresenter(TableRepository repository, ReserveTableContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void reserveTable(String number, String timeBooking, String userName, String userEmail, String userPhone) {
        mRepository.reserveTable(number, timeBooking, userName, userEmail, userPhone)
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
}
