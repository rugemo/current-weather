package com.rumodigi.data.entity.mapper;

import com.rumodigi.data.entity.CurrentlyData;
import com.rumodigi.data.entity.ForecastData;
import com.rumodigi.domain.models.CurrentlyModel;
import com.rumodigi.domain.models.ForecastModel;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ForecastDataMapTest {

    private ForecastData forecastData;
    private CurrentlyData currentlyData;
    private double latitude = 55.8685531;
    private double longitude = -4.2635288;
    private double temp = 40.0;
    private double precipProbability = 0.60;
    private double cloudCover = 0.97;
    private String precipType = "Snow";
    private String summary = "Winter is coming";

    @Before
    public void setUp(){
        forecastData = new ForecastData();
        currentlyData = new CurrentlyData();
        currentlyData.setTime(Instant.now().getEpochSecond());
        currentlyData.setTemperature(temp);
        currentlyData.setPrecipProbability(precipProbability);
        currentlyData.setPrecipType(precipType);
        currentlyData.setSummary(summary);
        currentlyData.setCloudCover(cloudCover);

        forecastData.setLatitude(latitude);
        forecastData.setLongitude(longitude);
        forecastData.setCurrently(currentlyData);
    }

    @Test
    public void checkTransformation_ForecastData_to_ForecastModel() {
        ForecastDataMap forecastDataMap = new ForecastDataMap();
        ForecastModel expectedForecastModel = forecastDataMap.transform(forecastData);
        assertThat(expectedForecastModel, is(instanceOf(ForecastModel.class)));
        assertThat(expectedForecastModel.getCurrentlyModel(), is(instanceOf(CurrentlyModel.class)));
        assertEquals(expectedForecastModel.getLatitude(), latitude, 0.0);
        assertEquals(expectedForecastModel.getLongitude(), longitude, 0.0);
        assertEquals(expectedForecastModel.getCurrentlyModel().getCloudCover(), cloudCover, 0.0);
        assertEquals(expectedForecastModel.getCurrentlyModel().getTemperature(), temp, 0.0);
        assertEquals(expectedForecastModel.getCurrentlyModel().getPrecipProbability(), precipProbability, 0.0);
        assertEquals(expectedForecastModel.getCurrentlyModel().getPrecipType(), precipType);
        assertEquals(expectedForecastModel.getCurrentlyModel().getSummary(), summary);
    }
}