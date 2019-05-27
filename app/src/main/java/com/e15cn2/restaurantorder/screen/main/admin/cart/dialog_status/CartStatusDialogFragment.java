package com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentDialogChangeCartStatusBinding;
import com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.AdminListCartFragment;
import com.e15cn2.restaurantorder.utils.Constants;

import static android.app.Activity.RESULT_OK;
import static com.e15cn2.restaurantorder.utils.Constants.JsonCartKey.STATUS_CANCELED;
import static com.e15cn2.restaurantorder.utils.Constants.JsonCartKey.STATUS_DISTRIBUTED;
import static com.e15cn2.restaurantorder.utils.Constants.JsonCartKey.STATUS_PAID;
import static com.e15cn2.restaurantorder.utils.Constants.JsonCartKey.STATUS_PROGRESS;

public class CartStatusDialogFragment extends DialogFragment
        implements CartStatusContract.View {
    private static final String ARGUMENT_CART = "ARGUMENT_CART";
    private CartStatusContract.Presenter mPresenter;
    private FragmentDialogChangeCartStatusBinding mBinding;
    private Cart mCart;

    public static CartStatusDialogFragment newInstance(Cart cart) {
        CartStatusDialogFragment fragment = new CartStatusDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDialogChangeCartStatusBinding
                .inflate(inflater, container, false);
        initData();
        return mBinding.getRoot();
    }

    private void initData() {
        mBinding.setListener(this);
        mPresenter = new CartStatusPresenter(
                CartRepository.getInstance(CartRemoteDataSource.getInstance()), this);
        if (getArguments() != null) {
            mCart = getArguments().getParcelable(ARGUMENT_CART);
            mBinding.setItem(mCart);
        }
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            //TODO handle success event
        } else {
            //TODO handle error action
        }
    }

    @Override
    public void showError(Exception e) {
        //dev
    }

    public void onInProgress() {
        if (mCart.getStatus() != STATUS_PROGRESS) {
            mPresenter.updateCartStatus(mCart.getId(), STATUS_PROGRESS);
            assert getTargetFragment() != null;
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    RESULT_OK,
                    AdminListCartFragment.getCartIntent(STATUS_PROGRESS));
            this.dismiss();
        }
    }

    public void onDistributed() {
        if (mCart.getStatus() != STATUS_DISTRIBUTED) {
            mPresenter.updateCartStatus(mCart.getId(), STATUS_DISTRIBUTED);
            assert getTargetFragment() != null;
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    RESULT_OK,
                    AdminListCartFragment.getCartIntent(STATUS_DISTRIBUTED));
            this.dismiss();
        }
    }

    public void onPaid() {
        if (mCart.getStatus() != STATUS_PAID) {
            mPresenter.updateCartStatus(mCart.getId(), STATUS_PAID);
            assert getTargetFragment() != null;
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    RESULT_OK,
                    AdminListCartFragment.getCartIntent(STATUS_PAID));
            this.dismiss();
        }
    }

    public void onCanceled() {
        if (mCart.getStatus() != STATUS_CANCELED) {
            mPresenter.updateCartStatus(mCart.getId(), STATUS_CANCELED);
            assert getTargetFragment() != null;
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    RESULT_OK,
                    AdminListCartFragment.getCartIntent(STATUS_CANCELED));
            this.dismiss();
        }
    }

    public void onDelete() {
        mPresenter.deleteCart(mCart.getId());
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                RESULT_OK,
                AdminListCartFragment.getCartIntent(mCart.getStatus()));
        this.dismiss();
    }
}
