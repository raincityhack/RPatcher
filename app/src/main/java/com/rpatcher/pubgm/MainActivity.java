package com.rpatcher.pubgm;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;

import com.rpatcher.pubgm.service.CanvasService;
import com.rpatcher.pubgm.service.MainService;
import com.rpatcher.pubgm.utils.Commander;
import com.rpatcher.pubgm.utils.FileCommons;
import com.rpatcher.pubgm.utils.HackUtils;

import java.io.File;


public class MainActivity extends Activity {


    public static int REQUEST = 5565;
    private SharedUtils sharedUtils;
    private RadioButton radiobutton_global_version, radiobutton_vietnam_version, radiobutton_korea_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Commander.execute("echo Check root...").start();
        sharedUtils = new SharedUtils(this);
        sharedUtils.initShared();
        InitDaemonPath();
        requestOverlayPermission();
        BindView();
        if (sharedUtils.getInt("PUBGM_VERSION") == 1){
            radiobutton_global_version.setChecked(true);
        }
        if (sharedUtils.getInt("PUBGM_VERSION") == 2){
            radiobutton_vietnam_version.setChecked(true);
        }
        if (sharedUtils.getInt("PUBGM_VERSION") == 3){
            radiobutton_korea_version.setChecked(true);
        }
    }

    private void InitDaemonPath() {
        int GameCode = sharedUtils.getInt("PUBGM_VERSION");
        FileCommons.copyFromAssets(this, getFilesDir().getPath(), "rpatcherdaemon");
        File daemonPath = new File(getFilesDir().getPath() + "/rpatcherdaemon");
        Commander.execute("chmod +x " + daemonPath.toString()).start();
        HackUtils.setDaemonPath(daemonPath.toString() + " " + GameCode);
    }

    private void BindView() {
        radiobutton_global_version = findViewById(R.id.radiobutton_global_version);
        radiobutton_vietnam_version = findViewById(R.id.radiobutton_vietnam_version);
        radiobutton_korea_version = findViewById(R.id.radiobutton_korea_version);
        CheckAPPInstalled();
        radiobutton_global_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedUtils.setInt("PUBGM_VERSION", 1);
            }
        });
        radiobutton_vietnam_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedUtils.setInt("PUBGM_VERSION", 2);
            }
        });
        radiobutton_korea_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedUtils.setInt("PUBGM_VERSION", 3);
            }
        });
    }

    private void CheckAPPInstalled() {
        Intent globalIntent = getPackageManager().getLaunchIntentForPackage("com.tencent.ig");
        Intent vietnamIntent = getPackageManager().getLaunchIntentForPackage("com.vng.pubgmobile");
        Intent koreaIntent = getPackageManager().getLaunchIntentForPackage("com.pubg.krmobile");
        if (globalIntent != null){
            radiobutton_global_version.setText("Global version [Installed]");
        } else {
            radiobutton_global_version.setText("Global version [Not Installed]");
        }
        if (vietnamIntent != null){
            radiobutton_vietnam_version.setText("Vietnam version [Installed]");
        } else {
            radiobutton_vietnam_version.setText("Vietnam version [Not Installed]");
        }
        if (koreaIntent != null){
            radiobutton_korea_version.setText("Korea version [Installed]");
        } else {
            radiobutton_korea_version.setText("Korea version [Not Installed]");
        }
    }
    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)){
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
                startActivityForResult(intent, REQUEST);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestOverlayPermission();
            }
        }
    }

    public void StartCheat(View view) {
        CanvasService.StartService(this);
        MainService.StartService(this);
    }

    public void StopCheat(View view) {
        CanvasService.StopService();
        MainService.StopService();
    }
}