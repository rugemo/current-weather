package com.rumodigi.data.datasource;

import com.rumodigi.data.api.ApiDetails;
import com.rumodigi.data.api.DarkSkyService;
import com.rumodigi.data.entity.ForecastData;

import javax.inject.Inject;

import io.reactivex.Single;

import static dagger.internal.Preconditions.checkNotNull;

public class DarkSkyApiDataSource implements DarkSkyDataSource {
    private DarkSkyService darkSkyService;
    private ApiDetails apiDetails;

    @Inject
    public DarkSkyApiDataSource(DarkSkyService darkSkyService, ApiDetails apiDetails){
        this.darkSkyService = checkNotNull(darkSkyService);
        this.apiDetails = apiDetails;
    }

    @Override
    public Single<ForecastData> getForecast(double latitude, double longitude) {
       return darkSkyService.getForecast(apiDetails.getApiKey(), latitude, longitude);
    }
}
