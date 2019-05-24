package com.e15cn2.restaurantorder.screen.main.user.order_detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;

import java.util.List;

public class UserOrderDetailFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements UserOrderDetailContract.View {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private UserOrderDetailContract.Presenter mPresenter;
    private BaseAdapter<Cart> mAdapter;

    public static UserOrderDetailFragment newInstance(User user) {
        UserOrderDetailFragment fragment = new UserOrderDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mPresenter = new UserOrderDetailPresenter(
                CartRepository.getInstance(CartRemoteDataSource.getInstance()), this);
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_cart_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAdapter);
        if (getArguments() != null) {
            User user = getArguments().getParcelable(ARGUMENT_USER);
            mPresenter.getOrders(user.getId());
        }
    }

    @Override
    public void onSuccess(List<Cart> carts) {
        mAdapter.setData(carts);
    }

    @Override
    public void showError(Exception e) {
        //belong to dev
    }

    @Override
    public void showMessage(String msg) {
        //TODO handle later
    }
}
