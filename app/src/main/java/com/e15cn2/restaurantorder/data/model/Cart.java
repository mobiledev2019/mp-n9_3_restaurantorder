package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart implements Parcelable {
    @SerializedName(Constants.JsonCartKey.ID)
    private long mId;
    @SerializedName(Constants.JsonCartKey.USER)
    private User mUser;
    @SerializedName(Constants.JsonCartKey.TABLE)
    private Table mTable;
    @SerializedName(Constants.JsonCartKey.CART_ITEMS)
    private List<CartItem> mCartItems;
    @SerializedName(Constants.JsonCartKey.PRICE)
    private double mPrice;
    @SerializedName(Constants.JsonCartKey.CART_STATUS)
    private int mStatus;
    @SerializedName(Constants.JsonCartKey.TIME)
    private String mTime;

    public Cart(Builder builder) {
        mId = builder.mId;
        mUser = builder.mUser;
        mTable = builder.mTable;
        mCartItems = builder.mCartItems;
        mPrice = builder.mPrice;
        mStatus = builder.mStatus;
        mTime = builder.mTime;
    }

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

    public int getStatus() {
        return mStatus;
    }

    public String getTime() {
        return mTime;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public static class Builder {
        private long mId;
        private User mUser;
        private Table mTable;
        private List<CartItem> mCartItems;
        private double mPrice;
        private int mStatus;
        private String mTime;

        public Builder setId(long id) {
            mId = id;
            return this;
        }

        public Builder setUser(User user) {
            mUser = user;
            return this;
        }

        public Builder setTable(Table table) {
            mTable = table;
            return this;
        }

        public Builder setCartItems(List<CartItem> cartItems) {
            mCartItems = cartItems;
            return this;
        }

        public Builder setPrice(double price) {
            mPrice = price;
            return this;
        }

        public Builder setStatus(int status) {
            mStatus = status;
            return this;
        }

        public Builder setTime(String time) {
            mTime = time;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

    protected Cart(Parcel in) {
        mId = in.readLong();
        mUser = in.readParcelable(User.class.getClassLoader());
        mTable = in.readParcelable(Table.class.getClassLoader());
        mCartItems = in.createTypedArrayList(CartItem.CREATOR);
        mPrice = in.readDouble();
        mStatus = in.readInt();
        mTime = in.readString();
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

    @Override
    public String toString() {
        return "Cart{" +
                "mId=" + mId +
                ", mUser=" + mUser +
                ", mTable=" + mTable +
                ", mCartItems=" + mCartItems +
                ", mPrice=" + mPrice +
                ", mStatus=" + mStatus +
                ", mTime='" + mTime + '\'' +
                '}';
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
        dest.writeInt(mStatus);
        dest.writeString(mTime);
    }
}
