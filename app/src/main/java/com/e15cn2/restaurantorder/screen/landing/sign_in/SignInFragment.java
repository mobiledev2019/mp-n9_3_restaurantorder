package com.e15cn2.restaurantorder.screen.landing.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.User;
import com.e15cn2.restaurantorder.data.repository.UserRepository;
import com.e15cn2.restaurantorder.data.source.remote.UserRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentSignInBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.landing.sign_up.SignUpFragment;
import com.e15cn2.restaurantorder.screen.main.admin.AdminMainActivity;
import com.e15cn2.restaurantorder.screen.main.user.UserMainActivity;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.SharedPreferenceUtils;
import com.e15cn2.restaurantorder.utils.StringUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignInFragment extends BaseFragment<FragmentSignInBinding>
        implements SignInContract.View, FacebookCallback<LoginResult> {
    private static final String ARGUMENT_EMAIL = "ARGUMENT_EMAIL";
    private static final String ARGUMENT_PASSWORD = "ARGUMENT_PASSWORD";
    private static final String FB_ID = "id";
    private static final String FB_FIELDS = "fields";
    private static final String FB_NAME = "name";
    private static final String FB_EMAIL = "email";
    private static final String FB_PUBLIC_PROFILE = "public_profile";
    private static final String FB_DOB = "birthday";
    private static final String FB_VALUE = FB_ID + "," + FB_NAME + "," + FB_EMAIL + "," + FB_DOB;
    private static final int RC_SIGN_IN = 123;
    private SignInContract.Presenter mPresenter;
    private User mUser;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;

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
        mCallbackManager = CallbackManager.Factory.create();
        configFacebookSignIn(mCallbackManager);
        configGoogleSignIn();
        onSignInSaved();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!SharedPreferenceUtils.getInstance(getActivity()).getSignInState()) {
            LoginManager.getInstance().logOut();
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Handle particular task
                        }
                    });
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        handleFacebookSignResult();
    }

    @Override
    public void onCancel() {
        //Handle cancel state here
    }

    @Override
    public void onError(FacebookException error) {
        // Handle exp here
    }

    @Override
    public void showUser(User user) {
        mUser = user;
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            onSignInSuccess(mUser);
        } else {
            Toast.makeText(getActivity(), AppContext.getInstance().getString(R.string.text_sign_in_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    public void onFacebookSignIn() {
        binding.buttonFacebookOrigin.performClick();
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList(FB_PUBLIC_PROFILE));
    }

    public void onGoogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signIn() {
        if (!StringUtils.isLongEnough(binding.textEmail, binding.textPassword)) {
            return;
        } else if (!StringUtils.isEmail(binding.textEmail)) {
            return;
        } else {
            mPresenter.signIn(
                    binding.textEmail.getText().toString().trim(),
                    binding.textPassword.getText().toString().trim(),
                    SharedPreferenceUtils.getInstance(getActivity()).getDeviceToken());
        }
    }

    public void moveToSignUpScreen() {
        ActivityUtils.replaceFragment(
                getFragmentManager(),
                R.id.frame_landing,
                SignUpFragment.newInstance());
    }

    private void configGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    private void configFacebookSignIn(CallbackManager callbackManager) {
        binding.buttonFacebookOrigin.setReadPermissions(Arrays.asList(FB_EMAIL, FB_PUBLIC_PROFILE));
        binding.buttonFacebookOrigin.setFragment(this);
        binding.buttonFacebookOrigin.registerCallback(callbackManager, this);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                User user = null;
                if (account.getPhotoUrl() != null) {
                    user = new User.Builder()
                            .setId(account.getId())
                            .setName(account.getDisplayName())
                            .setEmail(account.getEmail())
                            .setImage(account.getPhotoUrl().toString())
                            .build();
                } else {
                    user = new User.Builder()
                            .setId(account.getId())
                            .setName(account.getDisplayName())
                            .setEmail(account.getEmail())
                            .build();
                }

                mPresenter.signUpSocialAccount(
                        user.getId(),
                        user.getName(),
                        user.getDob(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getPassword());
                onSignInSuccess(user);
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void handleFacebookSignResult() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            User user = new User.Builder()
                                    .setId(String.valueOf(response.getJSONObject().getString(FB_ID)))
                                    .setName(response.getJSONObject().getString(FB_NAME))
                                    .setEmail(response.getJSONObject().getString(FB_EMAIL))
                                    .setDob(response.getJSONObject().getString(FB_DOB))
                                    .build();
                            mPresenter.signUpSocialAccount(
                                    user.getId(),
                                    user.getName(),
                                    user.getDob(),
                                    user.getEmail(),
                                    user.getPhone(),
                                    user.getPassword());
                            onSignInSuccess(user);
                            onSignInSuccess(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(FB_FIELDS, FB_VALUE);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void onSignInSuccess(User user) {
        SharedPreferenceUtils.getInstance(getActivity()).saveSignInState(true);
        SharedPreferenceUtils.getInstance(getActivity()).saveUser(user);
        if (user.getIsAdmin() == Constants.UserKey.IS_ADMIN) {
            startActivity(AdminMainActivity.getAdminMainIntent(getActivity(), user));
            getActivity().finish();
        } else {
            startActivity(UserMainActivity.getUserMainIntent(getActivity(), user));
            getActivity().finish();
        }
    }

    private void onSignInSaved() {
        User user = SharedPreferenceUtils.getInstance(getActivity()).getUser();
        if (user != null) {
            if (user.getIsAdmin() == Constants.UserKey.IS_ADMIN) {
                if (SharedPreferenceUtils.getInstance(getActivity()).getSignInState()) {
                    startActivity(AdminMainActivity.getAdminMainIntent(getActivity(), user));
                    getActivity().finish();
                }
            } else {
                if (SharedPreferenceUtils.getInstance(getActivity()).getSignInState()) {
                    startActivity(UserMainActivity.getUserMainIntent(getActivity(), user));
                    getActivity().finish();
                }
            }
        }
    }
}
