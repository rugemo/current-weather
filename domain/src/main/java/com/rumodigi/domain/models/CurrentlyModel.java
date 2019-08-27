package com.rumodigi.domain.models;


import io.reactivex.annotations.Nullable;

public class CurrentlyModel {

    @Nullable
    private int time;
    @Nullable
    private String summary;
    @Nullable
    private double precipProbability;
    @Nullable
    private String precipType;
    @Nullable
    private double temperature;
    @Nullable
    private double cloudCover;


    public int getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }
}
