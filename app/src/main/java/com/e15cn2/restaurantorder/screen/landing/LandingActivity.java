package com.e15cn2.restaurantorder.screen.landing;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.databinding.ActivityLandingBinding;
import com.e15cn2.restaurantorder.screen.base.BaseActivity;
import com.e15cn2.restaurantorder.screen.landing.sign_in.SignInFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;

public class LandingActivity extends BaseActivity<ActivityLandingBinding> {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_landing;
    }

    @Override
    protected void initData() {
        ActivityUtils.replaceFragment(
                getSupportFragmentManager(),
                R.id.frame_landing,
                SignInFragment.newInstance());
    }
}
