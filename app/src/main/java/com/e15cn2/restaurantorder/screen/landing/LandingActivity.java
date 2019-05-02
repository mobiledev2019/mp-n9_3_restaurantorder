package com.e15cn2.restaurantorder.screen.landing;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.databinding.ActivityLandingBinding;
import com.e15cn2.restaurantorder.screen.base.BaseActivity;
import com.e15cn2.restaurantorder.screen.landing.sign_in.SignInFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;

public class LandingActivity extends BaseActivity<ActivityLandingBinding> {
    private String mFragmentClassName;

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
        fragmentListener();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentClassName.equals(SignInFragment.class.getName())) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void fragmentListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_landing);
                if (fragment != null) {
                    mFragmentClassName = fragment.getClass().getName();
                }
            }
        });
    }
}
