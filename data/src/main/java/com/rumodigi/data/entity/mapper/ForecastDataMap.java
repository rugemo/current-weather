package com.rumodigi.data.entity.mapper;

import com.rumodigi.data.entity.CurrentlyData;
import com.rumodigi.data.entity.ForecastData;
import com.rumodigi.data.entity.HourlyData;
import com.rumodigi.domain.models.CurrentlyModel;
import com.rumodigi.domain.models.ForecastModel;
import com.rumodigi.domain.models.HourlyModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ForecastDataMap {

    @Inject
    ForecastDataMap(){}

    public ForecastModel transform(ForecastData forecastData){
        ForecastModel forecastModel = null;
        if (forecastData != null) {
            forecastModel = new ForecastModel();
            forecastModel.setLatitude(forecastData.getLatitude());
            forecastModel.setLongitude(forecastData.getLongitude());
            forecastModel.setCurrentlyModel(transformCurrentlyData(forecastData.getCurrently()));
            forecastModel.setHourlyModel(transformHourlyData(forecastData.getHourly()));
        }
        return forecastModel;
    }

    private CurrentlyModel transformCurrentlyData(CurrentlyData currentlyData) {
        CurrentlyModel currentlyModel = null;
        if (currentlyData != null) {
            currentlyModel = new CurrentlyModel();
            currentlyModel.setTime(currentlyData.getTime());
            currentlyModel.setSummary(currentlyData.getSummary());
            currentlyModel.setPrecipProbability(currentlyData.getPrecipProbability());
            currentlyModel.setPrecipType(currentlyData.getPrecipType());
            currentlyModel.setTemperature(currentlyData.getTemperature());
            currentlyModel.setCloudCover(currentlyData.getCloudCover());
        }
        return currentlyModel;
    }

    private HourlyModel transformHourlyData(HourlyData hourlyData) {
         HourlyModel hourlyModel = null;
         if (hourlyData != null) {
             hourlyModel = new HourlyModel();
             hourlyModel.setData(hourlyData.getData());
         }
         return hourlyModel;
    }
}
