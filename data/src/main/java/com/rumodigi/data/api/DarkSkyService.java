package com.rumodigi.data.api;

import com.rumodigi.data.entity.ForecastData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DarkSkyService {
    @GET("/forecast/{apiKey}/{lat},{long}")
    Single<ForecastData> getForecast(@Path("apiKey") String key,
                                     @Path("lat") double latitude,
                                     @Path("long") double longitude);
}

