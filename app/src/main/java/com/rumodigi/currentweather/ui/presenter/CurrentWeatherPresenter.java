package com.rumodigi.currentweather.ui.presenter;

import androidx.annotation.NonNull;
import com.rumodigi.currentweather.framework.location.LocationHandler;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

public class CurrentWeatherPresenter<T extends CurrentWeatherView> {
    private WeakReference<T> currentWeatherView;
    private LocationHandler locationHandler;

    @Inject
    CurrentWeatherPresenter(LocationHandler locationHandler) {
        this.locationHandler = locationHandler;
    }

    public void onViewCreated(@NonNull final T view) {
        currentWeatherView = new WeakReference<>(requireNonNull(view));
    }

    public void setLocationResultListener(LocationResultListener locationResultListener) {
        this.locationHandler.setUpLocationCallback(locationResultListener);
    }

    public void refreshWeatherDetails() {

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
