package com.rpatcher.pubgm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.view.View;

import com.rpatcher.pubgm.utils.LIBHandler;

public class RView extends View implements Runnable {

    Thread mThread;
    int FPS = 60;
    static long sleepTime;
    private int    mFPS = 0;
    private int    mFPSCounter = 0;
    private long   mFPSTime = 0;

    Paint mTextPaint;

    public RView(Context context) {
        super(context, null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
        sleepTime = 1000 / FPS;
        mThread = new Thread(this);
        mThread.start();
    }

    private void InitializePaints() {
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(1.1f);
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        while (mThread.isAlive() && !mThread.isInterrupted()) {
            try {
                long t1 = System.currentTimeMillis();
                postInvalidate();
                long td = System.currentTimeMillis() - t1;
                Thread.sleep(Math.max(Math.min(0, sleepTime - td), sleepTime));
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null && getVisibility() == VISIBLE) {
            ClearCanvas(canvas);
            LIBHandler.GetInitCanvasDrawing(this, canvas);
        }
    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void DrawFPS(Canvas cvs, float posX, float posY) {
        mTextPaint.setColor(Color.YELLOW);
        mTextPaint.setShadowLayer(5,0,0, Color.BLACK);
        mTextPaint.setTextSize(30);
        if (SystemClock.uptimeMillis() - mFPSTime > 1000) {
            mFPSTime = SystemClock.uptimeMillis();
            mFPS = mFPSCounter;
            mFPSCounter = 0;
        } else {
            mFPSCounter++;
        }
        cvs.drawText("RPatcher FPS " + mFPS, posX, posY, mTextPaint);
    }

}
