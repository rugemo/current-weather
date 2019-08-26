package com.rumodigi.currentweather.ui.presenter;

import android.location.Location;

import androidx.annotation.NonNull;

import com.rumodigi.currentweather.framework.location.LocationHandler;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;
import com.rumodigi.domain.models.Forecast;
import com.rumodigi.domain.usecases.GetForecastDetailsUseCase;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

import static java.util.Objects.requireNonNull;

public class CurrentWeatherPresenter<T extends CurrentWeatherView> {
    private WeakReference<T> currentWeatherView;
    private LocationHandler locationHandler;
    private GetForecastDetailsUseCase getForecastDetailsUseCase;
    private DisposableSingleObserver<Forecast> forecastObserver;

    @Inject
    CurrentWeatherPresenter(LocationHandler locationHandler, GetForecastDetailsUseCase getForecastDetailsUseCase) {
        this.locationHandler = locationHandler;
        this.getForecastDetailsUseCase = getForecastDetailsUseCase;
    }

    public void onViewCreated(@NonNull final T view) {
        this.currentWeatherView = new WeakReference<>(requireNonNull(view));
    }

    public void setLocationResultListener(LocationResultListener locationResultListener) {
        this.locationHandler.setUpLocationCallback(locationResultListener);
    }

    public void getLocation() {
        getView().showProgressSpinner();
        this.locationHandler.getUserLocation();
    }

    public void refreshWeatherDetails(Location location) {
        forecastObserver = new DisposableSingleObserver<Forecast>() {
            @Override
            public void onSuccess(Forecast forecast) {
                getView().hideErrorMessage();
                getView().updateTimezone(forecast.getTimezone());
                getView().updateLatLong(forecast.getLatitude(), forecast.getLongitude());
                getView().hideProgressSpinner();
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgressSpinner();
                getView().showErrorMessage();
                e.printStackTrace();
            }
        };
        getForecastDetailsUseCase.execute(forecastObserver, location.getLatitude(), location.getLongitude());
        getView().updateTemp("10");
    }

    // Would move all this to a base presenter if app grew in size

    private T getView() {
        if (!isViewAttached()) {
            throw new ViewNotAttachedException();
        } else {
            return currentWeatherView.get();
        }
    }

    private boolean isViewAttached() {
        return currentWeatherView != null && currentWeatherView.get() != null;
    }

    private static class ViewNotAttachedException extends IllegalStateException {

        ViewNotAttachedException() {
            super("Trying to access a view that is not attached");
        }
    }


}
