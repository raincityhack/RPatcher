package com.rpatcher.pubgm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.rpatcher.pubgm.R;
import com.rpatcher.pubgm.SharedUtils;
import com.rpatcher.pubgm.utils.Commander;
import com.rpatcher.pubgm.utils.HackUtils;

import java.io.File;

public class MainService extends Service {

    private static MainService instance;
    private WindowManager mWindowManager;
    private View menuView;
    private LinearLayout mainView, controlView;
    private SharedUtils sharedUtils;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedUtils = new SharedUtils(this);
        CreateOverlay();
    }

    private void CreateOverlay() {
        menuView = LayoutInflater.from(this).inflate(R.layout.layout_service, null);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.RGBA_8888);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(menuView, params);
        controlView = menuView.findViewById(R.id.layout_controll);
        mainView = menuView.findViewById(R.id.layout_menu);
        menuView.findViewById(R.id.ic_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlView.setVisibility(View.VISIBLE);
                mainView.setVisibility(View.GONE);
            }
        });
        final GestureDetector gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        menuView.findViewById(R.id.base_layout).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    mainView.setVisibility(View.VISIBLE);
                    controlView.setVisibility(View.GONE);
                    return true;
                } else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            mWindowManager.updateViewLayout(menuView, params);
                            return true;
                    }
                    return false;
                }
            }
        });
        initMenu();
    }

    private void initMenu() {
        instance.setTheme(R.style.ServiceTheme);

        Switch switch_small_crosshair = mainView.findViewById(R.id.switch_small_crosshair);
        switch_small_crosshair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HackUtils.syncFeatures(HackUtils.SmallCrosshair, isChecked);
            }
        });

        Switch switch_less_recoil = mainView.findViewById(R.id.switch_less_recoil);
        switch_less_recoil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HackUtils.syncFeatures(HackUtils.LessRecoil, isChecked);
            }
        });

        SeekBar seekbar_character_view = mainView.findViewById(R.id.seekbar_character_view);
        final TextView textview_seekbar_character_view = mainView.findViewById(R.id.textview_seekbar_character_view);
        seekbar_character_view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textview_seekbar_character_view.setText(String.format("%s", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int Value = 360;
                if (seekBar.getProgress() == 10){
                    Value = 260;
                } else if (seekBar.getProgress() == 9){
                    Value = 270;
                } else if (seekBar.getProgress() == 8){
                    Value = 280;
                } else if (seekBar.getProgress() == 7){
                    Value = 290;
                } else if (seekBar.getProgress() == 6){
                    Value = 300;
                } else if (seekBar.getProgress() == 5){
                    Value = 310;
                } else if (seekBar.getProgress() == 4){
                    Value = 320;
                } else if (seekBar.getProgress() == 3){
                    Value = 330;
                } else if (seekBar.getProgress() == 2){
                    Value = 340;
                } else if (seekBar.getProgress() == 1){
                    Value = 350;
                } else if (seekBar.getProgress() == -10){
                    Value = 460;
                } else if (seekBar.getProgress() == -9){
                    Value = 450;
                } else if (seekBar.getProgress() == -8){
                    Value = 440;
                } else if (seekBar.getProgress() == -7){
                    Value = 430;
                } else if (seekBar.getProgress() == -6){
                    Value = 420;
                } else if (seekBar.getProgress() == -5){
                    Value = 410;
                } else if (seekBar.getProgress() == -4){
                    Value = 400;
                } else if (seekBar.getProgress() == -3){
                    Value = 390;
                } else if (seekBar.getProgress() == -2){
                    Value = 380;
                } else if (seekBar.getProgress() == -1){
                    Value = 370;
                }
                HackUtils.syncFeatures(HackUtils.CharacterView, Value);
            }
        });
        textview_seekbar_character_view.setText(String.valueOf(seekbar_character_view.getProgress()));

        Switch switch_aimbot_100m = mainView.findViewById(R.id.switch_aimbot_100m);
        switch_aimbot_100m.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HackUtils.syncFeatures(HackUtils.Aimbot100M, isChecked);
            }
        });
    }

    public static void StartService(Context context){
        if (instance == null){
            context.startService(new Intent(context, MainService.class));
        }
    }

    public static void StopService(){
        if (instance != null){
            instance.stopService(new Intent(instance, MainService.class));
            instance.stopSelf();
            instance.onDestroy();
        }
    }

    class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (menuView != null){
            mWindowManager.removeView(menuView);
            menuView = null;
            instance = null;
        }
    }
}