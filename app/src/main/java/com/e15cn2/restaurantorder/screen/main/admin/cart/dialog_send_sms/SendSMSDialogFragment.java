package com.e15cn2.restaurantorder.screen.main.admin.cart.dialog_send_sms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.databinding.FragmentDialogSendSmsBinding;
import com.e15cn2.restaurantorder.utils.StringUtils;

public class SendSMSDialogFragment extends DialogFragment {
    private static final String ARGUMENT_CART = "ARGUMENT_CART";
    private static final String URI_SMS = "smsto:";
    private static final String EXTRA_SMS_BODY = "sms_body";
    private FragmentDialogSendSmsBinding mBinding;
    private Cart mCart;
    private String mCartMessage;

    public static SendSMSDialogFragment newInstance(Cart cart) {
        SendSMSDialogFragment fragment = new SendSMSDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDialogSendSmsBinding
                .inflate(inflater, container, false);
        initData();
        return mBinding.getRoot();
    }

    private void initData() {
        mBinding.setListener(this);
        if (getArguments() != null) {
            mCart = getArguments().getParcelable(ARGUMENT_CART);
            mBinding.setItem(mCart);
            String item = "";
            for (CartItem cartItem : mCart.getCartItems()) {
                item = item + "\n\n" + cartItem.getItem().getName()
                        + "\n" + AppContext.getInstance().getString(R.string.text_bill_quantity) + cartItem.getQuantity()
                        + "\n" + AppContext.getInstance().getString(R.string.text_bill_price) + StringUtils.currencyFormat(cartItem.getPrice());
            }
            mCartMessage = "\n" + AppContext.getInstance().getString(R.string.text_customer) + mCart.getUser().getName() +
                    "\n" + AppContext.getInstance().getString(R.string.text_email_booking) + mCart.getUser().getEmail() +
                    "\n" + AppContext.getInstance().getString(R.string.text_number) + mCart.getTable().getNumber() +
                    "\n" + AppContext.getInstance().getString(R.string.text_bill_table_type) + mCart.getTable().getType() +
                    "\n" + AppContext.getInstance().getString(R.string.text_order_time) + mCart.getTime() +
                    "\n" + AppContext.getInstance().getString(R.string.text_payment) + StringUtils.currencyFormat(mCart.getPrice()) +
                    "\n\n" + AppContext.getInstance().getString(R.string.text_bill_item) + item;


        }
    }

    public void onSend() {
        if (!StringUtils.isPhone(mBinding.textPhone)) {
            return;
        } else {
            Intent intentSend = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse(URI_SMS + mBinding.textPhone.getText().toString().trim());
            intentSend.putExtra(EXTRA_SMS_BODY, mCartMessage);
            intentSend.setData(uri);
            startActivity(intentSend);
            this.dismiss();
        }
    }
}
