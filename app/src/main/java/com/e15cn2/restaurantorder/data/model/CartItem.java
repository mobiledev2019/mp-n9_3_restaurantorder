package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CartItem implements Parcelable, Serializable {
    private Item mItem;
    private Table mTable;
    private User mUser;
    private int mQuantity;
    private double mPrice;

    public CartItem(Item item, Table table, User user, int quantity, double price) {
        mItem = item;
        mTable = table;
        mUser = user;
        mQuantity = quantity;
        mPrice = price;
    }

    protected CartItem(Parcel in) {
        mItem = in.readParcelable(Item.class.getClassLoader());
        mTable = in.readParcelable(Table.class.getClassLoader());
        mUser = in.readParcelable(User.class.getClassLoader());
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
        dest.writeParcelable(mTable, flags);
        dest.writeParcelable(mUser, flags);
        dest.writeInt(mQuantity);
        dest.writeDouble(mPrice);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "mItem=" + mItem.getName() +
                ", mTable=" + mTable.getNumber() +
                ", mUser=" + mUser.getName() +
                ", mQuantity=" + mQuantity +
                ", mPrice=" + mPrice +
                '}';
    }
}
