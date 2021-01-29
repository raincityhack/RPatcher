package com.rpatcher.pubgm.utils;

import android.content.Context;
import android.widget.Toast;

import com.rpatcher.pubgm.SharedUtils;

public class HackUtils {

    public static String DAEMON_PATH = null;

    public static void setDaemonPath(String daemonPath) {
        DAEMON_PATH = daemonPath;
    }

    public static int SmallCrosshair = 1;
    public static int LessRecoil = 2;
    public static int CharacterView = 3;
    public static int Aimbot100M = 4;

    public static void syncFeatures(int features, int command){
        Commander.execute(DAEMON_PATH + " " + features + " " + command).start();
    }

    /** For CharacterView **/
    public static void syncFeatures(int features, boolean about){
        if (about){
            Commander.execute(DAEMON_PATH + " " + features + " " + 1).start();
        } else {
            Commander.execute(DAEMON_PATH + " " + features + " " + 0).start();
        }

    }
}
