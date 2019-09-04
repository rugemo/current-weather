package com.rumodigi.domain.models;

import java.util.List;

public class HourlyModel {
    private List<HourlyForecastModel> data;

    public List<HourlyForecastModel> getData() {
        return data;
    }

    public void setData(List<HourlyForecastModel> data) {
        this.data = data;
    }
}
