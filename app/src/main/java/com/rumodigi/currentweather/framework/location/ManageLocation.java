package com.rumodigi.currentweather.framework.location;

import android.location.LocationManager;

import com.rumodigi.currentweather.di.component.ApplicationComponent;

import javax.inject.Inject;

public class ManageLocation {

    private ApplicationComponent applicationComponent;
    private LocationManager locationManager;

    @Inject
    public ManageLocation(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        locationManager = this.applicationComponent.getLocationManager();
    }

    public void updateLocation(){

    }
}
