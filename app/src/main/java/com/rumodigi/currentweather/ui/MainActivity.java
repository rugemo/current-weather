package com.rumodigi.currentweather.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rumodigi.currentweather.CurrentWeatherApplication;
import com.rumodigi.currentweather.R;
import com.rumodigi.currentweather.di.component.ActivityComponent;
import com.rumodigi.currentweather.di.component.DaggerActivityComponent;
import com.rumodigi.currentweather.di.module.ActivityModule;
import com.rumodigi.currentweather.ui.presenter.CurrentWeatherPresenter;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements CurrentWeatherView {

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
    }

    public void onRefreshWeatherClicked() {
        currentWeatherPresenter.refreshWeatherDetails();
    }

    @Override
    public void updateTemp(String temp) {

    }
}
