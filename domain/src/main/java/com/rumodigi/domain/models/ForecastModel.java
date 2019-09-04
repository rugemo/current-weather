package com.rumodigi.domain.models;

public class ForecastModel {
    private double latitude;
    private double longitude;
    private CurrentlyModel currentlyModel;
    private HourlyModel hourlyModel;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public CurrentlyModel getCurrentlyModel() {
        return currentlyModel;
    }

    public void setCurrentlyModel(CurrentlyModel currentlyModel) {
        this.currentlyModel = currentlyModel;
    }

    public HourlyModel getHourlyModel() {
        return hourlyModel;
    }

    public void setHourlyModel(HourlyModel hourlyModel) {
        this.hourlyModel = hourlyModel;
    }
}
