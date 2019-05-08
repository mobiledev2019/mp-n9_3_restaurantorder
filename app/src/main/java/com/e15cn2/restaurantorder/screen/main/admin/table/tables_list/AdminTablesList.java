package com.e15cn2.restaurantorder.screen.main.admin.table.tables_list;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AdminTablesList extends BaseFragment<FragmentRecyclerViewBinding> {
    private static final String ARGUMENT_TABLES = "ARGUMENT_TABLES";
    private BaseAdapter<Table> mAdapter;
    private List<Table> mTables;

    public static AdminTablesList newInstance(List<Table> tables) {
        AdminTablesList fragment = new AdminTablesList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_TABLES, (ArrayList<? extends Parcelable>) tables);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_table);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        if (getArguments() != null) {
            mTables = getArguments().getParcelableArrayList(ARGUMENT_TABLES);
            mAdapter.setDatas(mTables);
        }
    }

}
