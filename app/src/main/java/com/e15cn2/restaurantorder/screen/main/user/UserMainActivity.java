package com.e15cn2.restaurantorder.screen.main.user;


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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.ActivityUserMainBinding;
import com.e15cn2.restaurantorder.screen.base.BaseActivity;
import com.e15cn2.restaurantorder.screen.landing.LandingActivity;
import com.e15cn2.restaurantorder.screen.main.admin.menu.MenuFragment;
import com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.TablesListFragment;
import com.e15cn2.restaurantorder.screen.main.user.add_item.AddItemToCartFragment;
import com.e15cn2.restaurantorder.screen.main.user.cart.CartFragment;
import com.e15cn2.restaurantorder.screen.main.user.home.UserHomeFragment;
import com.e15cn2.restaurantorder.screen.main.user.order_detail.UserOrderDetailFragment;
import com.e15cn2.restaurantorder.screen.main.user.table.UserTableFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.SharedPreferenceUtils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainActivity extends BaseActivity<ActivityUserMainBinding>
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuFragment.OnMenuClickListener, TablesListFragment.OnTableClickListener,
        AddItemToCartFragment.OnCartItemListener, CartFragment.OnCartItemChangedListener {
    public static final String EXTRA_USER =
            "com.e15cn2.restaurantorder.screen.main.user.EXTRA_USER";
    private User mUser;
    private String mFragmentClassName;
    private NotificationBadge mBadge;
    private List<CartItem> mCartItems;
    private Table mTable;

    public static Intent getUserMainIntent(Context context, User user) {
        Intent intent = new Intent(context, UserMainActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user_main;
    }

    @Override
    protected void initData() {
        mCartItems = new ArrayList<>();
        if (getIntent() != null) {
            mUser = getIntent().getParcelableExtra(EXTRA_USER);
            setUserInfor(mUser);
            actionNavigation(UserHomeFragment.newInstance(mUser));
        }
        setActionBar();
        fragmentListener();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentClassName.equals(UserHomeFragment.class.getName())
                || mFragmentClassName.equals(UserOrderDetailFragment.class.getName())) {
            return;
        } else if (binding.drawerMain.isDrawerOpen(GravityCompat.START)) {
            binding.drawerMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        final View view = menu.findItem(R.id.menuCart).getActionView();
        mBadge = view.findViewById(R.id.badge);
        MenuItem item = menu.findItem(R.id.menuCart);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCartItems.size() > 0) {
                    actionNavigation(CartFragment.newInstance(getCart(mCartItems)));
                } else {
                    Toast.makeText(UserMainActivity.this, AppContext.getInstance().getString(R.string.text_cart_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.action_home:
                actionNavigation(UserHomeFragment.newInstance(mUser));
                break;
            case R.id.action_order_detail:
                actionNavigation(UserOrderDetailFragment.newInstance(mUser));
                break;
            case R.id.action_sign_out:
                signOut();
                break;
        }

        binding.drawerMain.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMenuClicked(Menu menu) {
        binding.includeAppBarMain.textToolbarTitle.setText(menu.getName());
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
        SharedPreferenceUtils.getInstance(this).saveSignInState(false);
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
        if (mFragmentClassName.equals(UserHomeFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.action_home));
            setToggleState();
            binding.navMain.getMenu().getItem(0).setChecked(true);
        } else if (mFragmentClassName.equals(UserTableFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.action_table));
        } else if (mFragmentClassName.equals(MenuFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.action_menu));
        } else if (mFragmentClassName.equals(CartFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.action_cart));
        } else if (mFragmentClassName.equals(UserOrderDetailFragment.class.getName())) {
            binding.includeAppBarMain.textToolbarTitle.setText(this.getString(R.string.action_your_orders));
            setToggleState();
            binding.navMain.getMenu().getItem(1).setChecked(true);
        }
    }

    private void actionNavigation(Fragment fragment) {
        ActivityUtils.replaceFragment(
                getSupportFragmentManager(),
                R.id.frame_main,
                fragment);

    }

    public void updateBadgeCount() {
        if (mBadge == null) return;
        if (mCartItems.size() == 0)
            mBadge.setVisibility(View.INVISIBLE);
        else {
            mBadge.setVisibility(View.VISIBLE);
            mBadge.setText(String.valueOf(mCartItems.size()));
        }

    }

    private Cart getCart(List<CartItem> cartItems) {
        double price = 0;
        for (CartItem cartItem : cartItems) {
            price = price + cartItem.getPrice();
        }
        Cart cart = new Cart(System.currentTimeMillis(), mUser, mTable, cartItems, price);
        return cart;
    }

    @Override
    public void onMakeAnOrder(Table table) {
        actionNavigation(MenuFragment.newInstance(mUser, table));
    }

    @Override
    public void onAdded(Table table, CartItem cartItem) {
        mCartItems.add(cartItem);
        mTable = table;
        updateBadgeCount();
    }

    @Override
    public void onRemove(List<CartItem> cartItems) {
        mCartItems = cartItems;
        updateBadgeCount();
    }

    public Cart getNewCart() {
        double price = 0;
        for (CartItem cartItem : mCartItems) {
            price = price + cartItem.getPrice();
        }
        Cart cart = new Cart(System.currentTimeMillis(), mUser, mTable, mCartItems, price);
        return cart;
    }
}
