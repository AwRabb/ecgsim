package com.aw.ecgsim.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.aw.ecgsim.R;
import com.aw.ecgsim.business.DrawThread;

/**
 * Created by Andrew Rabb on 2016-07-10.
 */
public class BPPanel extends SurfaceView implements SurfaceHolder.Callback{
    Context context;
    DrawThread drawThread;
    Boolean b = true;

    public BPPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        //this.setBackgroundColor(Color.GREEN);

        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

//        Canvas c = getHolder().lockCanvas();
//        draw(c);
//        //doDraw(c);
//        getHolder().unlockCanvasAndPost(c);



        drawThread = new DrawThread(holder, context, this);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        drawThread.setRunning(false);
        boolean retry = true;
        while (retry){
            try{
                drawThread.join();
                retry=false;
            }catch (Exception ex){
                Log.d("business.BPPanel",  ex.getMessage());
            }
        }


    }
 int i = 0;
    public void doDraw(Canvas canvas){
        //canvas.drawColor(Color.BLACK);
        //canvas.drawLine(0,0, canvas.getWidth(), canvas.getHeight(), new Paint(Color.WHITE));
    }


    private Paint paint;
    private void init(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(15f);
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        init();


//        canvas.drawColor(Color.BLACK);
        paint.setColor(b?Color.BLUE: Color.GREEN);
if(b) canvas.drawLine(100, 100, getWidth() - 100, getHeight() - 100, paint);
else canvas.drawLine(100, getHeight() - 100, getWidth() - 100, 100,  paint);
        b=!b;
        
        paint.setTextSize(50);
        canvas.drawText("Hello World", 0, canvas.getHeight()/2, paint);


        }
}
