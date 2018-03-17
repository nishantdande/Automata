package com.automata.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.automata.R;
import com.automata.adapter.DevicesAdapter;
import com.automata.device.DeviceManager;
import com.automata.device.model.RoomList;
import com.automata.preferences.Profile;
import com.automata.ui.event.EventDeviceManager;
import com.automata.ui.view.AutomataRecycleView;
import com.automata.ui.view.ProgressView;
import com.automata.util.DialogUtils;
import com.automata.util.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nishantdande on 30/09/16.
 */

public class NewHomeScreenActivity extends AutomataActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.nvView)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private HomeScreenActivity.ViewPagerAdapter adapter;

    private long mBackPressed;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @BindView(R.id.DeviceList)
    AutomataRecycleView mDeviceList;
    private DevicesAdapter livingRoomAdapter;

    @BindView(R.id.total_devices)
    TextView mTotalDevice;

    ProgressView mProgressView;

    List<RoomList> roomLists = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_screen);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                fetchDevices();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        livingRoomAdapter = DevicesAdapter.getInstance();
        mDeviceList.setAdapter(livingRoomAdapter);
        mDeviceList.setItemClickListener(onItemClickListener);

    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchDevices();
    }

    @OnClick(R.id.all_off)
    public void allOff(){
        DialogUtils.createAlertDialogWithButtons(this,"Automata","Are you sure you want to turn off all\nequipments in Home?"
                ,"Ok", "Cancel",(dialogInterface, i) -> {
                    DeviceManager.getInstance().allOff(Profile.getInstance().getUsername(),
                            Profile.getInstance().getPassword()).subscribe(device -> {
                        mTotalDevice.setText("0");
                    }, throwable -> {
                        apiErrorHandler.handleError(throwable);
                    });
                }, (dialogInterface, i) -> {
                    dialogInterface.cancel();
                    dialogInterface.dismiss();
                }).show();
    }

    @OnClick(R.id.logout)
    public void logoutClick(){
        Profile.getInstance().dologout();
        Router.startLoginActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + 2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), getString(R.string.exit_alert_title_message), Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    private void refreshDeviceList(List<RoomList> equipments) {
        if (equipments!=null)
            livingRoomAdapter.setLivingRoomDevice(this, equipments);
    }

    AutomataRecycleView.OnItemClickListener onItemClickListener
            = new AutomataRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            EventDeviceManager eventDeviceManager = new EventDeviceManager(HomeScreenActivity.DeviceActivity.DEVICE_DETAIL);
            eventDeviceManager.setEquipments(roomLists.get(position).getEquipments());
            eventDeviceManager.setTitle(roomLists.get(position).getName());

            Bundle bundle = new Bundle();
//            bundle.putParcelable("details", eventDeviceManager);
            bundle.putParcelableArrayList("equipment", roomLists.get(position).getEquipments());
            bundle.putString("roomTitle", roomLists.get(position).getName());
//
            Router.startDetailScreenActivity(NewHomeScreenActivity.this,bundle);

//            EventBus.getDefault().post(eventDeviceManager);
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

            mTotalDevice.setText(String.valueOf(device.getTotalEquipmentsOnInHome()));
        }, throwable -> {
            apiErrorHandler.handleError(throwable);
        });

    }
}
