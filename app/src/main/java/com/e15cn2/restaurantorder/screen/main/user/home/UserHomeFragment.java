package com.e15cn2.restaurantorder.screen.main.user.home;

import android.os.Bundle;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.FragmentUserHomeBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;

public class UserHomeFragment extends BaseFragment<FragmentUserHomeBinding> {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private User mUser;

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

    public void onMakeOrderClick(){
        //TODO
        Toast.makeText(getActivity(), "onMakeOrderClick", Toast.LENGTH_SHORT).show();
    }

    public void onReserveTableClick(){
        //TODO
        Toast.makeText(getActivity(), "onReserveTableClick", Toast.LENGTH_SHORT).show();
    }
}
