package com.e15cn2.restaurantorder.screen.main.admin.menu;

import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

public interface OnMenuClickListener extends BaseAdapter.OnItemClickListener {
    void onMenuClicked(Menu menu, int position);

    boolean onMenuLongClicked(Menu menu, int position);
}
