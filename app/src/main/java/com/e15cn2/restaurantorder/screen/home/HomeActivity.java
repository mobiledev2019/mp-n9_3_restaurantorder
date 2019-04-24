package com.e15cn2.restaurantorder.screen.home;

import android.content.Context;
import android.content.Intent;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.ActivityHomeBinding;
import com.e15cn2.restaurantorder.screen.base.BaseActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    public static final String EXTRA_USER =
            "com.e15cn2.restaurantorder.screen.home.EXTRA_USER";

    public static Intent getHomeIntent(Context context, User user) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {

    }
}
