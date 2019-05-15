package com.e15cn2.restaurantorder.screen.main.admin.table.table_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentDialogTableDetailBinding;
import com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.TablesListFragment;
import com.e15cn2.restaurantorder.utils.Constants;

import static android.app.Activity.RESULT_OK;
import static com.e15cn2.restaurantorder.utils.Constants.JsonCommonKey.IS_OFF;
import static com.e15cn2.restaurantorder.utils.Constants.JsonCommonKey.IS_ON;

public class TableDetailsDialogFragment extends DialogFragment
        implements TableDetailsContract.View {
    private static final String ARGUMENT_TABLE = "ARGUMENT_TABLE";
    private static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";
    private FragmentDialogTableDetailBinding mBinding;
    private TableDetailsContract.Presenter mPresenter;
    private Table mTable;
    private int mPosition;

    public static TableDetailsDialogFragment newInstance(Table table, int position) {
        TableDetailsDialogFragment fragment = new TableDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_TABLE, table);
        args.putInt(ARGUMENT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDialogTableDetailBinding
                .inflate(inflater, container, false);
        initData();
        return mBinding.getRoot();
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }

    @Override
    public void showMessage(String msg) {
        //Delete action
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            //TODO handle success event
        } else {
            //TODO handle error action
        }
    }

    private void initData() {
        mPresenter = new TableDetailsPresenter(
                TableRepository.getInstance(TableRemoteDataSource.getInstance()), this);
        mBinding.setListener(this);
        mBinding.recyclerBooking.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        if (getArguments() != null) {
            mTable = getArguments().getParcelable(ARGUMENT_TABLE);
            mPosition = getArguments().getInt(ARGUMENT_POSITION);
            mBinding.setItem(mTable);
        }
    }

    public void onButtonOnClick() {
        mPresenter.updateTableStatus(mTable.getNumber(), IS_ON);
        assert getTargetFragment() != null;
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, TablesListFragment.getTableIntent(mPosition, IS_ON));
        mTable.setStatus(IS_ON);
        mBinding.setItem(mTable);
    }

    public void onButtonOffClick() {
        mPresenter.updateTableStatus(mTable.getNumber(), IS_OFF);
        assert getTargetFragment() != null;
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, TablesListFragment.getTableIntent(mPosition, IS_OFF));
        mTable.setStatus(IS_OFF);
        mBinding.setItem(mTable);
    }
}
