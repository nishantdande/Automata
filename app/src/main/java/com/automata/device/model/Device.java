
package com.automata.device.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("room_list")
    @Expose
    private List<RoomList> roomList = new ArrayList<RoomList>();
    @SerializedName("total_equipments_on_in_home")
    @Expose
    private Integer totalEquipmentsOnInHome;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The roomList
     */
    public List<RoomList> getRoomList() {
        return roomList;
    }

    /**
     * 
     * @param roomList
     *     The room_list
     */
    public void setRoomList(List<RoomList> roomList) {
        this.roomList = roomList;
    }

    /**
     * 
     * @return
     *     The totalEquipmentsOnInHome
     */
    public Integer getTotalEquipmentsOnInHome() {
        return totalEquipmentsOnInHome;
    }

    /**
     * 
     * @param totalEquipmentsOnInHome
     *     The total_equipments_on_in_home
     */
    public void setTotalEquipmentsOnInHome(Integer totalEquipmentsOnInHome) {
        this.totalEquipmentsOnInHome = totalEquipmentsOnInHome;
    }

}
