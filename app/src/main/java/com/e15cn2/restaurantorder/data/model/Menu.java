package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu implements Serializable, Parcelable {
    @SerializedName(Constants.JsonMenuKey.ID)
    private int mId;
    @SerializedName(Constants.JsonMenuKey.NAME)
    private String mName;
    @SerializedName(Constants.JsonMenuKey.IMAGE)
    private String mImage;

    public Menu(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mImage = builder.mImage;
    }

    protected Menu(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mImage = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mImage);
    }

    public static class Builder {
        private int mId;
        private String mName;
        private String mImage;

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setImage(String image) {
            mImage = image;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
