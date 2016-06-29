package com.aw.ecgsim.business;

import android.util.Log;

/**
 * Blood Pressure Line simulation. accepts an input time, and returns the current Blood Pressure (amplitude)
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
    /**
     * TAG value, for differentiating logs
     */
    final private String Tag = "BloodPressure.business";

    /**
     * accepts the time, and returns the current amplitude of the Line. In this case, the current blood pressure.
     * @param time time parameter
     * @return blood pressure rating (in mmhg/Torr I believe)
     */
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
        return (int)amplitude;
    }

    /**
     * returns diastolic value
     * @return - diastolic
     */
    public double getDiastolic() {
        return diastolic;
    }

    /**
     * returns heart Rate value
     * @return - heart Rate
     */
    public double getHeartRate() {
        return heartRate;
    }

    /**
     * returns systolic value
     * @return - systolic
     */
    public double getSystolic() {
        return systolic;
    }

    /**
     * sets Diastolic Value. must be smaller than systolic, otherwise will be set to default (80), unless systolic <80, than systolic-1. also must be > 0
     * @param diastolic value to set
     * @return true if worked set to input value, false otherwise
     */
    public boolean setDiastolic(double diastolic) {
        if (diastolic >= systolic || diastolic == 0){
            Log.d(Tag ,"diastolic higher than systolic, or diastolic = 0, setting Default diastolic (80)");
            if (systolic < 80){
                this.diastolic = systolic - 1;
                Log.d(Tag, "systolic higher than default, settings to systolic - 1, or " + diastolic);
                return false;
            }
            this.diastolic = 80;
            return false;
        }
        if (diastolic == 0){
            this.diastolic = 80;
        }
        this.diastolic = diastolic;
        return true;
    }

    /**
     * sets heart rate to the input value
     * @param heartRate - heart rate to be input
     */
    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    /**
     * sets systolic Value. must be larger than diastolic, otherwise will be set to default (120), unless diastolic > 120, than diastolic + 1. also must be >0
     * @param systolic - systolic value to be input
     * @return true if value input is entered. false if value is set to default or 1 + diastolic
     */
    public boolean setSystolic(double systolic) {
        if (diastolic >= systolic || systolic == 0){
            this.systolic = 120;
            Log.d(Tag ,"diastolic higher than systolic, or systolic = 0, setting Default systolic " + systolic);
            if (diastolic > 120){
                this.systolic = diastolic + 1;
                Log.d(Tag, "systolic higher than default, settings to systolic - 1, or " + systolic);
                return false;
            }
            return false;
        }
        this.systolic = systolic;
        return true;
    }

    /**
     * gets the amplitude of the blood Pressure.
     * @param time current time in heart beat cycle
     * @return current Blood Pressure
     */
    @Override
    public int getAmplitude(int time) {
        return Amplitude(time);
    }
}
