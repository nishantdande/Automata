package com.automata.ui.fragment;/*
 ==============================================
 Author      : nishant dande
 Version     : 
 Copyright   :
 Description :
 Date        : 08/03/16
 ==============================================
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automata.R;
import com.automata.adapter.DevicesAdapter;
import com.automata.device.DeviceManager;
import com.automata.device.model.RoomList;
import com.automata.preferences.Profile;
import com.automata.ui.activity.HomeScreenActivity;
import com.automata.ui.event.EventDeviceManager;
import com.automata.ui.view.AutomataRecycleView;
import com.automata.ui.view.ProgressView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DevicesFragment extends BaseFragment {

    private static DevicesFragment deviceFragment = null;

    private static int tabPosition = -1;

    @BindView(R.id.DeviceList)
    AutomataRecycleView mDeviceList;
    private DevicesAdapter livingRoomAdapter;
    ProgressView mProgressView;

    List<RoomList> roomLists = new ArrayList<>();

    public static DevicesFragment createFragment() {
        deviceFragment = new DevicesFragment();
        return deviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        livingRoomAdapter = DevicesAdapter.getInstance();
        mDeviceList.setAdapter(livingRoomAdapter);
        mDeviceList.setItemClickListener(onItemClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchDevices();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProgressView = new ProgressView(getActivity(), "Loading");
    }

    private void refreshDeviceList(List<RoomList> equipments) {
        if (equipments!=null)
            livingRoomAdapter.setLivingRoomDevice(getActivity(), equipments);
    }

    AutomataRecycleView.OnItemClickListener onItemClickListener
            = new AutomataRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            EventDeviceManager eventDeviceManager = new EventDeviceManager(HomeScreenActivity.DeviceActivity.DEVICE_DETAIL);
            eventDeviceManager.setEquipments(roomLists.get(position).getEquipments());
            eventDeviceManager.setTitle(roomLists.get(position).getName());

            EventBus.getDefault().post(eventDeviceManager);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void fetchDevices() {
        DeviceManager.getInstance().getStatus(Profile.getInstance().getUsername(),
                Profile.getInstance().getPassword()).subscribe(device->{
            roomLists.clear();
            roomLists = device.getRoomList();
            refreshDeviceList(roomLists);
        }, throwable -> {
            Log.e("error",throwable.getMessage(),throwable);
        });

    }
}
