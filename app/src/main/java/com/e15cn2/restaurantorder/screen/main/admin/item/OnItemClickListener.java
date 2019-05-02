package com.e15cn2.restaurantorder.screen.main.admin.item;

import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

public interface OnItemClickListener extends BaseAdapter.OnItemClickListener {
    void onMenuClicked(Item item, int position);

    boolean onMenuLongClicked(Item item, int position);

    void onButtonOnClicked(Item item, int position);

    void onButtonOffClicked(Item item, int position);
}
