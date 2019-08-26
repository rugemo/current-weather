package com.rumodigi.currentweather.ui.view;

public interface CurrentWeatherView {
    void updateTemp(String temp);
    void updateTimezone(String timezone);
    void updateLatLong(double latitude, double longitude);
    void showErrorMessage();
    void hideErrorMessage();
    void showProgressSpinner();
    void hideProgressSpinner();
}
