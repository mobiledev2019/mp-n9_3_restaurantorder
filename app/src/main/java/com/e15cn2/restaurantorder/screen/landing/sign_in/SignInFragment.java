package com.e15cn2.restaurantorder.screen.landing.sign_in;

import android.os.Bundle;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.UserRepository;
import com.e15cn2.restaurantorder.data.source.remote.UserRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentSignInBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.home.HomeActivity;
import com.e15cn2.restaurantorder.screen.landing.sign_up.SignUpFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.StringUtils;

public class SignInFragment extends BaseFragment<FragmentSignInBinding>
        implements SignInContract.View {
    private static final String ARGUMENT_EMAIL = "ARGUMENT_EMAIL";
    private static final String ARGUMENT_PASSWORD = "ARGUMENT_PASSWORD";
    private SignInContract.Presenter mPresenter;
    private User mUser;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    public static SignInFragment newInstance(String email, String password) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_EMAIL, email);
        args.putString(ARGUMENT_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void initData() {
        ActivityUtils.hideSoftKeyboard(getActivity(), binding.getRoot());
        mPresenter = new SignInPresenter(
                UserRepository.getInstance(UserRemoteDataSource.getInstance()), this);
        binding.setListener(this);
        if (getArguments() != null) {
            binding.textEmail.setText(getArguments().getString(ARGUMENT_EMAIL));
            binding.textPassword.setText(getArguments().getString(ARGUMENT_PASSWORD));
        }
    }

    @Override
    public void showUser(User user) {
        //TODO
        mUser = user;
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            //TODO
            onSignInSuccess(mUser);
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_sign_in_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }

    public void signIn() {
        if (!StringUtils.isLongEnough(binding.textEmail, binding.textPassword)) {
            return;
        } else if (!StringUtils.isEmail(binding.textEmail)) {
            return;
        } else {
            mPresenter.signIn(
                    binding.textEmail.getText().toString().trim(),
                    binding.textPassword.getText().toString().trim());
        }
    }

    public void moveToSignUpScreen() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_landing,
                SignUpFragment.newInstance());
    }

    private void onSignInSuccess(User user) {
        startActivity(HomeActivity.getHomeIntent(getActivity(), user));
        getActivity().finish();
    }
}
