package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable, Parcelable {
    private long mId;
    private User mUser;
    private Table mTable;
    private List<CartItem> mCartItems;
    private double mPrice;
    private String time;

    public Cart(long id, User user, Table table, List<CartItem> cartItems, double price) {
        mId = id;
        mUser = user;
        mTable = table;
        mCartItems = cartItems;
        mPrice = price;
    }

    protected Cart(Parcel in) {
        mId = in.readLong();
        mUser = in.readParcelable(User.class.getClassLoader());
        mTable = in.readParcelable(Table.class.getClassLoader());
        mCartItems = in.createTypedArrayList(CartItem.CREATOR);
        mPrice = in.readDouble();
        time = in.readString();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public long getId() {
        return mId;
    }

    public User getUser() {
        return mUser;
    }

    public Table getTable() {
        return mTable;
    }

    public List<CartItem> getCartItems() {
        return mCartItems;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeParcelable(mUser, flags);
        dest.writeParcelable(mTable, flags);
        dest.writeTypedList(mCartItems);
        dest.writeDouble(mPrice);
        dest.writeString(time);
    }
}
