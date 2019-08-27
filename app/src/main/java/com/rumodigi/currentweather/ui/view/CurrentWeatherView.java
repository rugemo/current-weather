package com.rumodigi.currentweather.ui.view;

public interface CurrentWeatherView {
    void updateTemp(Double temp);
    void updatePrecipitation(String precipitationChance, String precipitationType);
    void updateCloudCover(String cloudCover);
    void noPrecipitation();
    void updateDateAndTime(String time);
    void updateLatLong(double latitude, double longitude);
    void updateSummary(String summary);
    void showErrorMessage();
    void hideErrorMessage();
    void showProgressSpinner();
    void hideProgressSpinner();
}
