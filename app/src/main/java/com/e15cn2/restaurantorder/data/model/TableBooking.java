package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TableBooking implements Serializable, Parcelable {
    @SerializedName(Constants.JSonTableBookingKey.ID)
    private long mId;
    @SerializedName(Constants.JSonTableBookingKey.TABLE_NUMBER)
    private int mTableNumber;
    @SerializedName(Constants.JSonTableBookingKey.TIME_BOOKING)
    private String mTimeBooking;
    @SerializedName(Constants.JSonTableBookingKey.USER_NAME)
    private String mUsername;
    @SerializedName(Constants.JSonTableBookingKey.USER_EMAIL)
    private String mUserEmail;
    @SerializedName(Constants.JSonTableBookingKey.USER_PHONE)
    private String mUserPhone;

    public TableBooking(long id, int tableNumber, String timeBooking, String username, String userEmail, String userPhone) {
        mId = id;
        mTableNumber = tableNumber;
        mTimeBooking = timeBooking;
        mUsername = username;
        mUserEmail = userEmail;
        mUserPhone = userPhone;
    }

    protected TableBooking(Parcel in) {
        mId = in.readLong();
        mTableNumber = in.readInt();
        mTimeBooking = in.readString();
        mUsername = in.readString();
        mUserEmail = in.readString();
        mUserPhone = in.readString();
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

    public long getId() {
        return mId;
    }

    public int getTableNumber() {
        return mTableNumber;
    }

    public String getTimeBooking() {
        return mTimeBooking;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public String getUserPhone() {
        return mUserPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeInt(mTableNumber);
        dest.writeString(mTimeBooking);
        dest.writeString(mUsername);
        dest.writeString(mUserEmail);
        dest.writeString(mUserPhone);
    }

    @Override
    public String toString() {
        return "TableBooking{" +
                "mId=" + mId +
                ", mTableNumber=" + mTableNumber +
                ", mTimeBooking='" + mTimeBooking + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mUserEmail='" + mUserEmail + '\'' +
                ", mUserPhone='" + mUserPhone + '\'' +
                '}';
    }
}
