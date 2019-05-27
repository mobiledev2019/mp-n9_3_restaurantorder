package com.e15cn2.restaurantorder.screen.main.user.order_detail;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.OnCartClickListener;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.List;

public class UserOrderDetailFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements UserOrderDetailContract.View, OnCartClickListener {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private UserOrderDetailContract.Presenter mPresenter;
    private BaseAdapter<Cart> mAdapter;
    private int mCartPosition;
    private List<Cart> mCarts;

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
        mAdapter.setListener(this);
        if (getArguments() != null) {
            User user = getArguments().getParcelable(ARGUMENT_USER);
            mPresenter.getOrders(user.getId());
        }
    }

    @Override
    public void onSuccess(List<Cart> carts) {
        mCarts = carts;
        mAdapter.setData(mCarts);
    }

    @Override
    public void showError(Exception e) {
        //belong to dev
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_DELETE_SUCCESS)) {
            mCarts.remove(mCartPosition);
            mAdapter.notifyItemRemoved(mCartPosition);
        }
        //TODO handle later
    }

    @Override
    public boolean onLongClicked(Cart cart, int position) {
        showAlertDialog(cart, position);
        return true;
    }

    @Override
    public void onSendSMS(Cart cart) {
        //Belong to admin screen
    }

    private void showAlertDialog(final Cart cart, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_inform_yes_no, null);
        builder.setCancelable(false);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        TextView message = mView.findViewById(R.id.text_inform);
        Button buttonNo = mView.findViewById(R.id.button_negative);
        Button buttonYes = mView.findViewById(R.id.button_positive);
        buttonNo.setText(AppContext.getInstance().getString(R.string.text_cancel));
        buttonYes.setText(AppContext.getInstance().getString(R.string.text_delete));
        message.setText(AppContext.getInstance().getString(R.string.text_ask_delete));
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getStatus() == Constants.JsonCartKey.STATUS_PROGRESS) {
                    mPresenter.deleteCart(cart.getId());
                    mCartPosition = position;
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_inform_cannot_delete), Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }
}
