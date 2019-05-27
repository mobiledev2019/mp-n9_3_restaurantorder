package com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

public interface OnCartClickListener extends BaseAdapter.OnItemClickListener {
    boolean onLongClicked(Cart cart, int position);

    void onSendSMS(Cart cart);
}
