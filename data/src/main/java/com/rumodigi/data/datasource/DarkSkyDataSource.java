package com.rumodigi.data.datasource;

import com.rumodigi.data.entity.ForecastData;

import io.reactivex.Single;
import retrofit2.http.Path;

public interface DarkSkyDataSource {
    Single<ForecastData> getForecast(@Path("lat") double latitude,
                                     @Path("long") double longitude);
}
