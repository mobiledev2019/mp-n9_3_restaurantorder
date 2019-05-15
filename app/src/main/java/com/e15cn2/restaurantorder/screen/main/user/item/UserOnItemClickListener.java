package com.e15cn2.restaurantorder.screen.main.user.item;

import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

public interface UserOnItemClickListener extends BaseAdapter.OnItemClickListener {
    void onItemClicked(Item item, int position);
}
