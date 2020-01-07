package com.example.exchangetoys.DTOs.UserServiceData;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Child implements Parcelable {
    private Long child_id;
    private String child_parent_id;//generally it's parent's email
    private String child_name;
    private String child_login;
    private String child_password;
    private int child_age;
    private int child_radius_area;
    private double child_latitude;
    private double child_longitude;
    private String child_suggestion;

    protected Child(Parcel in) {
        if (in.readByte() == 0) {
            child_id = null;
        } else {
            child_id = in.readLong();
        }
        child_parent_id = in.readString();
        child_name = in.readString();
        child_login = in.readString();
        child_password = in.readString();
        child_age = in.readInt();
        child_radius_area = in.readInt();
        child_latitude = in.readDouble();
        child_longitude = in.readDouble();
        child_suggestion = in.readString();
    }

    public static final Creator<Child> CREATOR = new Creator<Child>() {
        @Override
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }

        @Override
        public Child[] newArray(int size) {
            return new Child[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (child_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(child_id);
        }
        dest.writeString(child_parent_id);
        dest.writeString(child_name);
        dest.writeString(child_login);
        dest.writeString(child_password);
        dest.writeInt(child_age);
        dest.writeInt(child_radius_area);
        dest.writeDouble(child_latitude);
        dest.writeDouble(child_longitude);
        dest.writeString(child_suggestion);
    }
}
