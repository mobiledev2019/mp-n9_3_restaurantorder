package com.e15cn2.restaurantorder.screen.main.user.table;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.e15cn2.restaurantorder.screen.main.admin.table.AdminTableViewPager;

import java.util.List;

public class UserTableViewPager extends AdminTableViewPager {

    public UserTableViewPager(FragmentManager fm, List<String> tabs, List<Fragment> fragments) {
        super(fm, tabs, fragments);
    }
}