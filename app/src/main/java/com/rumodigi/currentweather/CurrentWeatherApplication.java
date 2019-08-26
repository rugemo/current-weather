package com.rumodigi.currentweather;

import android.app.Activity;
import android.app.Application;

import com.rumodigi.currentweather.di.component.ApplicationComponent;
import com.rumodigi.currentweather.di.component.DaggerApplicationComponent;
import com.rumodigi.currentweather.di.module.AppModule;

public class CurrentWeatherApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();

        getApplicationComponent().injectApplication(this);
    }

    public static CurrentWeatherApplication get(Activity activity){
        return (CurrentWeatherApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
