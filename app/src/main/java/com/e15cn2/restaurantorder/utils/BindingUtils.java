package com.e15cn2.restaurantorder.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.e15cn2.restaurantorder.R;

public class BindingUtils {
    @BindingAdapter({"src"})
    public static void setImageRes(ImageView image, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_item_null);
        requestOptions.error(R.drawable.ic_item_null);
        Glide.with(image.getContext()).setDefaultRequestOptions(requestOptions).load(url).into(image);
    }
}
