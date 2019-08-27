package com.rumodigi.currentweather.ui;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.rumodigi.currentweather.CurrentWeatherApplication;
import com.rumodigi.currentweather.R;
import com.rumodigi.currentweather.di.component.ActivityComponent;
import com.rumodigi.currentweather.di.component.DaggerActivityComponent;
import com.rumodigi.currentweather.di.module.ActivityModule;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.presenter.CurrentWeatherPresenter;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements CurrentWeatherView, LocationResultListener {

    ActivityComponent activityComponent;

    @Inject
    CurrentWeatherPresenter<MainActivity> currentWeatherPresenter;

    @BindView(R.id.lastUpdateDetails)
    TextView lastUpdateDetails;
    @BindView(R.id.latLongDetails)
    TextView latLongDetails;
    @BindView(R.id.summaryDetails)
    TextView summaryDetails;
    @BindView(R.id.tempDetails)
    TextView tempDetails;
    @BindView(R.id.precipitationDetails)
    TextView precipitationDetails;
    @BindView(R.id.cloudCoverDetails)
    TextView cloudCoverDetails;
    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.updateForecastDetails)
    Button updateForecatDeatils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        currentWeatherPresenter.permissionResultsRecieved(requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        currentWeatherPresenter.resultRecieved(requestCode, resultCode);
    }

    @OnClick(R.id.updateForecastDetails)
    public void updateForecastDetails(){
        currentWeatherPresenter.getLocation();
    }


    @Override
    public void updateTemp(Double temp) {
        tempDetails.setText(getString(R.string.temp_detail,
                String.valueOf(temp))
        );
    }

    @Override
    public void updatePrecipitation(String precipitationChance, String precipitationType) {
        precipitationDetails.setText(getString(R.string.precipitation_detail,
                precipitationChance,
                precipitationType)
        );
    }

    @Override
    public void updateCloudCover(String cloudCover) {
        cloudCoverDetails.setText(getString(R.string.cloud_cover_details, cloudCover));
    }

    @Override
    public void noPrecipitation() {
        precipitationDetails.setText(getString(R.string.no_preciptation));
    }

    @Override
    public void updateDateAndTime(String time) {
        lastUpdateDetails.setText(time);
    }

    @Override
    public void updateLatLong(double latitude, double longitude) {
        latLongDetails.setText(getString(R.string.lat_long_details,
                String.valueOf(latitude),
                String.valueOf(longitude))
        );
    }

    @Override
    public void updateSummary(String summary) {
        summaryDetails.setText(summary);
    }

    @Override
    public void showErrorMessage() {
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMessage() {
        errorMessage.setVisibility(View.GONE);
    }

    @Override
    public void showProgressSpinner() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressSpinner() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setLocation(Location location) {
        currentWeatherPresenter.refreshWeatherDetails(location);
    }
}
