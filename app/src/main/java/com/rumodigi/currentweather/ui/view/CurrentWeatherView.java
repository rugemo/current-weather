package com.rumodigi.currentweather.ui.view;

import com.rumodigi.domain.models.HourlyModel;

import java.util.List;

public interface CurrentWeatherView {
    void updateTemp(Double temp);
    void updatePrecipitation(String precipitationType);
    void updateCloudCover(String cloudCover);
    void updateHourlyData(HourlyModel hourlyModel);
    void noPrecipitation();
    void updateDateAndTime(String time);
    void updateLatLong(double latitude, double longitude);
    void updateSummary(String summary);
    void hideUpdateForecastButton();
    void showUpdateForecastButton();
    void hidePermissionRetryButton();
    void showPermissionRetryButton();
    void showErrorMessage();
    void hideErrorMessage();
    void showRetryMessage();
    void hideRetryMessage();
    void showProgressSpinner();
    void hideProgressSpinner();
    void showGotoSettingsMessage();
    void hideGotoSettingsMessage();
}
