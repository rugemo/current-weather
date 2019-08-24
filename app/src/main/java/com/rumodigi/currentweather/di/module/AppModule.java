package com.rumodigi.currentweather.di.module;

import android.content.Context;
import android.location.LocationManager;

import com.rumodigi.currentweather.di.qualifier.ApplicationContext;
import com.rumodigi.currentweather.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;
    private LocationManager locationManager;

    public AppModule(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context provideContext() {
        return context;
    }

    @Provides
    @ApplicationScope
    LocationManager provideLocationManager() {
        return locationManager;
    }
}
