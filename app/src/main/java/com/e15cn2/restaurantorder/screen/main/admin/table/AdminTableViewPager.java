package com.e15cn2.restaurantorder.screen.main.admin.table;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class AdminTableViewPager extends FragmentStatePagerAdapter {

    private List<String> mTabs;
    private List<Fragment> mFragments;

    public AdminTableViewPager(
            FragmentManager fm,
            List<String> tabs,
            List<Fragment> fragments) {
        super(fm);
        mTabs = tabs;
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
