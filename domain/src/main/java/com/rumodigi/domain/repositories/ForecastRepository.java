package com.rumodigi.domain.repositories;

import com.rumodigi.domain.models.Forecast;

import io.reactivex.Single;

public interface ForecastRepository {
    Single<Forecast> getForecast(double latitude, double longitude);
}
