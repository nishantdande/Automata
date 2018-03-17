package com.automata.ui.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.automata.device.model.Equipment;
import com.automata.ui.activity.HomeScreenActivity;

import java.util.List;

/**
 * Created by nishantdande on 25/09/16.
 */

public class EventDeviceManager implements Parcelable {

    private HomeScreenActivity.DeviceActivity deviceActivity;
    private List<Equipment> equipments;
    private String title;

    public EventDeviceManager(HomeScreenActivity.DeviceActivity deviceActivity) {
        this.deviceActivity = deviceActivity;
    }

    public HomeScreenActivity.DeviceActivity getDeviceActivity() {
        return deviceActivity;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.deviceActivity == null ? -1 : this.deviceActivity.ordinal());
        dest.writeTypedList(this.equipments);
        dest.writeString(this.title);
    }

    protected EventDeviceManager(Parcel in) {
        int tmpDeviceActivity = in.readInt();
        this.deviceActivity = tmpDeviceActivity == -1 ? null : HomeScreenActivity.DeviceActivity.values()[tmpDeviceActivity];
        this.equipments = in.createTypedArrayList(Equipment.CREATOR);
        this.title = in.readString();
    }

    public static final Parcelable.Creator<EventDeviceManager> CREATOR = new Parcelable.Creator<EventDeviceManager>() {
        @Override
        public EventDeviceManager createFromParcel(Parcel source) {
            return new EventDeviceManager(source);
        }

        @Override
        public EventDeviceManager[] newArray(int size) {
            return new EventDeviceManager[size];
        }
    };
}
