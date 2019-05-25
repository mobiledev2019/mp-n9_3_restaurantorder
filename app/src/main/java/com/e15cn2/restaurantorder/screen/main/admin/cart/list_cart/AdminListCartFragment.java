package com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_status.CartStatusDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AdminListCartFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements OnCartClickListener {
    public static final String ACTION_RELOAD =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.ACTION_RELOAD";
    public static final String EXTRA_RELOAD =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_RELOAD";
    public static final String EXTRA_CURRENT_TAB =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_CURRENT_TAB";
    private static final String EXTRA_STATUS
            = "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_STATUS";
    private BaseAdapter<Cart> mAdapter;
    private static final String ARGUMENT_CARTS = "ARGUMENT_CARTS";
    private static final int REQUEST_DIALOG = 1862;
    private List<Cart> mCarts;

    public static AdminListCartFragment newInstance(List<Cart> carts) {
        AdminListCartFragment fragment = new AdminListCartFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_CARTS, (ArrayList<? extends Parcelable>) carts);
        fragment.setArguments(args);
        return fragment;
    }

    public static Intent getCartIntent(int status) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STATUS, status);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_cart_admin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
        if (getArguments() != null) {
            mCarts = getArguments().getParcelableArrayList(ARGUMENT_CARTS);
            mAdapter.setData(mCarts);
        }
    }

    @Override
    public boolean onLongClicked(Cart cart, int position) {
        CartStatusDialogFragment dialogFragment = CartStatusDialogFragment.newInstance(cart);
        dialogFragment.setTargetFragment(this, REQUEST_DIALOG);
        assert getFragmentManager() != null;
        dialogFragment.show(getFragmentManager(), null);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIALOG && resultCode == RESULT_OK) {
            int status = data.getIntExtra(EXTRA_STATUS, 0);
            Intent intent = new Intent(ACTION_RELOAD);
            intent.putExtra(EXTRA_RELOAD, true);
            intent.putExtra(EXTRA_CURRENT_TAB, status);
            getActivity().sendBroadcast(intent);
        }
    }
}
