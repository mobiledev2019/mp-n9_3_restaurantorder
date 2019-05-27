package com.e15cn2.restaurantorder.screen.main.admin.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentCartAdminBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.AdminListCartFragment;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.AdminListCartFragment.ACTION_RELOAD;
import static com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.AdminListCartFragment.ACTION_SEND;
import static com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.AdminListCartFragment.EXTRA_CURRENT_TAB;
import static com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.AdminListCartFragment.EXTRA_RELOAD;

public class AdminCartFragment extends BaseFragment<FragmentCartAdminBinding>
        implements AdminCartContract.View {
    private AdminCartViewPager mPagerAdapter;
    private AdminCartContract.Presenter mPresenter;
    private int mCurrentTab;

    public static AdminCartFragment newInstance() {
        return new AdminCartFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_cart_admin;
    }

    @Override
    protected void initData() {
        mPresenter = new AdminCartPresenter(
                CartRepository.getInstance(CartRemoteDataSource.getInstance()), this);
        mPresenter.getCarts();
        getActivity().registerReceiver(new FragmentReceiver(), new IntentFilter(ACTION_RELOAD));
        getActivity().registerReceiver(new FragmentReceiver(), new IntentFilter(ACTION_SEND));
    }

    @Override
    public void onSuccess(List<Cart> carts) {
        List<Fragment> fragments = new ArrayList<>();
        List<Cart> inProgress = new ArrayList<>();
        List<Cart> distributed = new ArrayList<>();
        List<Cart> paid = new ArrayList<>();
        List<Cart> canceled = new ArrayList<>();
        for (Cart cart : carts) {
            if (cart.getStatus() == Constants.JsonCartKey.STATUS_PROGRESS) {
                inProgress.add(cart);
            } else if (cart.getStatus() == Constants.JsonCartKey.STATUS_DISTRIBUTED) {
                distributed.add(cart);
            } else if (cart.getStatus() == Constants.JsonCartKey.STATUS_PAID) {
                paid.add(cart);
            } else if (cart.getStatus() == Constants.JsonCartKey.STATUS_CANCELED) {
                canceled.add(cart);
            }
        }
        fragments.add(AdminListCartFragment.newInstance(inProgress));
        fragments.add(AdminListCartFragment.newInstance(distributed));
        fragments.add(AdminListCartFragment.newInstance(paid));
        fragments.add(AdminListCartFragment.newInstance(canceled));
        setUpViewPager(fragments);
    }

    @Override
    public void showMessage(String msg) {
//TODO handle later
    }

    private void setUpViewPager(List<Fragment> fragments) {
        mPagerAdapter = new AdminCartViewPager(
                getChildFragmentManager(), fragments);
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.tabCart.setupWithViewPager(binding.viewPager);
        binding.tabCart.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.viewPager.setCurrentItem(mCurrentTab);
    }

    public class FragmentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(EXTRA_RELOAD, false)) {
                mCurrentTab = intent.getIntExtra(EXTRA_CURRENT_TAB, 0);
                mPresenter.getCarts();
            }

        }
    }
}
