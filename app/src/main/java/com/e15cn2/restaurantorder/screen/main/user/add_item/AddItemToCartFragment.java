package com.e15cn2.restaurantorder.screen.main.user.add_item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.FragmentDialogAddItemBinding;

public class AddItemToCartFragment extends DialogFragment {
    private FragmentDialogAddItemBinding mBinding;
    private static final String ARGUMENT_ITEM = "ARGUMENT_ITEM";
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private static final String ARGUMENT_TABLE = "ARGUMENT_TABLE";
    private User mUser;
    private Table mTable;
    private Item mItem;
    private int mQuantity;
    String TAG = "AddItemToCartFragment";

    public static AddItemToCartFragment newInstance(User user, Table table, Item item) {
        AddItemToCartFragment fragment = new AddItemToCartFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_ITEM, item);
        args.putParcelable(ARGUMENT_USER, user);
        args.putParcelable(ARGUMENT_TABLE, table);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDialogAddItemBinding.inflate(inflater, container, false);
        initData();
        return mBinding.getRoot();
    }

    private void initData() {
        mQuantity = 0;
        this.setCancelable(false);
        mBinding.setListener(this);
        if (getArguments() != null) {
            mItem = getArguments().getParcelable(ARGUMENT_ITEM);
            mUser = getArguments().getParcelable(ARGUMENT_USER);
            mTable = getArguments().getParcelable(ARGUMENT_TABLE);
            mBinding.setItem(mItem);
        }
    }

    public void onPlus() {
        mQuantity++;
        mBinding.textQuantity.setText(String.valueOf(mQuantity));
        if (mQuantity > 0) {
            mBinding.buttonAdd.setEnabled(true);
        }
    }

    public void onMinus() {
        if (mQuantity > 0) {
            mQuantity--;
            mBinding.textQuantity.setText(String.valueOf(mQuantity));
        } else {
            mBinding.buttonAdd.setEnabled(false);
        }
    }

    public void onClose() {
        this.dismiss();
    }

    public void onAddToCart() {
        double price = mItem.getPrice() * mQuantity;
        CartItem cartItem = new CartItem(mItem, mTable, mUser, mQuantity, price);
        Log.d(TAG, "onAddToCart: " + cartItem.toString());
        //TODO
        this.dismiss();
    }
}
