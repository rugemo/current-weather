package com.rumodigi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastData {
    private double latitude;
    private double longitude;
    private CurrentlyData currently;

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
}
