package com.rpatcher.pubgm.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commander {

    private String command = null;

    public Commander(String command){
        this.command = command;
    }

    public static Commander execute(String command){
        return new Commander(command);
    }

    public void start(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Process su = Runtime.getRuntime().exec("su -c " + command);
                    DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                    outputStream.writeBytes("exit\n");
                    outputStream.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(su.getInputStream()));
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
