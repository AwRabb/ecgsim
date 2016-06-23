package com.aw.ecgsim.business;

import android.util.Log;

/**
 * Created by Andrew Rabb on 2016-06-22.
 */
public class bloodPressure implements Line {

    /**
     * default constructor
     */
    public bloodPressure(){
        systolic = 120;
        diastolic = 80;
        heartRate = 45;
    }

    /**
     * Parameterized constructor for ease of creation
     * @param heartRate - heart rate
     * @param systolic - highest blood pressure
     * @param diastolic - lowest blood pressure
     */
    public bloodPressure(int heartRate, int systolic, int diastolic){
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartRate = heartRate;
    }
    /**
     * rate of heartbeats. 1 heartbeat every heartRate seconds (0 start assumed)
     */
    private double heartRate;
    /**
     * Highest point of blood pressure during a heart beat. Blood pressure usually relayed as systolic/diastolic
     */
    private double systolic;
    /**
     * Lowest point of blood pressure during a heart beat. baseline level.
     */
    private double diastolic;

    private int Amplitude(int time){
        double amplitude;

        double cycleTime = time;



        if (cycleTime > heartRate) {
            cycleTime -= heartRate;
            if (cycleTime > heartRate) {
                cycleTime -= heartRate;
            }
        }
        if (cycleTime > heartRate * 0.75){ cycleTime = heartRate * 0.75;   }




        double temp = (cycleTime / (heartRate * 0.75));

        amplitude = systolic - (temp * (systolic - diastolic));

        if ((time & 1) == 0) {
            Log.d("BP cycleTime = ", String.valueOf(cycleTime));
            Log.d("BP temp = ", String.valueOf(temp));
            Log.d("BP amplitude = ", String.valueOf(amplitude));
        }
        return (int)amplitude;
    }









    @Override
    public int getAmplitude(int time) {
        return Amplitude(time);
    }
}
