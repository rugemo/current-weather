package com.rumodigi.currentweather.framework.utils;

import com.rumodigi.currentweather.BuildConfig;
import com.rumodigi.data.api.ApiDetails;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApiDetailsHandler implements ApiDetails {

    @Inject
    ApiDetailsHandler(){}

    @Override
    public String getHost() {
        return BuildConfig.DARK_SKY_API;
    }

    @Override
    public String getApiKey() {
        return BuildConfig.DARK_SKY_KEY;
    }
}
