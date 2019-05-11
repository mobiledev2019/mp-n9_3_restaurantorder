package com.e15cn2.restaurantorder.screen.main.user.home;

import android.os.Bundle;
import android.support.annotation.StringDef;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.FragmentUserHomeBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.user.table.UserTableFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;

import static com.e15cn2.restaurantorder.screen.main.user.home.UserHomeFragment.Action.ORDER;
import static com.e15cn2.restaurantorder.screen.main.user.home.UserHomeFragment.Action.RESERVE;

public class UserHomeFragment extends BaseFragment<FragmentUserHomeBinding> {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private User mUser;

    @StringDef({RESERVE, ORDER})
    public @interface Action {
        String RESERVE = "RESERVE";
        String ORDER = "ORDER";
    }

    public static UserHomeFragment newInstance(User user) {
        UserHomeFragment fragment = new UserHomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_home;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARGUMENT_USER);
        }
        binding.setListener(this);
    }

    public void onMakeOrderClick() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_main,
                UserTableFragment.newInstance(mUser, ORDER)
        );
    }

    public void onReserveTableClick() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_main,
                UserTableFragment.newInstance(mUser, RESERVE));
    }
}
