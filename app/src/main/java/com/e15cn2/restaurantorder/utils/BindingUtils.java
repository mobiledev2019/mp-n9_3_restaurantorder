package com.e15cn2.restaurantorder.utils;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.model.CartItem;
import com.e15cn2.restaurantorder.data.model.TableBooking;
import com.e15cn2.restaurantorder.screen.base.BaseAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.e15cn2.restaurantorder.utils.Constants.JsonCommonKey.IS_OFF;
import static com.e15cn2.restaurantorder.utils.Constants.JsonItemKey.IS_ON;

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

    @BindingAdapter({"isCheckedSwitchOff"})
    public static void setCheckedButtonOff(RadioButton checkedButton, int status) {
        if (status == Constants.JsonItemKey.IS_OFF)
            checkedButton.setChecked(true);
        else checkedButton.setChecked(false);

    }

    @BindingAdapter({"isCheckedSwitchOn"})
    public static void setCheckedButtonOn(RadioButton checkedButton, int status) {
        if (status == IS_ON)
            checkedButton.setChecked(true);
        else checkedButton.setChecked(false);
    }

    @BindingAdapter({"srcTableType"})
    public static void setTableImageByType(ImageView image, String type) {
        if (type.equals(Constants.TableKey.TYPE_4_PEOPLES)) {
            image.setImageResource(R.drawable.ic_table_4);
        } else if (type.equals(Constants.TableKey.TYPE_6_PEOPLES)) {
            image.setImageResource(R.drawable.ic_table_6);
        }
    }

    @BindingAdapter({"tableStatus"})
    public static void setTableStatus(ImageView image, int status) {
        if (status == IS_ON) {
            image.setImageResource(R.drawable.ic_status_green);
        } else if (status == IS_OFF) {
            image.setImageResource(R.drawable.ic_status_grey);
        }
    }

    @BindingAdapter({"recyclerTableBooking"})
    public static void setRecyclerViewTableBooking(RecyclerView recyclerView, final List<TableBooking> tableBookings) {
        final BaseAdapter<TableBooking> adapter = new BaseAdapter<>(
                recyclerView.getContext(),
                R.layout.item_table_booking);
        adapter.setData(tableBookings);
        recyclerView.setAdapter(adapter);
        createTouchHelper(recyclerView, adapter, tableBookings);

    }

    @BindingAdapter({"recyclerCartItems"})
    public static void setRecyclerViewCartItems(RecyclerView recyclerView, List<CartItem> cartItems) {
        BaseAdapter<CartItem> adapter = new BaseAdapter<>(
                recyclerView.getContext(),
                R.layout.item_cart_item);
        adapter.setData(cartItems);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("quantity")
    public static void setQuantity(TextView textView, int quantity) {
        textView.setText(String.valueOf(quantity));
    }

    private static <T> void createTouchHelper(RecyclerView recyclerView, final BaseAdapter<T> adapter, final List<T> data) {
        ItemTouchHelper mHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(data, from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                data.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        mHelper.attachToRecyclerView(recyclerView);
    }

    @BindingAdapter("formatTimeStampSQL")
    public static void formatTimeStampSQL(TextView textView, String timeStamp) {
        String dd = timeStamp.substring(8, 10);
        String MM = timeStamp.substring(5, 7);
        String yyyy = timeStamp.substring(0, 4);
        String time = timeStamp.substring(11, 19);
        textView.setText(time + " " + dd + "/" + MM + "/" + yyyy);
    }

    @BindingAdapter("cartStatus")
    public static void setCartStatus(TextView textStatus, int status) {
        if (status == Constants.JsonCartKey.STATUS_CANCELED) {
            textStatus.setText(Constants.TabName.CANCELED);
        } else if (status == Constants.JsonCartKey.STATUS_PROGRESS) {
            textStatus.setText(Constants.TabName.IN_PROGRESS);
        } else if (status == Constants.JsonCartKey.STATUS_DISTRIBUTED) {
            textStatus.setText(Constants.TabName.DISTRIBUTED);
        } else {
            textStatus.setText(Constants.TabName.PAID);
        }
    }
}
