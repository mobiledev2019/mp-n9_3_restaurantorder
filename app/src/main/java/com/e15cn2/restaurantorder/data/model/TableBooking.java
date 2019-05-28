package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class TableBooking implements Parcelable {
    @SerializedName(Constants.JSonTableBookingKey.BOOKING_ID)
    private long mId;
    @SerializedName(Constants.JSonTableBookingKey.TABLE_NUMBER)
    private int mTableNumber;
    @SerializedName(Constants.JSonTableBookingKey.TIME_BOOKING)
    private String mTimeBooking;
    @SerializedName(Constants.JSonTableBookingKey.USER_NAME)
    private String mUsername;
    @SerializedName(Constants.JSonTableBookingKey.USER_EMAIL)
    private String mUserEmail;
    @SerializedName(Constants.JSonTableBookingKey.PHONE_BOOKING)
    private String mPhoneBooking;

    protected TableBooking(Parcel in) {
        mId = in.readLong();
        mTableNumber = in.readInt();
        mTimeBooking = in.readString();
        mUsername = in.readString();
        mUserEmail = in.readString();
        mPhoneBooking = in.readString();
    }

    public TableBooking(JSONObject jsonObject) throws JSONException {
        mId = jsonObject.getInt(Constants.JSonTableBookingKey.BOOKING_ID);
        mTableNumber = jsonObject.getInt(Constants.JSonTableBookingKey.TABLE_NUMBER);
        mTimeBooking = jsonObject.getString(Constants.JSonTableBookingKey.TIME_BOOKING);
        mUsername = jsonObject.getString(Constants.JSonTableBookingKey.USER_NAME);
        mUserEmail = jsonObject.getString(Constants.JSonTableBookingKey.USER_EMAIL);
        mPhoneBooking = jsonObject.getString(Constants.JSonTableBookingKey.PHONE_BOOKING);
    }

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

    public String getPhoneBooking() {
        return mPhoneBooking;
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
        dest.writeLong(mId);
        dest.writeInt(mTableNumber);
        dest.writeString(mTimeBooking);
        dest.writeString(mUsername);
        dest.writeString(mUserEmail);
        dest.writeString(mPhoneBooking);
    }

    @Override
    public String toString() {
        return "TableBooking{" +
                "mId=" + mId +
                ", mTableNumber=" + mTableNumber +
                ", mTimeBooking='" + mTimeBooking + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mUserEmail='" + mUserEmail + '\'' +
                ", mPhoneBooking='" + mPhoneBooking + '\'' +
                '}';
    }
}
