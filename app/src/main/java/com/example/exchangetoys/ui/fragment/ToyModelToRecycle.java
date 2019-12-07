package com.example.exchangetoys.ui.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToyModelToRecycle implements Parcelable {
    private String name;
    private String info;
    private int image;


    protected ToyModelToRecycle(Parcel in) {
        name = in.readString();
        info = in.readString();
        image = in.readInt();
    }

    public static final Creator<ToyModelToRecycle> CREATOR = new Creator<ToyModelToRecycle>() {
        @Override
        public ToyModelToRecycle createFromParcel(Parcel in) {
            return new ToyModelToRecycle(in);
        }

        @Override
        public ToyModelToRecycle[] newArray(int size) {
            return new ToyModelToRecycle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(info);
        dest.writeInt(image);
    }
}
