package com.automata.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.automata.R;
import com.automata.adapter.LivingRoomAdapter;
import com.automata.device.DeviceManager;
import com.automata.device.model.Equipment;
import com.automata.preferences.Profile;
import com.automata.ui.view.AutomataRecycleView;
import com.automata.ui.view.ProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nishantdande on 30/09/16.
 */

public class DetailActivity extends AutomataActivity {

    @BindView(R.id.livingDeviceList)
    AutomataRecycleView mLivingRoomDevice;
    private LivingRoomAdapter livingRoomAdapter;
    ProgressView mProgressView;

    List<Equipment> equipments;
    String title;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.navigateBack)
    ImageView mNavigateBack;

    private Paint p = new Paint();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_living_room);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            equipments = bundle.getParcelableArrayList("equipment");

            title = bundle.getString("roomTitle");
        }

        setTitle("");
        mTitle.setText(title);
        mNavigateBack.setColorFilter(getResources().getColor(R.color.colorPrimary));
        mNavigateBack.setOnClickListener(view -> {
            finish();
        });

        mProgressView = new ProgressView(this, "Loading");
        livingRoomAdapter = LivingRoomAdapter.getInstance();
        mLivingRoomDevice.setAdapter(livingRoomAdapter);
        mLivingRoomDevice.setItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDeviceList(equipments);
    }

    private void refreshDeviceList(List<Equipment> equipments) {
        if (equipments!=null)
            livingRoomAdapter.setLivingRoomDevice(this, equipments);
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
                }, throwable -> {
                    mProgressView.closeDialog();
                    apiErrorHandler.handleError(throwable);
                });


            });
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };


    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
//                    adapter.removeItem(position);
                } else {
//                    removeView();
//                    edit_position = position;
//                    alertDialog.setTitle("Edit Country");
//                    et_country.setText(countries.get(position));
//                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_keyboard_arrow_right_black);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,
                                (float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mLivingRoomDevice);
    }
}
