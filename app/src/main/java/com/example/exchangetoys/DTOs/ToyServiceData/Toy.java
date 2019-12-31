package com.example.exchangetoys.DTOs.ToyServiceData;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Toy implements Parcelable {

    private Long toy_id;
    private String toy_owner_id;//foreign key to adult
    private String toy_current_holder_id;
    private String toy_name;
    private String toy_description;
    private int toy_age_category;
    private String toy_main_category;
    private String toy_special_feature;
    private String toy_tags;
    private int toy_didactic;//0 no, 1 yes
    private int toy_vintage;//0 no, 1 yes
    private String toy_factory_name;
    private int toy_quality_of_made;
    private String toy_photos;

    protected Toy(Parcel in) {
        if (in.readByte() == 0) {
            toy_id = null;
        } else {
            toy_id = in.readLong();
        }
        toy_owner_id = in.readString();
        toy_current_holder_id = in.readString();
        toy_name = in.readString();
        toy_description = in.readString();
        toy_age_category = in.readInt();
        toy_main_category = in.readString();
        toy_special_feature = in.readString();
        toy_tags = in.readString();
        toy_didactic = in.readInt();
        toy_vintage = in.readInt();
        toy_factory_name = in.readString();
        toy_quality_of_made = in.readInt();
        toy_photos = in.readString();
    }

    public static final Creator<Toy> CREATOR = new Creator<Toy>() {
        @Override
        public Toy createFromParcel(Parcel in) {
            return new Toy(in);
        }

        @Override
        public Toy[] newArray(int size) {
            return new Toy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (toy_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(toy_id);
        }
        dest.writeString(toy_owner_id);
        dest.writeString(toy_current_holder_id);
        dest.writeString(toy_name);
        dest.writeString(toy_description);
        dest.writeInt(toy_age_category);
        dest.writeString(toy_main_category);
        dest.writeString(toy_special_feature);
        dest.writeString(toy_tags);
        dest.writeInt(toy_didactic);
        dest.writeInt(toy_vintage);
        dest.writeString(toy_factory_name);
        dest.writeInt(toy_quality_of_made);
        dest.writeString(toy_photos);
    }
}
