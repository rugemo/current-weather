package com.rumodigi.currentweather;

import android.app.Application;

import com.rumodigi.currentweather.di.component.ApplicationComponent;

public class CurrentWeatherApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
