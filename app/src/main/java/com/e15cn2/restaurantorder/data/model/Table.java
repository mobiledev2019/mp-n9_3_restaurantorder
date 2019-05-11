package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable, Parcelable {
    @SerializedName(Constants.JsonTableKey.NUMBER)
    private String mNumber;
    @SerializedName(Constants.JsonTableKey.TYPE)
    private String mType;
    @SerializedName(Constants.JsonTableKey.STATUS)
    private int mStatus;
    @SerializedName(Constants.JsonTableKey.TABLE_BOOKINGS)
    private List<TableBooking> mTableBookings;

    protected Table(Parcel in) {
        mNumber = in.readString();
        mType = in.readString();
        mStatus = in.readInt();
        mTableBookings = in.createTypedArrayList(TableBooking.CREATOR);
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    public String getNumber() {
        return mNumber;
    }

    public String getType() {
        return mType;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public List<TableBooking> getTableBookings() {
        return mTableBookings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNumber);
        dest.writeString(mType);
        dest.writeInt(mStatus);
        dest.writeTypedList(mTableBookings);
    }
}
