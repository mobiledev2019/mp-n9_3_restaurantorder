package com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.databinding.FragmentRecyclerViewBinding;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_send_sms.SendSMSDialogFragment;
import com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_status.CartStatusDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AdminListCartFragment extends BaseFragment<FragmentRecyclerViewBinding>
        implements OnCartClickListener, SearchView.OnQueryTextListener {
    public static final String ACTION_RELOAD =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.ACTION_RELOAD";
    public static final String EXTRA_RELOAD =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_RELOAD";
    public static final String ACTION_SEND =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.ACTION_SEND";
    public static final String EXTRA_SEND =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_SEND";
    public static final String EXTRA_CURRENT_TAB =
            "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_CURRENT_TAB";
    private static final String EXTRA_STATUS
            = "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_STATUS";
    public static final String EXTRA_PHONE
            = "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_PHONE";
    public static final String EXTRA_MESSAGE_SEND
            = "com.e15cn2.restaurantorder.screen.main.admin.cart.list_cart.EXTRA_MESSAGE_SEND";
    private BaseAdapter<Cart> mAdapter;
    private static final String ARGUMENT_CARTS = "ARGUMENT_CARTS";
    private static final int REQUEST_DIALOG_OPTION = 1862;
    private static final int REQUEST_DIALOG_SEND = 1863;
    private List<Cart> mCarts;
    private String[] PERMISSIONS = {Manifest.permission.SEND_SMS};
    private String mMessageSend;

    public static AdminListCartFragment newInstance(List<Cart> carts) {
        AdminListCartFragment fragment = new AdminListCartFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_CARTS, (ArrayList<? extends Parcelable>) carts);
        fragment.setArguments(args);
        return fragment;
    }

    public static Intent getCartIntent(int status) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STATUS, status);
        return intent;
    }

    public static Intent getPhoneIntent(String phone) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PHONE, phone);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        mAdapter = new BaseAdapter<>(getActivity(), R.layout.item_cart_admin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
        if (getArguments() != null) {
            mCarts = getArguments().getParcelableArrayList(ARGUMENT_CARTS);
            mAdapter.setData(mCarts);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onLongClicked(Cart cart, int position) {
        CartStatusDialogFragment dialogFragment = CartStatusDialogFragment.newInstance(cart);
        dialogFragment.setTargetFragment(this, REQUEST_DIALOG_OPTION);
        assert getFragmentManager() != null;
        dialogFragment.show(getFragmentManager(), null);
        return true;
    }

    @Override
    public void onSendSMS(Cart cart) {
        if (checkPermissions()) {
            String item = null;
            for (CartItem cartItem : cart.getCartItems()) {
                item = item + "\n" + cartItem.getItem().getName()
                        + "\nQuantity: " + cartItem.getQuantity()
                        + "\nPrice: " + cartItem.getPrice();
            }
            mMessageSend = "\nCustomer: " + cart.getUser().getName() +
                    "\nEmail: " + cart.getUser().getEmail() +
                    "\nTable number: " + cart.getTable().getNumber() +
                    "\nTable type: " + cart.getTable().getType() +
                    "\nTime Order: " + cart.getTime() +
                    "\nPayment: " + cart.getPrice() +
                    "\nItem: " + item;

            SendSMSDialogFragment smsDialogFragment = SendSMSDialogFragment.newInstance(cart);
            assert getFragmentManager() != null;
            smsDialogFragment.setTargetFragment(this, REQUEST_DIALOG_SEND);
            smsDialogFragment.show(getFragmentManager(), null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIALOG_OPTION && resultCode == RESULT_OK) {
            int status = data.getIntExtra(EXTRA_STATUS, 0);
            Intent intent = new Intent(ACTION_RELOAD);
            intent.putExtra(EXTRA_RELOAD, true);
            intent.putExtra(EXTRA_CURRENT_TAB, status);
            getActivity().sendBroadcast(intent);
        }
        if (requestCode == REQUEST_DIALOG_SEND && resultCode == RESULT_OK) {
            String phone = data.getStringExtra(EXTRA_PHONE);
            Intent intent = new Intent(ACTION_SEND);
            intent.putExtra(EXTRA_SEND, true);
            intent.putExtra(EXTRA_PHONE, phone);
            intent.putExtra(EXTRA_MESSAGE_SEND, mMessageSend);
            getActivity().sendBroadcast(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.admin_home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.getFilter().filter(s);
        return true;
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
