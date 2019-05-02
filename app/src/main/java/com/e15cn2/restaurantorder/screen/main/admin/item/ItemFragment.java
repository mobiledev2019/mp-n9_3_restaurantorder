package com.e15cn2.restaurantorder.screen.main.admin.item;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.repository.ItemRepository;
import com.e15cn2.restaurantorder.data.source.remote.ItemRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements ItemContract.View, OnItemClickListener {
    private static final String ARGUMENT_MENU = "ARGUMENT_MENU";
    private ItemContract.Presenter mPresenter;
    private BaseAdapter<Item> mAdapter;

    public static ItemFragment newInstance(Menu menu) {
        ItemFragment fragment = new ItemFragment();
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
        mPresenter = new ItemPresenter(
                ItemRepository.getInstance(ItemRemoteDataSource.getInstance()), this);
        mPresenter.getItems();
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_item);
        mAdapter.setListener(this);
        binding.recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showItems(List<Item> items) {
        List<Item> itemsShow = new ArrayList<>();
        if (getArguments() != null) {
            Menu menu = getArguments().getParcelable(ARGUMENT_MENU);
            for (Item item : items) {
                if (item.getMenu().equals(menu.getName())) {
                    itemsShow.add(item);
                }
            }
        }
        mAdapter.setDatas(itemsShow);
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            mPresenter.getItems();
        } else {
         //TODO handle error action
        }
    }

    @Override
    public void onMenuClicked(Item item, int position) {
        //TODO
    }

    @Override
    public boolean onMenuLongClicked(Item item, int position) {
        //TODO
        return false;
    }

    @Override
    public void onButtonOnClicked(Item item, int position) {
        mPresenter.updateItemStatus(item.getId(),Constants.JsonItemKey.IS_ON);
    }

    @Override
    public void onButtonOffClicked(Item item, int position) {
        mPresenter.updateItemStatus(item.getId(),Constants.JsonItemKey.IS_OFF);
    }
}