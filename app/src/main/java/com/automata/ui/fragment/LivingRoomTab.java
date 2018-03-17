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
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automata.R;
import com.automata.adapter.LivingRoomAdapter;
import com.automata.device.DeviceManager;
import com.automata.device.model.Equipment;
import com.automata.preferences.Profile;
import com.automata.ui.activity.HomeScreenActivity;
import com.automata.ui.view.ProgressView;
import com.automata.ui.view.AutomataRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LivingRoomTab extends BaseFragment implements HomeScreenActivity.onPageChange {

    private static LivingRoomTab livingRoomTab = null;

    private static int tabPosition = -1;

    @BindView(R.id.livingDeviceList)
    AutomataRecycleView mLivingRoomDevice;
    private LivingRoomAdapter livingRoomAdapter;
    ProgressView mProgressView;

    public static LivingRoomTab createFragment() {
        livingRoomTab = new LivingRoomTab();
        return livingRoomTab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_living_room, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        livingRoomAdapter = LivingRoomAdapter.getInstance();
        mLivingRoomDevice.setAdapter(livingRoomAdapter);
        mLivingRoomDevice.setItemClickListener(onItemClickListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeScreenActivity)context).mOnPageChange = this;
        mProgressView = new ProgressView(getActivity(), "Loading");
    }

    private void refreshDeviceList(List<Equipment> equipments) {
        if (equipments!=null)
            livingRoomAdapter.setLivingRoomDevice(getActivity(), equipments);
    }

    AutomataRecycleView.OnItemClickListener onItemClickListener
            = new AutomataRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            final SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.Switch);
            switchCompat.setOnClickListener(v -> {
            int status = switchCompat.isChecked() == true ? 1 : 0;
            final Equipment equipment = livingRoomAdapter.getDeviceByPosition(position);
            mProgressView.showDialog();
                DeviceManager.getInstance().setStatusById(Profile.getInstance().getUsername(),
                        Profile.getInstance().getPassword(), equipment.getId(), status).subscribe(device -> {
                    mProgressView.closeDialog();
                    refreshDeviceList(device.getRoomList().get(tabPosition).getEquipments());
                }, throwable -> {
                    mProgressView.closeDialog();
                    Log.e("error", throwable.getMessage(), throwable);
                });


            });
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    @Override
    public void pageChange(List<Equipment> equipments) {
        refreshDeviceList(equipments);
    }
}
