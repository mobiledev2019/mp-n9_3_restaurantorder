package com.e15cn2.restaurantorder.screen.main.admin.table.tables_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.table.table_details.TableDetailsDialogFragment;
import com.e15cn2.restaurantorder.screen.main.user.home.UserHomeFragment;
import com.e15cn2.restaurantorder.screen.main.user.reserve_table.ReserveTableDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.e15cn2.restaurantorder.utils.Constants.UserKey.IS_ADMIN;

public class TablesListFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements OnTableClickListener, SearchView.OnQueryTextListener {
    private static final String EXTRA_POSITION
            = "com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.EXTRA_POSITION";
    private static final String EXTRA_STATUS
            = "com.e15cn2.restaurantorder.screen.main.admin.table.tables_list.EXTRA_STATUS";
    private static final String ARGUMENT_TABLES = "ARGUMENT_TABLES";
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private static final String ARGUMENT_ACTION = "ARGUMENT_ACTION";
    private static final int REQUEST_DIALOG = 1861;
    private BaseAdapter<Table> mAdapter;
    private List<Table> mTables;
    private User mUser;
    private String mAction;
    private OnTableClickListener mCallback;

    public static TablesListFragment newInstance(List<Table> tables, User user) {
        TablesListFragment fragment = new TablesListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_TABLES, (ArrayList<? extends Parcelable>) tables);
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }


    public static TablesListFragment newInstance(List<Table> tables, User user, String action) {
        TablesListFragment fragment = new TablesListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_TABLES, (ArrayList<? extends Parcelable>) tables);
        args.putParcelable(ARGUMENT_USER, user);
        args.putString(ARGUMENT_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    public static Intent getTableIntent(int position, int status) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_STATUS, status);
        return intent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnTableClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
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
            mUser = getArguments().getParcelable(ARGUMENT_USER);
            mAction = getArguments().getString(ARGUMENT_ACTION);
            mAdapter.setData(mTables);
            mAdapter.setListener(this);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onTableClicked(Table table, int position) {
        if (mUser.getIsAdmin() != IS_ADMIN && mAction.equals(UserHomeFragment.Action.RESERVE)) {
            assert getFragmentManager() != null;
            ReserveTableDialogFragment.newInstance(table, mUser)
                    .show(getFragmentManager(), null);
        } else if (mUser.getIsAdmin() != IS_ADMIN && mAction.equals(UserHomeFragment.Action.ORDER)) {
            mCallback.onMakeAnOrder(table);
        } else {
            TableDetailsDialogFragment dialog = TableDetailsDialogFragment.newInstance(table, position);
            dialog.setTargetFragment(this, REQUEST_DIALOG);
            assert getFragmentManager() != null;
            dialog.show(getFragmentManager(), null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIALOG && resultCode == RESULT_OK) {
            int position = data.getIntExtra(EXTRA_POSITION, 0);
            int status = data.getIntExtra(EXTRA_STATUS, 0);
            mTables.get(position).setStatus(status);
            mAdapter.setData(mTables);
        }
    }

    public interface OnTableClickListener {
        void onMakeAnOrder(Table table);
    }
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.admin_home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.getFilter().filter(s);
        return true;
    }
}
