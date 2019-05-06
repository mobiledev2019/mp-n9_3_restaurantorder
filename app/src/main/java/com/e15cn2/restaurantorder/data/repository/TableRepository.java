package com.e15cn2.restaurantorder.data.repository;

import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.source.TableDataSource;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class TableRepository implements TableDataSource.Remote {
    private static TableRepository sInstance;
    private TableDataSource.Remote mRemote;

    private TableRepository(TableDataSource.Remote remote) {
        mRemote = remote;
    }

    public static TableRepository getInstance(TableDataSource.Remote remote) {
        if (sInstance == null) {
            sInstance = new TableRepository(remote);
        }
        return sInstance;
    }

    @Override
    public Call<ResponseBody> addTable(String number, String type) {
        return mRemote.addTable(number, type);
    }

    @Override
    public Call<List<Table>> getTables() {
        return mRemote.getTables();
    }
}
