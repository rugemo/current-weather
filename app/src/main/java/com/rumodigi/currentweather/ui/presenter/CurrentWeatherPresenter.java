package com.rumodigi.currentweather.ui.presenter;

import android.location.Location;
import androidx.annotation.NonNull;

import com.rumodigi.currentweather.framework.location.LocationHandler;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;
import com.rumodigi.domain.models.ForecastModel;
import com.rumodigi.domain.usecases.GetForecastDetailsUseCase;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static java.util.Objects.requireNonNull;

public class CurrentWeatherPresenter<T extends CurrentWeatherView> {
    private WeakReference<T> currentWeatherView;
    private LocationHandler locationHandler;
    private GetForecastDetailsUseCase getForecastDetailsUseCase;
    private DisposableSingleObserver<ForecastModel> forecastObserver;

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
        getView().hideErrorMessage();
        getView().showProgressSpinner();
        this.locationHandler.getUserLocation();
    }

    public void refreshWeatherDetails(Location location) {
        forecastObserver = new DisposableSingleObserver<ForecastModel>() {

            @Override
            public void onSuccess(ForecastModel forecastModel) {
                getView().hideErrorMessage();
                getView().updateDateAndTime(getDateAndTime(forecastModel.getCurrentlyModel().getTime()));
                getView().updateLatLong(forecastModel.getLatitude(), forecastModel.getLongitude());
                getView().updateSummary(forecastModel.getCurrentlyModel().getSummary());
                getView().updateTemp(forecastModel.getCurrentlyModel().getTemperature());
                if (forecastModel.getCurrentlyModel().getPrecipType() == null) {
                    getView().noPrecipitation();
                } else {
                    getView().updatePrecipitation(
                            getPercentageFromDouble(forecastModel.getCurrentlyModel().getPrecipProbability()),
                            forecastModel.getCurrentlyModel().getPrecipType()
                    );
                }
                getView().updateCloudCover(getPercentageFromDouble(forecastModel.getCurrentlyModel().getCloudCover()));
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
    }

    public void permissionResultsRecieved(int requestCode) {
        if (requestCode == LocationHandler.LOCATION_REQUEST_CODE) {
            getLocation();
        }
    }

    public void resultRecieved(int requestCode, int resultCode) {
        if (requestCode == LocationHandler.LOCATION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getLocation();
            } else if (resultCode == RESULT_CANCELED){
                getView().hideProgressSpinner();
                getView().showErrorMessage();
            }
        }
    }

    private static class ViewNotAttachedException extends IllegalStateException {

        ViewNotAttachedException() {
            super("Trying to access a view that is not attached");
        }
    }

    private String getPercentageFromDouble(double valueToConvert){
        final DecimalFormat decimalFormat = new DecimalFormat("#%");
        return decimalFormat.format(valueToConvert);
    }

    private String getDateAndTime(int timestamp){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                .format(new Date(timestamp * 1000L));
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

}
