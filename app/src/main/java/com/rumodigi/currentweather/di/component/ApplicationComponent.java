package com.rumodigi.currentweather.di.component;

import android.content.Context;
import android.location.LocationManager;

import com.rumodigi.currentweather.CurrentWeatherApplication;
import com.rumodigi.currentweather.di.module.AppModule;
import com.rumodigi.currentweather.di.qualifier.ApplicationContext;
import com.rumodigi.currentweather.di.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    LocationManager getLocationManager();

    void injectApplication(CurrentWeatherApplication currentWeatherApplication);
}
