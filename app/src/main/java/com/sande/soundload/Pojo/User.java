package com.sande.soundload.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class User implements Parcelable {
    long id;
    String username;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.username);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
