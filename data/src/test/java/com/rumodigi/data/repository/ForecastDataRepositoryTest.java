package com.rumodigi.data.repository;

import com.rumodigi.data.datasource.DarkSkyApiDataSource;
import com.rumodigi.data.entity.ForecastData;
import com.rumodigi.data.entity.mapper.ForecastDataMap;
import com.rumodigi.domain.repositories.ForecastRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForecastDataRepositoryTest {

    private double latitude = 55.8685531;
    private double longitude = -4.2635288;

    @Mock
    DarkSkyApiDataSource mockDarkSkyDataSource;
    @Mock
    ForecastDataMap mockForecastDataMap;
    @Mock
    Single<ForecastData> mockApiResponse;

    @Test
    public void whenGetForecastIsCalled_confirmCallIsMadeToGetForecastData(){
        when(mockDarkSkyDataSource.getForecast(anyDouble(), anyDouble())).thenReturn(mockApiResponse);
        ForecastRepository forecastRepository = new ForecastDataRepository(mockDarkSkyDataSource, mockForecastDataMap);
        forecastRepository.getForecast(latitude, longitude);
        verify(mockDarkSkyDataSource).getForecast(latitude, longitude);
    }
}