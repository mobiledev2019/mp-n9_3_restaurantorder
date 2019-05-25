package com.e15cn2.restaurantorder.screen.main.admin.table.tables_list;

import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

public interface OnTableClickListener extends BaseAdapter.OnItemClickListener {
    void onTableClicked(Table table, int position);
}
