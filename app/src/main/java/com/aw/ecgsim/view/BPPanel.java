package com.aw.ecgsim.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.aw.ecgsim.business.BloodPressure;
import com.aw.ecgsim.business.DrawThread;
import com.aw.ecgsim.business.LineColl;

/**
 * Created by Andrew Rabb on 2016-07-10.
 */
public class BPPanel extends SurfaceView implements SurfaceHolder.Callback{
    Context context;
    DrawThread drawThread;
    BloodPressure bloodPressure;
    private Paint paint;
    private final int dScreenWidth = 120;
    private final int dScreenHeight = 140;

    public void setBloodPressure(BloodPressure bloodPressure) {
        this.bloodPressure = bloodPressure;
    }


    public BPPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        //this.setBackgroundColor(Color.GREEN);

        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15f);
    }
    int counter = 0;
    int top;
    int bot;
    int left;
    int right;
    int width;
    int height;
    int refreshLine;
    int widthTick;
    int heightTick;
    int baseHeight;
    float[] lineList = new float[0];
    LineColl lineColl = new LineColl();

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        init();

        if (counter > dScreenWidth){
            counter = 0;
            lineColl = new LineColl();
        }
        else {
            counter++;
        }
        top = 0;
        bot = canvas.getHeight() / 2;
        left = 0;
        right = canvas.getWidth();

        refreshLine = 0;
        baseHeight = height*3/4;
        width = getWidth();
        widthTick = width / dScreenWidth;
        heightTick = height / dScreenHeight;
        height = getHeight();
        testingLineColl(canvas);
    }

    private void LineGraph(Canvas canvas ){
        int amplitude = bloodPressure.getAmplitude(counter);
        float[] inputFloats = new float[0];

        if (lineList.length == 0){
            inputFloats = new float[]{0, baseHeight, counter * widthTick, amplitude * heightTick};
        }
        else{
            inputFloats[0] = lineList[lineList.length-2];
            inputFloats[1] = lineList[lineList.length-1];
        }
        createLineList(inputFloats);
        canvas.drawLines(lineList, paint);
    }

    private void testingLineColl(Canvas canvas){
        int amplitude = bloodPressure.getAmplitude(counter);

        //LineColl lineColl = new LineColl();
        lineColl.AddPoint(counter * widthTick,
                (int) (bot - (amplitude * heightTick) + (bloodPressure.getDiastolic() * heightTick)));
        amplitude = bloodPressure.getAmplitude(counter + 1);
        lineColl.AddPoint((counter + 1) * widthTick,
                (int) (bot - (amplitude * heightTick) + (bloodPressure.getDiastolic() * heightTick)));

//        canvas.drawLine(0,
//                (float) (bot - (bloodPressure.getSystolic() * heightTick)), width,
//                (float) (bot - (bloodPressure.getSystolic() * heightTick)), paint);
        canvas.drawLines(lineColl.SplitList(), paint);
    }
    /**
     * One possible way of drawing at least the BP graph. This one is based upon making a series of vertical lines.
     * each line goes from the base Height to the top. It should be adjusted such that the skew is not so extreme
     * @param canvas - canvas to draw on, supplied by surfaceHolder
     */
    private void VerticalBars(Canvas canvas){

        int amplitude = bloodPressure.getAmplitude(counter);
        lineColl.AddPoint(counter * widthTick, baseHeight-10);
        lineColl.AddPoint((float) (counter * widthTick),
                (float) (baseHeight - 5 + (bloodPressure.getDiastolic() * heightTick) - (amplitude * heightTick)));
        //float[] inputFloats = {counter * widthTick, baseHeight, counter * widthTick,(baseHeight +  (float) bloodPressure.getDiastolic()) - (amplitude * heightTick)};
        //createLineList(inputFloats);

        canvas.drawLines(lineColl.SplitList(), paint);
    }
    /**
     *
     * @param inputFloats
     */
    private void createLineList(float[] inputFloats) {
        float[] tempArray = new float[lineList.length + inputFloats.length];
        int index = lineList.length;
        for (int i = 0; i < lineList.length; i++){
            tempArray[i] = lineList[i];
        }
        for (int i = 0; i < inputFloats.length; i++){
            tempArray[i + index] = inputFloats[i];
        }
        lineList = tempArray;
    }
}
//sx, sy, ex, ey
/*
oxxxxxxxxx
y-amplitude
y
y
y-baseHeight
y
 */