package com.rumodigi.currentweather.ui.presenter;

import androidx.annotation.NonNull;

import com.rumodigi.currentweather.framework.location.ManageLocation;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

public class CurrentWeatherPresenter<T extends CurrentWeatherView> {
    private WeakReference<T> currentWeatherView;
    private ManageLocation manageLocation;

    @Inject
    CurrentWeatherPresenter(ManageLocation manageLocation) {
        this.manageLocation = manageLocation;
    }

    public void onViewCreated(@NonNull final T view) {
        currentWeatherView = new WeakReference<>(requireNonNull(view));
    }

    public void refreshWeatherDetails() {
        manageLocation.updateLocation();
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
