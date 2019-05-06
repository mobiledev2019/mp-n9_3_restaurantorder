package com.e15cn2.restaurantorder.screen.main.admin.add_menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.repository.MenuRepository;
import com.e15cn2.restaurantorder.data.source.remote.MenuRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentAddMenuBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.menu.MenuFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.RealPathUtils;
import com.e15cn2.restaurantorder.utils.StringUtils;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddMenuFragment extends BaseFragment<FragmentAddMenuBinding>
        implements AddMenuContract.View {
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private String mImagePath;
    private String mImageName;
    private String mImageCode;
    private Bitmap mBitmap;
    private boolean mIsImageSelected;
    private AddMenuContract.Presenter mPresenter;

    public static AddMenuFragment newInstance() {
        return new AddMenuFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_menu;
    }

    @Override
    protected void initData() {
        mIsImageSelected = false;
        mPresenter = new AddMenuPresenter(
                MenuRepository.getInstance(MenuRemoteDataSource.getInstance()), this);
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
        //belong to dev
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_add_menu_success), Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_add_menu_failed), Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage() {
        if (checkPermissions()) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 777);
        }
    }

    public void saveMenu() {
        if (StringUtils.isEmpty(binding.textName)) {
            return;
        } else if (!mIsImageSelected) {
            Toast.makeText(getActivity(), getActivity().getText(R.string.text_notify_upload_image), Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.addMenu(binding.textName.getText().toString().trim(), mImageName, mImageCode);
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
}
