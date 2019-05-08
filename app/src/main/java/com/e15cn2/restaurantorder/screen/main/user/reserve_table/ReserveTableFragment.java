package com.e15cn2.restaurantorder.screen.main.user.reserve_table;

import android.os.Bundle;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentUserReserveTableBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.add_item.SpinnerAdapter;
import com.e15cn2.restaurantorder.screen.main.admin.table.AdminTableContract;
import com.e15cn2.restaurantorder.screen.main.admin.table.AdminTablePresenter;

import java.util.ArrayList;
import java.util.List;

public class ReserveTableFragment extends BaseFragment<FragmentUserReserveTableBinding>
        implements AdminTableContract.View {
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private BaseAdapter<Table> mTableAdapter;
    private SpinnerAdapter mSpinnerTypes;
    private SpinnerAdapter mSpinnerTimes;
    private List<Table> mTables;
    private User mUser;
    private AdminTableContract.Presenter mPresenter;

    public static ReserveTableFragment newInstance(User user) {
        ReserveTableFragment fragment = new ReserveTableFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_reserve_table;
    }

    @Override
    protected void initData() {
        mPresenter = new AdminTablePresenter(
                TableRepository.getInstance(TableRemoteDataSource.getInstance()), this);
        mPresenter.getTables();
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARGUMENT_USER);
        }
    }

    @Override
    public void onGetTablesSuccess(List<Table> tables) {
        List<Table> availableTables = new ArrayList<>();

        for (Table table: tables){
            if (table.getStatus()==1){

            }
        }
        mTableAdapter = new BaseAdapter<>()
    }

    @Override
    public void showFailureMessage(String msg) {

    }
}
