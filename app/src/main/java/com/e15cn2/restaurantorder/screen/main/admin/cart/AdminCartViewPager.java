package com.e15cn2.restaurantorder.screen.main.admin.cart;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.e15cn2.restaurantorder.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class AdminCartViewPager extends FragmentStatePagerAdapter {
    private List<String> mTabs;
    private List<Fragment> mFragments;

    public AdminCartViewPager(
            FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mTabs = Arrays.asList(
                Constants.TabName.IN_PROGRESS,
                Constants.TabName.DISTRIBUTED,
                Constants.TabName.PAID,
                Constants.TabName.CANCELED);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments == null ? null : mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position);
    }
}
