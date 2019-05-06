package com.e15cn2.restaurantorder.data.model;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable {
    @SerializedName(Constants.JsonTableKey.ID)
    private int mId;
    @SerializedName(Constants.JsonTableKey.NUMBER)
    private String mNumber;
    @SerializedName(Constants.JsonTableKey.TYPE)
    private String mType;
    @SerializedName(Constants.JsonTableKey.STATUS)
    private int mStatus;
    @SerializedName(Constants.JsonTableKey.TABLE_BOOKINGS)
    private List<TableBooking> mTableBookings;


    public int getId() {
        return mId;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getType() {
        return mType;
    }

    public int getStatus() {
        return mStatus;
    }

    public List<TableBooking> getTableBookings() {
        return mTableBookings;
    }


}
