package com.rumodigi.currentweather.di.component;

import android.content.Context;

import com.rumodigi.currentweather.CurrentWeatherApplication;
import com.rumodigi.currentweather.di.module.AppModule;
import com.rumodigi.currentweather.di.qualifier.ApplicationContext;
import com.rumodigi.data.api.ApiDetails;
import com.rumodigi.data.api.ApiModule;
import com.rumodigi.domain.executors.PostExecutionThread;
import com.rumodigi.domain.repositories.ForecastRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();
    ForecastRepository getForecastRepository();
    PostExecutionThread getPostExecutionThread();
    ApiDetails getApiDetails();

    void injectApplication(CurrentWeatherApplication currentWeatherApplication);
}
