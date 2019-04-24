package com.e15cn2.restaurantorder.screen.landing.sign_up;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.repository.UserRepository;
import com.e15cn2.restaurantorder.data.source.remote.UserRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentSignUpBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.landing.sign_in.SignInFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.StringUtils;

import java.util.Calendar;

public class SignUpFragment extends BaseFragment<FragmentSignUpBinding> implements SignUpContract.View {
    private SignUpContract.Presenter mPresenter;
    private DatePickerDialog.OnDateSetListener mDate;
    private Calendar mCalendar;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void initData() {
        ActivityUtils.hideSoftKeyboard(getActivity(), binding.getRoot());
        mPresenter = new SignUpPresenter(
                UserRepository.getInstance(UserRemoteDataSource.getInstance()), this);
        mCalendar = Calendar.getInstance();
        pickDateOfBirth();
        binding.setListener(this);
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_sign_up_success), Toast.LENGTH_SHORT).show();
            onSignUpSuccess();
        } else if (msg.equals(Constants.JSonKey.MESSAGE_EXISTED)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_sign_up_existed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_sign_up_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }

    public void showDatePicker() {
        new DatePickerDialog(getActivity(), mDate,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void signUp() {
        if (!StringUtils.isLongEnough(
                binding.textFullName,
                binding.textEmail,
                binding.textPhoneNumber,
                binding.textPassword,
                binding.textConfirmedPassword)) {
            return;
        } else if (!StringUtils.isPhone(binding.textPhoneNumber)) {
            return;
        } else if (!StringUtils.isEqual(binding.textPassword, binding.textConfirmedPassword)) {
            return;
        } else if (!StringUtils.isEmail(binding.textEmail)) {
            return;
        } else {
            mPresenter.signUp(
                    binding.textFullName.getText().toString().trim(),
                    binding.textDob.getText().toString().trim(),
                    binding.textEmail.getText().toString().trim(),
                    binding.textPhoneNumber.getText().toString().trim(),
                    binding.textPassword.getText().toString().trim());
        }

    }

    public void moveToSignInScreen() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_landing,
                SignInFragment.newInstance());
    }

    private void onSignUpSuccess() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_landing,
                SignInFragment.newInstance(
                        binding.textEmail.getText().toString(),
                        binding.textPassword.getText().toString()));
    }

    private void pickDateOfBirth() {
        mDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDobField();
            }
        };
    }

    private void updateDobField() {
        binding.textDob.setText(StringUtils.dateFormat(mCalendar.getTime()));
    }
}
