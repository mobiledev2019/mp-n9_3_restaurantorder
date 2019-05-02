package com.e15cn2.restaurantorder.screen.main.admin;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.ActivityMainBinding;
import com.e15cn2.restaurantorder.screen.base.BaseActivity;
import com.e15cn2.restaurantorder.screen.landing.LandingActivity;
import com.e15cn2.restaurantorder.screen.main.admin.add_menu.AddMenuFragment;
import com.e15cn2.restaurantorder.screen.main.admin.menu.MenuFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.SharedPreferenceUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainActivity extends BaseActivity<ActivityMainBinding>
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_USER =
            "com.e15cn2.restaurantorder.screen.main.admin.EXTRA_USER";
    private User mUser;
    private String mFragmentClassName;

    public static Intent getAdminMainIntent(Context context, User user) {
        Intent intent = new Intent(context, AdminMainActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            mUser = getIntent().getParcelableExtra(EXTRA_USER);
            setUserInfor(mUser);
        }
        setActionBar();
        fragmentListener();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentClassName.equals(MenuFragment.class.getName())) {
            return;
        } else {
            super.onBackPressed();
        }
        if (binding.drawerMain.isDrawerOpen(GravityCompat.START)) {
            binding.drawerMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.action_menu:
                actionMenu();
                break;
            case R.id.action_sign_out:
                signOut();
                break;
        }

        binding.drawerMain.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setActionBar() {
        setSupportActionBar(binding.includeAppBarMain.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToggleState();
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            binding.includeAppBarMain.toolbar.setNavigationIcon(R.drawable.ic_back_toolbar);
            binding.includeAppBarMain.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().popBackStack();
                }
            });
        }
    }

    private void setToggleState() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerMain,
                binding.includeAppBarMain.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawerMain.addDrawerListener(toggle);
        toggle.syncState();
        binding.includeAppBarMain.toolbar.setNavigationIcon(R.drawable.ic_menu_toolbar);
        binding.navMain.setNavigationItemSelectedListener(this);
    }

    private void signOut() {
        SharedPreferenceUtils.getInstance(this).saveSignInState(false);
        startActivity(new Intent(this, LandingActivity.class));
        finish();
    }

    private void setUserInfor(User user) {
        View hView = binding.navMain.getHeaderView(0);
        CircleImageView imageUser = hView.findViewById(R.id.image_user);
        TextView textName = hView.findViewById(R.id.text_name);
        TextView textEmail = hView.findViewById(R.id.text_email);

        if (user != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_person_grey_default);
            requestOptions.error(R.drawable.ic_person_grey_default);
            Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load(user.getImage()).into(imageUser);
            textName.setText(user.getName());
            textEmail.setText(user.getEmail());
        }
    }

    private void fragmentListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main);
                if (fragment != null) {
                    updateTitle(fragment);
                }
            }
        });
    }

    private void updateTitle(Fragment fragment) {
        mFragmentClassName = fragment.getClass().getName();
        setActionBar();
        if (mFragmentClassName.equals(MenuFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.text_title_menu));
            setToggleState();
        } else if (mFragmentClassName.equals(AddMenuFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.text_title_add_menu));
        }
    }

    private void actionMenu() {
        ActivityUtils.replaceFragment(
                getSupportFragmentManager(),
                R.id.frame_main,
                MenuFragment.newInstance());
    }
}
