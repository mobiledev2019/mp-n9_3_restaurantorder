package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

public class CartItem implements Parcelable {
    @SerializedName(Constants.JsonCartItemKey.ITEM)
    private Item mItem;
    @SerializedName(Constants.JsonCartItemKey.QUANTITY)
    private int mQuantity;
    @SerializedName(Constants.JsonCartItemKey.PRICE)
    private double mPrice;

    public CartItem(Item item, int quantity, double price) {
        mItem = item;
        mQuantity = quantity;
        mPrice = price;
    }

    public Item getItem() {
        return mItem;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public double getPrice() {
        return mPrice;
    }

    protected CartItem(Parcel in) {
        mItem = in.readParcelable(Item.class.getClassLoader());
        mQuantity = in.readInt();
        mPrice = in.readDouble();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mItem, flags);
        dest.writeInt(mQuantity);
        dest.writeDouble(mPrice);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "mItem=" + mItem.getName() +
                ", mQuantity=" + mQuantity +
                ", mPrice=" + mPrice +
                '}';
    }
}
