
package com.automata.device.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Equipment implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.status);
        dest.writeString(this.name);
        dest.writeString(this.icon);
    }

    public Equipment() {
    }

    protected Equipment(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<Equipment> CREATOR = new Parcelable.Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel source) {
            return new Equipment(source);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };
}
