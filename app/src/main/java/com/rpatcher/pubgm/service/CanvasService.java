package com.rpatcher.pubgm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.rpatcher.pubgm.view.RView;

public class CanvasService extends Service {

    public static CanvasService instance = null;
    private static RView rView = null;
    private static WindowManager windowManager;
    private static WindowManager.LayoutParams params;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CreateOverlay();
    }

    private void CreateOverlay() {
        rView = new RView(this);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, 0, getNavigationBarHeight(instance),
                getLayoutType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.RGBA_8888);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;
        windowManager.addView(rView, params);
    }

    private int getLayoutType() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return LAYOUT_FLAG;
    }

    public static int getNavigationBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && !hasMenuKey) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static void StartService(Context context){
        if (instance == null){
            context.startService(new Intent(context, CanvasService.class));
        }
    }

    public static void StopService(){
        if (instance != null){
            instance.stopService(new Intent(instance, CanvasService.class));
            instance.stopSelf();
            instance.onDestroy();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rView != null){
            windowManager.removeView(rView);
            rView = null;
            instance = null;
        }
    }

}