package com.e15cn2.restaurantorder.screen.main.admin.add_item;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.repository.ItemRepository;
import com.e15cn2.restaurantorder.data.source.remote.ItemRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentAddItemBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.menu.MenuFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.RealPathUtils;
import com.e15cn2.restaurantorder.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends BaseFragment<FragmentAddItemBinding>
        implements AddItemContract.View, AdapterView.OnItemSelectedListener {
    private static final String ARGUMENT_MENUS = "ARGUMENT_MENUS";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private String mImagePath;
    private String mImageName;
    private String mImageCode;
    private Bitmap mBitmap;
    private boolean mIsImageSelected;
    private boolean mIsMenuSelected;
    private AddItemContract.Presenter mPresenter;
    private SpinnerAdapter mAdapter;
    private List<String> mMenuNames;
    private List<Menu> mMenus;

    public static AddItemFragment newInstance(List<Menu> menus) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_MENUS, (ArrayList<? extends Parcelable>) menus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_item;
    }

    @Override
    protected void initData() {
        addMenuSpinner();
        mIsImageSelected = false;
        mIsMenuSelected = false;
        mPresenter = new AddItemPresenter(
                ItemRepository.getInstance(ItemRemoteDataSource.getInstance()), this);
        mAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, mMenuNames);
        binding.spinnerMenu.setAdapter(mAdapter);
        binding.spinnerMenu.setOnItemSelectedListener(this);
        binding.setListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == RESULT_OK) {
            mImagePath = RealPathUtils.getPath(getActivity(), data.getData());
            Uri uri = Uri.fromFile(new File(mImagePath));
            // Get name
            mImageName = mImagePath.substring(mImagePath.lastIndexOf("/") + 1);
            mImageName = StringUtils.appendTimeToString(mImageName);
            try {
                //Get BitMap
                mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                mImageCode = mPresenter.encodeImage(mBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Load image
            binding.imagePreview.setVisibility(View.GONE);
            Glide.with(getActivity()).load(uri).into(binding.imageMenu);
            mIsImageSelected = true;
        }
    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_add_item_success), Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_add_item_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            mIsMenuSelected = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mIsMenuSelected = false;
    }

    public void uploadImage() {
        if (checkPermissions()) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 777);
        }
    }

    public void saveItem() {
        if (StringUtils.isEmpty(binding.textName, binding.textPrice)) {
            return;
        } else if (!mIsImageSelected) {
            Toast.makeText(getActivity(), getActivity().getText(R.string.text_notify_upload_image), Toast.LENGTH_SHORT).show();
        } else if (!mIsMenuSelected) {
            Toast.makeText(getActivity(), getActivity().getText(R.string.text_notify_choose_menu), Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.addItem(
                    binding.textName.getText().toString().trim(),
                    binding.spinnerMenu.getSelectedItem().toString().trim(),
                    Double.parseDouble(binding.textPrice.getText().toString().trim()),
                    binding.textDesc.getText().toString().trim(),
                    mImageName,
                    mImageCode);
        }
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(getActivity(), p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSIONS, 0);
                    return false;
                }
            }
        }
        return true;
    }

    private void addMenuSpinner() {
        mMenuNames = new ArrayList<>();
        mMenuNames.add(getActivity().getString(R.string.text_choose_item_menu));
        if (getArguments() != null) {
            mMenus = getArguments().getParcelableArrayList(ARGUMENT_MENUS);
            for (Menu menu : mMenus) {
                mMenuNames.add(menu.getName());
            }
        }
    }
}
