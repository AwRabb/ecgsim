package com.aw.ecgsim.business;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andrew Rabb on 2016-08-16.
 */
public class LineColl {

    float[] point;
    ArrayList<Float> PointList = new ArrayList<>();

    public void AddPoint(Float a, Float b){
        PointList.add(a);
        PointList.add(b);
    }
    public void AddPoint(int a, int b){
        AddPoint(Float.valueOf(a), Float.valueOf(b));
    }
    public float[] SplitList(){
        point = new float[PointList.size()];
        int i = 0;
        for (Float x : PointList){
            point[i] = x;//0 - 1
            i++;
        }
        return point;
    }
}
