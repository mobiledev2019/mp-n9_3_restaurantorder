package com.e15cn2.restaurantorder.screen.main.user.table;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentTableUserBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.TablesListFragment;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.e15cn2.restaurantorder.utils.Constants.JsonCommonKey.IS_ON;

public class UserTableFragment extends BaseFragment<FragmentTableUserBinding>
        implements UserTableContract.View {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private static final String ARGUMENT_ACTION = "ARGUMENT_ACTION";
    private UserTableContract.Presenter mPresenter;
    private UserTableViewPager mPagerAdapter;
    private User mUser;
    private String mAction;

    public static UserTableFragment newInstance(User user, String action) {
        UserTableFragment fragment = new UserTableFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        args.putString(ARGUMENT_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_table_user;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARGUMENT_USER);
            mAction = getArguments().getString(ARGUMENT_ACTION);
        }
        mPresenter = new UserTablePresenter(
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
        fragments.add(TablesListFragment.newInstance(tables4, mUser, mAction));
        fragments.add(TablesListFragment.newInstance(tables6, mUser, mAction));
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
        mPresenter.getTables();
    }

    private void setViewPager(List<String> tabs, List<Fragment> fragments) {
        mPagerAdapter = new UserTableViewPager(
                getChildFragmentManager(),
                tabs,
                fragments);
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.tabType.setupWithViewPager(binding.viewPager);
    }
}
