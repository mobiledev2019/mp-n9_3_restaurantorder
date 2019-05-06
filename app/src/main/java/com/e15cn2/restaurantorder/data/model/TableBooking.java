package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TableBooking implements Serializable, Parcelable {
    @SerializedName(Constants.JSonTableBookingKey.TABLE_ID)
    private int mTableId;
    @SerializedName(Constants.JSonTableBookingKey.TIME_BOOKING)
    private String mBookingTime;
    @SerializedName(Constants.JSonTableBookingKey.USER_EMAIL)
    private String mUserEmail;

    public TableBooking(int tableId, String bookingTime, String userEmail) {
        mTableId = tableId;
        mBookingTime = bookingTime;
        mUserEmail = userEmail;
    }

    protected TableBooking(Parcel in) {
        mTableId = in.readInt();
        mBookingTime = in.readString();
        mUserEmail = in.readString();
    }

    public int getTableId() {
        return mTableId;
    }

    public String getBookingTime() {
        return mBookingTime;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public static final Creator<TableBooking> CREATOR = new Creator<TableBooking>() {
        @Override
        public TableBooking createFromParcel(Parcel in) {
            return new TableBooking(in);
        }

        @Override
        public TableBooking[] newArray(int size) {
            return new TableBooking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTableId);
        dest.writeString(mBookingTime);
        dest.writeString(mUserEmail);
    }
}
