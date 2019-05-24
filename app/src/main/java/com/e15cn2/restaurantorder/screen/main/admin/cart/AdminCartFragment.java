package com.e15cn2.restaurantorder.screen.main.admin.cart;

import android.support.v7.widget.LinearLayoutManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;

import java.util.List;

public class AdminCartFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements AdminCartContract.View {
    private AdminCartContract.Presenter mPresenter;
    private BaseAdapter<Cart> mAdapter;
    private static final String TAG = "AdminCartFragment";

    public static AdminCartFragment newInstance() {
        return new AdminCartFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mPresenter = new AdminCartPresenter(
                CartRepository.getInstance(CartRemoteDataSource.getInstance()), this);
        mPresenter.getCarts();
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_cart_admin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(List<Cart> carts) {
        mAdapter.setData(carts);
    }

    @Override
    public void showMessage(String msg) {
//TODO Handle later
    }
}
