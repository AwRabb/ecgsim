package com.aw.ecgsim.business;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.aw.ecgsim.view.BPPanel;

/**
 * Created by Andrew Rabb on 2016-08-10.
 */
public class DrawThread extends Thread {

    boolean running;
    SurfaceHolder holder;
    Context context;
    BPPanel surfaceView;


    public DrawThread(SurfaceHolder holder, Context context, BPPanel surfaceView ){
        this.holder = holder;
        this.context = context;
        this.surfaceView = surfaceView;
        running = false;
    }

    public void setRunning(boolean b){
        running = b;
    }
    public void run(){
        super.run();
        while (running){
            Canvas canvas = holder.lockCanvas();
            if(canvas != null){
                surfaceView.draw(canvas);

                //Log.d("DrawThread - Drawing", "currently drawing to screen (every 5s currently)");

                holder.unlockCanvasAndPost(canvas);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
