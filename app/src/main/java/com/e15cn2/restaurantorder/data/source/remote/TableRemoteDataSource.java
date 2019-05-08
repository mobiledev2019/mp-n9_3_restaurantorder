package com.e15cn2.restaurantorder.data.source.remote;

import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.source.TableDataSource;
import com.e15cn2.restaurantorder.utils.network.AppConfig;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class TableRemoteDataSource implements TableDataSource.Remote {
    private static TableRemoteDataSource sInstance;

    public static TableRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TableRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> addTable(String number, String type) {
        return addTableAdmin(number, type);
    }

    @Override
    public Call<List<Table>> getTables() {
        return getTablesAdmin();
    }

    private Call<ResponseBody> addTableAdmin(String number, String type) {
        Call<ResponseBody> call = AppConfig.getApiConfig().addTable(number, type);
        return call;
    }

    private Call<List<Table>> getTablesAdmin() {
        Call<List<Table>> call = AppConfig.getApiConfig().getTablesAdmin();
        return call;
    }
}