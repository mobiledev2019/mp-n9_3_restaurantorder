package com.e15cn2.restaurantorder.screen.main.user.reserve_table;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Table;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.FCMRepository;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.FCMRemoteDataSource;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentDialogReserveTableBinding;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.StringUtils;
import com.e15cn2.restaurantorder.utils.widget.DialogInform;

import java.util.Calendar;

public class ReserveTableDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        ReserveTableContract.View {
    private static final String ARGUMENT_TABLE = "ARGUMENT_TABLE";
    private static final String ARGUMENT_USER = "ARGUMENT_USER";
    private FragmentDialogReserveTableBinding mBinding;
    private boolean mIsDateSet;
    private boolean mIsTimeFromSet;
    private boolean mIsTimeToSet;
    private Calendar mCalendar;
    private User mUser;
    private Table mTable;
    private ReserveTableContract.Presenter mPresenter;
    private String mTitleNotification;
    private String mMessageNotification;

    public static ReserveTableDialogFragment newInstance(Table table, User user) {
        ReserveTableDialogFragment fragment = new ReserveTableDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_TABLE, table);
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDialogReserveTableBinding
                .inflate(inflater, container, false);
        initData();
        return mBinding.getRoot();
    }

    private void initData() {
        mPresenter = new ReserveTablePresenter(
                TableRepository.getInstance(TableRemoteDataSource.getInstance()),
                FCMRepository.getInstance(FCMRemoteDataSource.getInstance()),
                this);
        this.setCancelable(false);
        mCalendar = Calendar.getInstance();
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARGUMENT_USER);
            mTable = getArguments().getParcelable(ARGUMENT_TABLE);
            setPhone(mUser);
        }
        mBinding.setListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.textTable.setText(AppContext.getInstance().getString(R.string.text_number) + mTable.getNumber());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateField();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        updateTimeField();
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            DialogInform.showDialog(getParentFragment().getActivity(), AppContext.getInstance().getString(R.string.text_inform_reserve_success));
            mPresenter.pushSmallNotification(mTitleNotification, mMessageNotification);
        } else if (msg.equals(Constants.JSonKey.MESSAGE_EXISTED)) {
            Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_existed_reservation), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_reserve_table_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        //dev handle
    }

    public void onFromClick() {
        mIsTimeFromSet = false;
        if (mIsDateSet) {
            showTimePicker();
        } else {
            Toast.makeText(
                    getActivity(),
                    AppContext.getInstance().getString(R.string.text_notify_pick_date_first),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onToClick() {
        mIsTimeToSet = false;
        if (mIsDateSet) {
            showTimePicker();
        } else {
            Toast.makeText(
                    getActivity(),
                    AppContext.getInstance().getString(R.string.text_notify_pick_date_first),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onCancel() {
        this.dismiss();
    }

    public void onReserve() {
        if (StringUtils.isEmpty(mBinding.textPhone)) {
            return;
        } else if (mBinding.textDate.getText().equals("") || mBinding.textFrom.getText().equals("") || mBinding.textTo.getText().equals("")) {
            Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_notifty_select_time_reserve), Toast.LENGTH_SHORT).show();
        } else {
            String timeBooking =
                    mBinding.textDate.getText().toString().trim() + AppContext.getInstance().getString(R.string.text_from_not_cap) +
                            mBinding.textFrom.getText().toString().trim() + AppContext.getInstance().getString(R.string.text_to) +
                            mBinding.textTo.getText().toString().trim();
            mPresenter.reserveTable(
                    mTable.getNumber(),
                    timeBooking,
                    mUser.getId(),
                    mBinding.textPhone.getText().toString().trim());
            mTitleNotification = AppContext.getInstance().getString(R.string.text_number) + mTable.getNumber();
            mMessageNotification = timeBooking;
            this.dismiss();
        }
    }

    public void showDatePicker() {
        mIsDateSet = false;
        new DatePickerDialog(getActivity(), this,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void showTimePicker() {
        new TimePickerDialog(getActivity(), this,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()))
                .show();
    }

    private void updateDateField() {
        if (StringUtils.compareDate(
                StringUtils.dateFormat(mCalendar.getTime()),
                StringUtils.dateFormat(Calendar.getInstance().getTime())) == 1) {
            mBinding.textDate.setText(StringUtils.dateFormat(mCalendar.getTime()));
            mIsDateSet = true;
        } else {
            Toast.makeText(
                    getActivity(),
                    AppContext.getInstance().getText(R.string.text_date_not_valid),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTimeField() {
        if (!mIsTimeFromSet) {
            mBinding.textFrom.setText(StringUtils.timeFormat(mCalendar.getTime()));
            mIsTimeFromSet = true;
        }
        if (!mIsTimeToSet) {
            mBinding.textTo.setText(StringUtils.timeFormat(mCalendar.getTime()));
            mIsTimeToSet = true;
        }
    }

    private void setPhone(User user) {
        if (user.getPhone() != null) {
            mBinding.textPhone.setText(user.getPhone());
        }
    }

}
