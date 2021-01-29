package com.rpatcher.pubgm.utils;

import android.graphics.Canvas;

import com.rpatcher.pubgm.view.RView;

public class LIBHandler {
    static {
        System.loadLibrary("RPatcherBase");
    }
    public static native void GetInitCanvasDrawing(RView rView, Canvas canvas);
    public static native void setBoolConfigPatcher(int fitur, boolean about);
}
