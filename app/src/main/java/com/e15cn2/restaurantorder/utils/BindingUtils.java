package com.e15cn2.restaurantorder.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.e15cn2.restaurantorder.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class BindingUtils {
    @BindingAdapter({"src"})
    public static void setImageRes(ImageView image, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_item_null);
        requestOptions.error(R.drawable.ic_item_null);
        Glide.with(image.getContext()).setDefaultRequestOptions(requestOptions).load(url).into(image);
    }

    @BindingAdapter({"currencyFormat"})
    public static void currencyFormat(TextView textView, double price) {
        NumberFormat format =
                new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        String currency = String.valueOf(price);
        currency = (!TextUtils.isEmpty(currency)) ? currency : "0";
        currency = currency.trim();
        currency = format.format(Double.parseDouble(currency));
        currency = currency.replaceAll(",", "\\.");

        if (currency.endsWith(".00")) {
            int centsIndex = currency.lastIndexOf(".00");
            if (centsIndex != -1) {
                currency = currency.substring(0, centsIndex);
            }
        }
        currency = String.format("%s đ", currency);
        textView.setText(currency);
    }
}
