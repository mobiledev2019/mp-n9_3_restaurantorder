package com.e15cn2.restaurantorder.data.source;

import com.e15cn2.restaurantorder.data.model.Table;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public interface TableDataSource {
    interface Local {

    }

    interface Remote {
        Call<ResponseBody> addTable(String number, String type);

        Call<List<Table>> getTables();

        Call<ResponseBody> reserveTable(String number,
                                        String timeBooking,
                                        String userId,
                                        String phoneBooking);

        Call<ResponseBody> updateTableStatus(String number, int status);
    }
}
