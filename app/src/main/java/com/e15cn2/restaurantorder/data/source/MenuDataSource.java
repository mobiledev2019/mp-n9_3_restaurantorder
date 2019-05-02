package com.e15cn2.restaurantorder.data.source;

import com.e15cn2.restaurantorder.data.model.Menu;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public interface MenuDataSource {
    interface Local {

    }

    interface Remote {
        Call<ResponseBody> addMenu(String name,
                                   String imageName,
                                   String imageCode);

        Call<List<Menu>> getMenus();
    }
}
