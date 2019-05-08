package com.e15cn2.restaurantorder.screen.main.admin.add_table;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentAddTableBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.add_item.SpinnerAdapter;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddTableFragment extends BaseFragment<FragmentAddTableBinding>
        implements AddTableContract.View, AdapterView.OnItemSelectedListener {
    private AddTableContract.Presenter mPresenter;
    private SpinnerAdapter mAdapter;
    private boolean mIsTableTypeSelected;
    private List<String> mTableTypes;

    public static AddTableFragment newInstance() {
        return new AddTableFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_table;
    }

    @Override
    protected void initData() {
        mIsTableTypeSelected = false;
        mPresenter = new AddTablePresenter(
                TableRepository.getInstance(TableRemoteDataSource.getInstance()), this);
        binding.setListener(this);
        initTableType();
        mAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, mTableTypes);
        binding.spinnerTableType.setAdapter(mAdapter);
        binding.spinnerTableType.setOnItemSelectedListener(this);
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_add_table_success), Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_add_table_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }

    public void saveTable() {
        if (StringUtils.isEmpty(binding.textNumber)) {
            return;
        }
        if (!mIsTableTypeSelected) {
            Toast.makeText(getActivity(), getActivity().getText(R.string.text_notify_choose_table_type), Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.addTable(binding.textNumber.getText().toString().trim(), binding.spinnerTableType.getSelectedItem().toString());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            mIsTableTypeSelected = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mIsTableTypeSelected = false;
    }

    private void initTableType() {
        mTableTypes = new ArrayList<>();
        mTableTypes.add(getActivity().getString(R.string.text_table_type));
        mTableTypes.add(Constants.TableKey.TYPE_4_PEOPLES);
        mTableTypes.add(Constants.TableKey.TYPE_6_PEOPLES);
    }
}
