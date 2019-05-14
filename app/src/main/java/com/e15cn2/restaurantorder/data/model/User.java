package com.e15cn2.restaurantorder.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.e15cn2.restaurantorder.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable {
    private String mId;
    private String mName;
    private String mDob;
    private String mEmail;
    private String mPhone;
    private String mPassword;
    private String mImage;
    private int mIsAdmin;

    public User(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mDob = builder.mDob;
        mEmail = builder.mEmail;
        mPhone = builder.mPhone;
        mPassword = builder.mPassword;
        mImage = builder.mImage;
        mIsAdmin = builder.mIsAdmin;
    }

    public User(JSONObject jsonObject) throws JSONException {
        mId = jsonObject.getString(Constants.JsonUserKey.ID);
        mName = jsonObject.getString(Constants.JsonUserKey.NAME);
        mDob = jsonObject.getString(Constants.JsonUserKey.DOB);
        mEmail = jsonObject.getString(Constants.JsonUserKey.EMAIL);
        mPhone = jsonObject.getString(Constants.JsonUserKey.PHONE);
        mPassword = jsonObject.getString(Constants.JsonUserKey.PASSWORD);
        mImage = jsonObject.getString(Constants.JsonUserKey.IMAGE);
        mIsAdmin = jsonObject.getInt(Constants.JsonUserKey.IS_ADMIN);
    }

    protected User(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDob = in.readString();
        mEmail = in.readString();
        mPhone = in.readString();
        mPassword = in.readString();
        mImage = in.readString();
        mIsAdmin = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDob() {
        return mDob;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getIsAdmin() {
        return mIsAdmin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mDob);
        dest.writeString(mEmail);
        dest.writeString(mPhone);
        dest.writeString(mPassword);
        dest.writeString(mImage);
        dest.writeInt(mIsAdmin);
    }

    public static class Builder {
        private String mId;
        private String mName;
        private String mDob;
        private String mEmail;
        private String mPhone;
        private String mPassword;
        private String mImage;
        private int mIsAdmin;

        public Builder() {
        }

        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setDob(String dob) {
            mDob = dob;
            return this;
        }

        public Builder setEmail(String email) {
            mEmail = email;
            return this;
        }

        public Builder setPhone(String phone) {
            mPhone = phone;
            return this;
        }

        public Builder setPassword(String password) {
            mPassword = password;
            return this;
        }

        public Builder setImage(String image) {
            mImage = image;
            return this;
        }

        public Builder setIsAdmin(int isAdmin) {
            mIsAdmin = isAdmin;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
