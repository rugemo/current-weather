package com.rumodigi.domain.repositories;

import com.rumodigi.domain.models.ForecastModel;

import io.reactivex.Single;

public interface ForecastRepository {
    Single<ForecastModel> getForecast(double latitude, double longitude);
}
