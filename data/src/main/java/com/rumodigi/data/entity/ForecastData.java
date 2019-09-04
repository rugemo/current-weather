package com.rumodigi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastData {
    private double latitude;
    private double longitude;
    private CurrentlyData currently;
    private HourlyData hourly;

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

    public CurrentlyData getCurrently() {
        return currently;
    }

    public void setCurrently(CurrentlyData currently) {
        this.currently = currently;
    }

    public HourlyData getHourly() {
        return hourly;
    }

    public void setHourly(HourlyData hourly) {
        this.hourly = hourly;
    }
}
