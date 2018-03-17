package com.automata.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.automata.R;
import com.automata.device.model.Equipment;
import com.automata.preferences.Profile;
import com.automata.ui.event.EventDeviceManager;
import com.automata.ui.fragment.DevicesFragment;
import com.automata.ui.fragment.LivingRoomTab;
import com.automata.ui.view.NonSwipeableViewPager;
import com.automata.util.Router;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nishant on 15/03/16.
 */
public class HomeScreenActivity extends AutomataActivity  {

    public interface onPageChange{
        void pageChange(List<Equipment> equipments);
    }

    public enum DeviceActivity{
        DEVICE(0),
        DEVICE_DETAIL(1);
        int id;
        DeviceActivity(int id) {
            this.id = id;
        }
    }

    @BindView(R.id.viewpager)
    NonSwipeableViewPager mFeedViewPager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.nvView)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ViewPagerAdapter adapter;

    public onPageChange mOnPageChange;
    private List<Equipment> equipments = new ArrayList<>();
    private long mBackPressed;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(view -> {

        });
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_logout:{
                    logoutClick();
                }
                    break;
            }

            return false;
        });

        // Initializing Drawer Layout and ActionBarToggle

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
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

        setupViewPager(mFeedViewPager);

        mFeedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mOnPageChange != null && equipments != null){
                    mOnPageChange.pageChange(equipments);
                }else {
                    Exception exception = new Exception("No Equipment List");
                    apiErrorHandler.handleError(exception);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFeedViewPager.setCurrentItem(0);
    }

    @Subscribe
    // This method will be called when a HelloWorldEvent is posted
    public void onEvent(EventDeviceManager event){

        if (event!=null && event.getDeviceActivity()!= null){
            switch (event.getDeviceActivity()){
                case DEVICE:
                    setTitle("Automata");
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    mFeedViewPager.setCurrentItem(0);
                    break;

                case DEVICE_DETAIL:
                    equipments.clear();
                    equipments.addAll(event.getEquipments());
                    setTitle(event.getTitle());
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(view -> {
                        EventBus.getDefault().post(new EventDeviceManager(DeviceActivity.DEVICE));
                    });
                    mFeedViewPager.setCurrentItem(1);
                    break;

            }
        }
    }

    public void logoutClick(){
        Profile.getInstance().dologout();
        Router.startLoginActivity(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
        }

        adapter.addFragment(DevicesFragment.createFragment(), "");
        adapter.addFragment(LivingRoomTab.createFragment(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
}
