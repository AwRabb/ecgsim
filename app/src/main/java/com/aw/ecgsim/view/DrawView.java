package com.aw.ecgsim.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Andrew Rabb on 2016-07-10.
 */
public class DrawView extends Canvas {


    Paint paint;
    public DrawView(Paint paint) {
    this.paint = paint;
    }

    public DrawView(Bitmap bitmap, Paint paint) {
        super(bitmap);
        this.paint = paint;
        }


    @Override
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        super.drawRect(left, top, right, bottom, paint);
    }



}
