package com.presentech.handsup;

/**
 * Created by Alex on 01/03/2016.
 */
public class MediaElement extends MediaObject{
    int startTime;
    int duration;


    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
