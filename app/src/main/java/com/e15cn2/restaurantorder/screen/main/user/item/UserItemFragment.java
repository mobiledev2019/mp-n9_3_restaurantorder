package com.e15cn2.restaurantorder.screen.main.user.item;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.ItemRepository;
import com.e15cn2.restaurantorder.data.source.remote.ItemRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.user.add_item.AddItemToCartFragment;

import java.util.ArrayList;
import java.util.List;

import static com.e15cn2.restaurantorder.utils.Constants.JsonItemKey.IS_ON;

public class UserItemFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements UserItemContract.View, UserOnItemClickListener {
    private static final String ARGUMENT_MENU = "ARGUMENT_MENU";
    private static final String ARGUMENT_TABLE = "ARGUMENT_TABLE";
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private BaseAdapter<Item> mAdapter;
    private UserItemContract.Presenter mPresenter;
    private static final int COLUMN_COUNT = 2;
    private Table mTable;
    private User mUser;

    public static UserItemFragment newInstance(User user, Table table, Menu menu) {
        UserItemFragment fragment = new UserItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_TABLE, table);
        args.putParcelable(ARGUMENT_MENU, menu);
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mPresenter = new UserItemPresenter(
                ItemRepository.getInstance(ItemRemoteDataSource.getInstance()), this);
        mPresenter.getItems();
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_item_user);
        mAdapter.setListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        if (getArguments() != null) {
            mTable = getArguments().getParcelable(ARGUMENT_TABLE);
            mUser = getArguments().getParcelable(ARGUMENT_USER);
        }
    }

    @Override
    public void showItems(List<Item> items) {
        List<Item> itemsShow = new ArrayList<>();
        if (getArguments() != null) {
            Menu menu = getArguments().getParcelable(ARGUMENT_MENU);
            for (Item item : items) {
                if (item.getMenu().equals(menu.getName()) && item.getStatus() == IS_ON) {
                    itemsShow.add(item);
                }
            }
        }
        mAdapter.setData(itemsShow);
    }

    @Override
    public void showError(Exception e) {
        // belong to dev
    }

    @Override
    public void showMessage(String msg) {
        //TODO Handle message
    }

    @Override
    public void onItemClicked(Item item, int position) {
        assert getFragmentManager() != null;
        AddItemToCartFragment.newInstance(mUser, mTable, item)
                .show(getFragmentManager(), null);
    }
}
