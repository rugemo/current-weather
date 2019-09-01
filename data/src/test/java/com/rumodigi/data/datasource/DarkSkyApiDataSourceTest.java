package com.rumodigi.data.datasource;

import com.rumodigi.data.api.ApiDetails;
import com.rumodigi.data.api.DarkSkyService;
import com.rumodigi.data.entity.ForecastData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DarkSkyApiDataSourceTest {

    private final String TEST_API_KEY = "test-api-key";
    private double latitude = 55.8685531;
    private double longitude = -4.2635288;

    @Mock
    DarkSkyService mockDarkSkyService;
    @Mock
    ApiDetails mockApiDetails;
    @Mock
    Single<ForecastData> mockForecastDataSingle;

    @Test
    public void whenGetForcastIsCalled_confirmCallToApiServiceIsMadeToGetForecast(){
        when(mockApiDetails.getApiKey()).thenReturn(TEST_API_KEY);
        when(mockDarkSkyService.getForecast(anyString(), anyDouble(), anyDouble())).thenReturn(mockForecastDataSingle);
        DarkSkyDataSource darkSkyDataSource = new DarkSkyApiDataSource(mockDarkSkyService, mockApiDetails);
        darkSkyDataSource.getForecast(latitude, longitude);
        verify(mockApiDetails).getApiKey();
        verify(mockDarkSkyService).getForecast(TEST_API_KEY, latitude, longitude);
    }
}