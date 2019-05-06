package com.e15cn2.restaurantorder.screen.main.user.item;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.repository.ItemRepository;
import com.e15cn2.restaurantorder.data.source.remote.ItemRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.e15cn2.restaurantorder.utils.Constants.JsonItemKey.IS_ON;

public class UserItemFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements UserItemContract.View, UserOnItemClickListener {
    private static final String ARGUMENT_MENU = "ARGUMENT_MENU";
    private BaseAdapter<Item> mAdapter;
    private UserItemContract.Presenter mPresenter;
    private static final int COLUMN_COUNT = 2;

    public static UserItemFragment newInstance(Menu menu) {
        UserItemFragment fragment = new UserItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_MENU, menu);
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
        Log.e("AAA", "showItems: " + itemsShow.size() );
        mAdapter.setDatas(itemsShow);
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
    public void onMenuClicked(Item item, int position) {
        Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
        //TODO
    }
}
