package com.e15cn2.restaurantorder.screen.main.admin.menu;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.repository.MenuRepository;
import com.e15cn2.restaurantorder.data.source.remote.MenuRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.add_menu.AddMenuFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;

import java.util.List;

public class MenuFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements MenuContract.View, OnMenuClickListener {
    private MenuPresenter mPresenter;
    private BaseAdapter<Menu> mAdapter;
    private Animation mFabOpen;
    private Animation mFabClose;
    private Animation mFabRotateForward;
    private Animation mFabRotateBackward;
    private boolean isFabOpen = false;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mPresenter = new MenuPresenter(
                MenuRepository.getInstance(MenuRemoteDataSource.getInstance()), this);
        mPresenter.getMenus();
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_menu);
        mAdapter.setListener(this);
        binding.recyclerView.setAdapter(mAdapter);
        binding.setListenerMenuFrag(this);
    }

    @Override
    public void showMenus(List<Menu> menus) {
        mAdapter.setDatas(menus);
    }


    @Override
    public void showMessage(String msg) {
        //TODO
    }

    @Override
    public void onMenuClicked(Menu menu, int position) {
        //TODO
    }

    @Override
    public boolean onMenuLongClicked(Menu menu, int position) {
        //TODO
        return true;
    }

    public void clickFabAdd() {
        animateFab();
    }

    public void clickFabMenu() {
        animateFab();
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_main,
                AddMenuFragment.newInstance());
    }

    public void clickFabItem() {
        animateFab();
        //TODO
    }

    private void animateFab() {
        mFabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        mFabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        mFabRotateForward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        mFabRotateBackward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        if (isFabOpen) {
            binding.fabAdd.startAnimation(mFabRotateForward);
            binding.fabMenu.startAnimation(mFabClose);
            binding.fabItem.startAnimation(mFabClose);
            binding.fabMenu.setClickable(false);
            binding.fabItem.setClickable(false);
            isFabOpen = false;
        } else {
            binding.fabAdd.startAnimation(mFabRotateBackward);
            binding.fabMenu.startAnimation(mFabOpen);
            binding.fabItem.startAnimation(mFabOpen);
            binding.fabMenu.setClickable(true);
            binding.fabItem.setClickable(true);
            isFabOpen = true;
        }
    }
}
