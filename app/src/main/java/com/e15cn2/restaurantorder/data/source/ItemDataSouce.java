package com.e15cn2.restaurantorder.data.source;

import com.e15cn2.restaurantorder.data.model.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public interface ItemDataSouce {
    interface Local {

    }

    interface Remote {
        Call<ResponseBody> addItem(String name,
                                   String menu,
                                   double price,
                                   String desc,
                                   String imageName,
                                   String imageCode);

        Call<List<Item>> getItems();
    }
}
