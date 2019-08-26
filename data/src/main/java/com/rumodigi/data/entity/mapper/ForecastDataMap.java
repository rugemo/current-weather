package com.rumodigi.data.entity.mapper;

import com.rumodigi.data.entity.ForecastData;
import com.rumodigi.domain.models.Forecast;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ForecastDataMap {

    @Inject
    ForecastDataMap(){}

    public Forecast transform(ForecastData forecastData){
        Forecast forecast = null;
        if (forecastData != null) {
            forecast = new Forecast();
            forecast.setLatitude(forecastData.getLatitude());
            forecast.setLongitude(forecastData.getLongitude());
            forecast.setTimezone(forecastData.getTimezone());
        }
        return forecast;
    }
}
