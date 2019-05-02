package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable, Parcelable {
    @SerializedName(Constants.JsonItemKey.ID)
    private int mId;
    @SerializedName(Constants.JsonItemKey.NAME)
    private String mName;
    @SerializedName(Constants.JsonItemKey.MENU)
    private String mMenu;
    @SerializedName(Constants.JsonItemKey.PRICE)
    private double mPrice;
    @SerializedName(Constants.JsonItemKey.DESCRIPTION)
    private String mDescription;
    @SerializedName(Constants.JsonItemKey.IMAGE)
    private String mImage;
    @SerializedName(Constants.JsonItemKey.STATUS)
    private int mStatus;


    protected Item(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mMenu = in.readString();
        mPrice = in.readDouble();
        mDescription = in.readString();
        mImage = in.readString();
        mStatus = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getMenu() {
        return mMenu;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImage() {
        return mImage;
    }

    public int getStatus() {
        return mStatus;
    }

    public Item(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mMenu = builder.mMenu;
        mPrice = builder.mPrice;
        mDescription = builder.mDescription;
        mImage = builder.mImage;
        mStatus = builder.mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mMenu);
        dest.writeDouble(mPrice);
        dest.writeString(mDescription);
        dest.writeString(mImage);
        dest.writeInt(mStatus);
    }


    public static class Builder {
        private int mId;
        private String mName;
        private String mMenu;
        private double mPrice;
        private String mImage;
        private String mDescription;
        private int mStatus;

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setMenu(String menu) {
            mMenu = menu;
            return this;
        }

        public Builder setPrice(double price) {
            mPrice = price;
            return this;
        }

        public Builder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public Builder setImage(String image) {
            mImage = image;
            return this;
        }

        public Builder setStatus(int status) {
            mStatus = status;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
