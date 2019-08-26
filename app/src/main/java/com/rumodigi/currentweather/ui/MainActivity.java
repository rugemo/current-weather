package com.rumodigi.currentweather.ui;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.rumodigi.currentweather.CurrentWeatherApplication;
import com.rumodigi.currentweather.R;
import com.rumodigi.currentweather.di.component.ActivityComponent;
import com.rumodigi.currentweather.di.component.DaggerActivityComponent;
import com.rumodigi.currentweather.di.module.ActivityModule;
import com.rumodigi.currentweather.framework.location.LocationHandler;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.presenter.CurrentWeatherPresenter;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements CurrentWeatherView, LocationResultListener {

    ActivityComponent activityComponent;

    @Inject
    CurrentWeatherPresenter<MainActivity> currentWeatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(CurrentWeatherApplication.get(this).getApplicationComponent())
                .build();

        activityComponent.injectMainActivity(this);

        currentWeatherPresenter.onViewCreated(this);
        currentWeatherPresenter.setLocationResultListener(this);
        currentWeatherPresenter.getLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocationHandler.LOCATION_REQUEST_CODE) {
            currentWeatherPresenter.getLocation();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LocationHandler.LOCATION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                currentWeatherPresenter.getLocation();
            } else if (resultCode == RESULT_CANCELED){
                //TODO - display a message and provide a button to allow access to location services
            }
        }
    }

    @Override
    public void updateTemp(String temp) {

    }

    @Override
    public void setLocation(Location location) {
        currentWeatherPresenter.refreshWeatherDetails(location);
        System.out.println("Latitude: " + location.getLatitude());
        System.out.println("Longitude: " + location.getLongitude());

    }
}
