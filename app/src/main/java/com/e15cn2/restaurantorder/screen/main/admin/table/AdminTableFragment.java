package com.e15cn2.restaurantorder.screen.main.admin.table;

import android.support.v4.app.Fragment;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentTableAdminBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.add_table.AddTableFragment;
import com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.AdminTablesListFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AdminTableFragment extends BaseFragment<FragmentTableAdminBinding>
        implements AdminTableContract.View {
    private AdminTableContract.Presenter mPresenter;
    private AdminTableViewPager mPagerAdapter;
    List<Fragment> mFragments;

    public static AdminTableFragment newInstance() {
        return new AdminTableFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_table_admin;
    }

    @Override
    protected void initData() {
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
        mFragments = new ArrayList<>();
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
        mFragments.add(AdminTablesListFragment.newInstance(tables4));
        mFragments.add(AdminTablesListFragment.newInstance(tables6));
        //init viewpager
        mPagerAdapter = new AdminTableViewPager(
                getChildFragmentManager(),
                tabs,
                mFragments);
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.tabType.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void showFailureMessage(String msg) {
        //Belong to dev
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getTables();
    }

    public void onFabAddClicked() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_main,
                AddTableFragment.newInstance());
    }
}
