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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automata.Automata;
import com.automata.device.model.Equipment;
import com.automata.R;

import java.util.List;

public class LivingRoomAdapter extends RecyclerView.Adapter<LivingRoomAdapter.ViewHolder> {

    private static LivingRoomAdapter livingRoomAdapter = new LivingRoomAdapter();

    LivingRoomAdapter(){}

    public static LivingRoomAdapter getInstance() {
        return livingRoomAdapter;
    }

    private List<Equipment> deviceList;
    private Context context;

    public void setLivingRoomDevice(Context context,List<Equipment> deviceList){
        this.context = context;

        if (this.deviceList != null && this.deviceList.size()>0){
            this.deviceList.clear();
        }
        this.deviceList = deviceList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_living_room, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (deviceList != null && deviceList.size()>0) {
            if (position == 0 ){
                holder.mEquipmentRow.setVisibility(View.GONE);
            }else {
                holder.mEquipmentRow.setVisibility(View.VISIBLE);
                Equipment equipment = deviceList.get(position);
                holder.title.setText(equipment.getName());
                controlDeviceIcon(holder, equipment.getStatus());
                switchState(holder,equipment.getStatus());
            }
        }
    }

    private void switchState(ViewHolder holder,Integer value){
        if (value == 1){
            holder.mSwitch.setChecked(true);
        }else
            holder.mSwitch.setChecked(false);
    }

    private void controlDeviceIcon(ViewHolder holder,Integer value) {
        if (value == 1){
            holder.mDeviceIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.switch_on));
            holder.mDeviceIcon.setColorFilter(Automata.getInstance().getContext().getResources().getColor(R.color.on_color));
        }else{
            holder.mDeviceIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.switch_off));
            holder.mDeviceIcon.setColorFilter(Automata.getInstance().getContext().getResources().getColor(R.color.off_color));
        }
    }

    @Override
    public int getItemCount()
    {
        return deviceList == null ? 0 : deviceList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        protected TextView title;
        protected SwitchCompat mSwitch;
        protected ImageView mDeviceIcon;
        protected RelativeLayout mEquipmentRow;

        public ViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.mSwitch = (SwitchCompat) view.findViewById(R.id.Switch);
            this.mDeviceIcon = (ImageView) view.findViewById(R.id.room_switch);
            this.mEquipmentRow = (RelativeLayout) view.findViewById(R.id.equipment_row);
        }
    }

    /**
     * Get the Feed By Position
     * @param position
     * @return
     */
    public Equipment getDeviceByPosition(int position) {
        return deviceList.get(position);
    }
}
