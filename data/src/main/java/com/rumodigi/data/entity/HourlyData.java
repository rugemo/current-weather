package com.rumodigi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rumodigi.domain.models.HourlyForecastModel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyData {
    private List<HourlyForecastData> data;

    public List<HourlyForecastData> getData() {
        return data;
    }

    public void setData(List<HourlyForecastData> data) {
        this.data = data;
    }
}
