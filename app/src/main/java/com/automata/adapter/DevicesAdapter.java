package com.automata.adapter;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.automata.R;
import com.automata.device.model.RoomList;

import java.util.List;
import java.util.Locale;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {

    private static DevicesAdapter livingRoomAdapter = new DevicesAdapter();

    DevicesAdapter(){}

    public static DevicesAdapter getInstance() {
        return livingRoomAdapter;
    }

    private List<RoomList> deviceList;
    private Context context;

    public void setLivingRoomDevice(Context context,List<RoomList> deviceList){
        this.context = context;

        if (this.deviceList != null && this.deviceList.size()>0){
            this.deviceList.clear();
        }
        this.deviceList = deviceList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_devices, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.navigationNext.setColorFilter(Color.GRAY);
        if (deviceList != null && deviceList.size()>0) {
            holder.title.setText(deviceList.get(position).getName());
            holder.deviceStatus.setText(String.format(Locale.ENGLISH,
                    "%d/%d", deviceList.get(position).getTotalOnEquipments(),
                    deviceList.get(position).getTotalEquipments()));
        }
    }

    @Override
    public int getItemCount()
    {
        return deviceList == null ? 0 : deviceList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        protected TextView title;
        protected TextView deviceStatus;
        protected ImageView navigationNext;


        public ViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.deviceTitle);
            this.deviceStatus = (TextView) view.findViewById(R.id.deviceStatus);
            this.navigationNext = (ImageView) view.findViewById(R.id.navigationNext);
        }
    }

    /**
     * Get the Feed By Position
     * @param position
     * @return
     */
    public RoomList getDeviceByPosition(int position) {
        return deviceList.get(position);
    }
}
