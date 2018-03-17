
package com.automata.device.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RoomList implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_equipments")
    @Expose
    private Integer totalEquipments;
    @SerializedName("equipments")
    @Expose
    private ArrayList<Equipment> equipments = new ArrayList<>();
    @SerializedName("total_on_equipments")
    @Expose
    private Integer totalOnEquipments;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.totalEquipments);
        dest.writeTypedList(this.equipments);
        dest.writeValue(this.totalOnEquipments);
    }

    protected RoomList(Parcel in) {
        this.name = in.readString();
        this.totalEquipments = (Integer) in.readValue(Integer.class.getClassLoader());
        this.equipments = in.createTypedArrayList(Equipment.CREATOR);
        this.totalOnEquipments = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<RoomList> CREATOR = new Parcelable.Creator<RoomList>() {
        @Override
        public RoomList createFromParcel(Parcel source) {
            return new RoomList(source);
        }

        @Override
        public RoomList[] newArray(int size) {
            return new RoomList[size];
        }
    };
}
