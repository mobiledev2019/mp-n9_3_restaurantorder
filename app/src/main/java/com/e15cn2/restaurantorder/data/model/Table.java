package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Table implements Parcelable {
    @SerializedName(Constants.JsonTableKey.NUMBER)
    private String mNumber;
    @SerializedName(Constants.JsonTableKey.TYPE)
    private String mType;
    @SerializedName(Constants.JsonTableKey.TABLE_STATUS)
    private int mStatus;
    @SerializedName(Constants.JsonTableKey.TABLE_BOOKINGS)
    private List<TableBooking> mTableBookings;

    protected Table(Parcel in) {
        mNumber = in.readString();
        mType = in.readString();
        mStatus = in.readInt();
        mTableBookings = in.createTypedArrayList(TableBooking.CREATOR);
    }

    public Table(JSONObject jsonObject) throws JSONException {
        mNumber = jsonObject.getString(Constants.JsonTableKey.NUMBER);
        mType = jsonObject.getString(Constants.JsonTableKey.TYPE);
        mStatus = jsonObject.getInt(Constants.JsonTableKey.TABLE_STATUS);
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

    @Override
    public String toString() {
        return "Table{" +
                "mNumber='" + mNumber + '\'' +
                ", mType='" + mType + '\'' +
                ", mStatus=" + mStatus +
                ", mTableBookings=" + mTableBookings +
                '}';
    }
}
