package com.e15cn2.restaurantorder.screen.main.user.cart;

import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

public interface OnCartItemListener extends BaseAdapter.OnItemClickListener {
    void onButtonCloseClicked(CartItem cartItem, int position);
}
