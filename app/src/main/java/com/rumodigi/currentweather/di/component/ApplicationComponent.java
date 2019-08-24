package com.rumodigi.currentweather.di.component;

import android.content.Context;

import com.rumodigi.currentweather.CurrentWeatherApplication;
import com.rumodigi.currentweather.di.module.ContextModule;
import com.rumodigi.currentweather.di.qualifier.ApplicationContext;
import com.rumodigi.currentweather.di.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    void injectApplication(CurrentWeatherApplication currentWeatherApplication);
}
