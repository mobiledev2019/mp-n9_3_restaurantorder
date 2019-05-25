package com.e15cn2.restaurantorder.screen.main.user.cart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.repository.FCMRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.data.source.remote.FCMRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentCartBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.user.UserMainActivity;
import com.e15cn2.restaurantorder.screen.main.user.order_detail.UserOrderDetailFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.List;

public class CartFragment extends BaseFragment<FragmentCartBinding>
        implements OnCartItemListener, CartContract.View {
    private static final String ARGUMENT_CART = "ARGUMENT_CART";
    private BaseAdapter<CartItem> mAdapter;
    private Cart mCart;
    private List<CartItem> mCartItems;
    private OnCartItemChangedListener mCallback;
    private CartContract.Presenter mPresenter;
    private String mNotificationTitle;
    private String mNotificationMessage;

    public static CartFragment newInstance(Cart cart) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCartItemChangedListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initData() {
        mPresenter = new CartPresenter(
                CartRepository.getInstance(CartRemoteDataSource.getInstance()),
                FCMRepository.getInstance(FCMRemoteDataSource.getInstance()),
                this);
        binding.setFragment(this);
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_cart_item_user);
        mAdapter.setListener(this);
        if (getArguments() != null) {
            mCart = getArguments().getParcelable(ARGUMENT_CART);
            binding.setItem(mCart);
            mCartItems = mCart.getCartItems();
            mAdapter.setData(mCartItems);
        }
        binding.recyclerCartItems.setAdapter(mAdapter);
        binding.recyclerCartItems.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onButtonCloseClicked(CartItem cartItem, int position) {
        mCartItems.remove(cartItem);
        mAdapter.notifyItemRemoved(position);
        mCallback.onRemove(mCartItems);
        UserMainActivity activity = (UserMainActivity) getActivity();
        mCart = activity.getNewCart();
        binding.setItem(mCart);
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            mPresenter.pushSmallNotification(mNotificationTitle, mNotificationMessage);
            mCartItems.clear();
            ActivityUtils.replaceFragment(
                    getFragmentManager(),
                    R.id.frame_main,
                    UserOrderDetailFragment.newInstance(mCart.getUser()));
        } else {
            Toast.makeText(getActivity(),
                    AppContext.getInstance().getString(R.string.text_inform_order_failed),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {

    }

    public interface OnCartItemChangedListener {
        void onRemove(List<CartItem> cartItems);
    }

    public void onOrder() {
        mNotificationTitle = AppContext.getInstance().getString(R.string.text_cart_id) + mCart.getId();
        mNotificationMessage = AppContext.getInstance().getString(R.string.text_customer) + mCart.getUser().getName() + " | " + AppContext.getInstance().getString(R.string.text_number) + mCart.getTable().getNumber();
        if (mCartItems.size() > 0) {
            for (CartItem cartItem : mCartItems) {
                mPresenter.uploadCartItem(
                        cartItem.getItem().getId(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        mCart.getId());
            }
            mPresenter.uploadCart(
                    mCart.getId(),
                    mCart.getUser().getId(),
                    mCart.getTable().getNumber(),
                    mCart.getPrice());
        } else {
            Toast.makeText(getActivity(),
                    AppContext.getInstance().getText(R.string.text_cart_empty),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
