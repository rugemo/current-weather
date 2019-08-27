package com.rumodigi.data.repository;


import com.rumodigi.data.datasource.DarkSkyApiDataSource;
import com.rumodigi.data.entity.mapper.ForecastDataMap;
import com.rumodigi.domain.models.ForecastModel;
import com.rumodigi.domain.repositories.ForecastRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ForecastDataRepository implements ForecastRepository {
    private final DarkSkyApiDataSource darkSkyApiDataSource;
    private final ForecastDataMap forecastDataMap;

    @Inject
    ForecastDataRepository(DarkSkyApiDataSource darkSkyApiDataSource,
                           ForecastDataMap forecastDataMap){
        this.darkSkyApiDataSource = darkSkyApiDataSource;
        this.forecastDataMap = forecastDataMap;
    }

    @Override
    public Single<ForecastModel> getForecast(double latitude, double longitude) {
        return darkSkyApiDataSource.getForecast(latitude, longitude).map(this.forecastDataMap :: transform);
    }
}
