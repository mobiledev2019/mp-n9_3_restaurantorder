package com.e15cn2.restaurantorder.screen.main.admin.table;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentTableAdminBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.add_table.AddTableFragment;
import com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.TablesListFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.e15cn2.restaurantorder.utils.Constants.UserKey.IS_ADMIN;

public class AdminTableFragment extends BaseFragment<FragmentTableAdminBinding>
        implements AdminTableContract.View {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private AdminTableContract.Presenter mPresenter;
    private AdminTableViewPager mPagerAdapter;
    private User mUser;

    public static AdminTableFragment newInstance(User user) {
        AdminTableFragment fragment = new AdminTableFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_table_admin;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARGUMENT_USER);
        }
        mPresenter = new AdminTablePresenter(
                TableRepository.getInstance(TableRemoteDataSource.getInstance()), this);
        binding.setListener(this);
        mPresenter.getTables();
    }

    @Override
    public void onGetTablesSuccess(List<Table> tables) {
        List<Table> tables4 = new ArrayList<>();
        List<Table> tables6 = new ArrayList<>();
        List<String> tabs = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        for (Table table : tables) {
            if (table.getType().equals(Constants.TableKey.TYPE_4_PEOPLES)) {
                tables4.add(table);
            } else if (table.getType().equals(Constants.TableKey.TYPE_6_PEOPLES)) {
                tables6.add(table);
            }
            if (!tabs.contains(table.getType().trim().toLowerCase())) {
                tabs.add(table.getType().trim().toLowerCase());
            }
        }
        fragments.add(TablesListFragment.newInstance(tables4, mUser));
        fragments.add(TablesListFragment.newInstance(tables6, mUser));
        //init viewpager
        setViewPager(tabs, fragments);
    }

    @Override
    public void showFailureMessage(String msg) {
        //Belong to dev
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mUser.getIsAdmin() == IS_ADMIN) {
            setFabAddVisible();
        }
        mPresenter.getTables();
    }

    @Override
    public void onStop() {
        super.onStop();
        setFabAddGone();
    }

    public void onFabAddClicked() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_main,
                AddTableFragment.newInstance());
    }

    @SuppressLint("RestrictedApi")
    public void setFabAddVisible() {
        binding.fabAdd.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    public void setFabAddGone() {
        binding.fabAdd.setVisibility(View.GONE);
    }

    private void setViewPager(List<String> tabs, List<Fragment> fragments) {
        mPagerAdapter = new AdminTableViewPager(
                getChildFragmentManager(),
                tabs,
                fragments);
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.tabType.setupWithViewPager(binding.viewPager);
    }

}
