package com.aw.ecgsim.business;

/**
 * Created by Andrew Rabb on 2016-06-22.
 */
public interface Line {

    /**
     * Accepts the current time of heart beat or respiration and returns appropriate value
     * @param time current time in heart beat cycle
     * @return amplitude of the line at input time
     */
   public int getAmplitude(int time);


}
