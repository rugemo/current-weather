package com.rumodigi.currentweather.ui.presenter;

import android.app.Activity;
import android.location.Location;

import com.rumodigi.currentweather.framework.location.LocationHandler;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;
import com.rumodigi.domain.models.CurrentlyModel;
import com.rumodigi.domain.models.ForecastModel;
import com.rumodigi.domain.usecases.GetForecastDetailsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableSingleObserver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentWeatherPresenterTest {

    private CurrentWeatherPresenter<CurrentWeatherView> currentWeatherPresenter;
    private double latitude = 55.8685531;
    private double longitude = -4.2635288;
    private DisposableSingleObserver<ForecastModel> capturedObserver;

    @Mock
    LocationHandler mockLocationHandler;
    @Mock
    GetForecastDetailsUseCase mockGetForecastDetailsUseCase;
    @Mock
    CurrentWeatherView mockCurrentWeatherView;
    @Mock
    LocationResultListener mockLocationResultListener;
    @Mock
    Location mockLocation;
    @Mock
    ForecastModel mockForecastModel;
    @Mock
    CurrentlyModel mockCurrentlyModel;
    @Captor
    ArgumentCaptor<DisposableSingleObserver<ForecastModel>> observerArgumentCaptor;

    @Before
    public void setUp() {
        when(mockLocation.getLatitude()).thenReturn(latitude);
        when(mockLocation.getLongitude()).thenReturn(longitude);
        currentWeatherPresenter = new CurrentWeatherPresenter<>(mockLocationHandler, mockGetForecastDetailsUseCase);
        currentWeatherPresenter.onViewCreated(mockCurrentWeatherView);
        currentWeatherPresenter.setLocationResultListener(mockLocationResultListener);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void whenGetLocationIsCalled_checkThatLocationHandlerIsActioned(){
        currentWeatherPresenter.getLocation();
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).showProgressSpinner();
        verify(mockLocationHandler).getUserLocation();
    }

    @Test
    public void whenCorrectPermissionReceived_locationIsRequested(){
        currentWeatherPresenter.permissionResultsReceived(LocationHandler.LOCATION_REQUEST_CODE);
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).showProgressSpinner();
        verify(mockLocationHandler).getUserLocation();
    }

    @Test
    public void whenIncorrectPermissionReceived_locationIsNotRequested(){
        currentWeatherPresenter.permissionResultsReceived(9999);
        verifyZeroInteractions(mockCurrentWeatherView);
    }

    @Test
    public void whenLocationServicesIsGranted_locationIsRequested(){
        currentWeatherPresenter.resultReceived(LocationHandler.LOCATION_REQUEST_CODE, Activity.RESULT_OK);
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).showProgressSpinner();
        verify(mockLocationHandler).getUserLocation();
    }

    @Test
    public void whenLocationServicesIsDenied_locationIsRequested(){
        currentWeatherPresenter.resultReceived(LocationHandler.LOCATION_REQUEST_CODE, Activity.RESULT_CANCELED);
        verify(mockCurrentWeatherView).showErrorMessage();
        verify(mockCurrentWeatherView).hideProgressSpinner();
    }

    @Test
    public void whenPermissionHasBeenDeniedBefore_showRetryMessageAndRetryButton(){
        currentWeatherPresenter.locationPermissionPreviouslyDenied();
        verify(mockCurrentWeatherView).hideProgressSpinner();
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).hideUpdateForecastButton();
        verify(mockCurrentWeatherView).showRetryMessage();
        verify(mockCurrentWeatherView).showPermissionRetryButton();
    }

    @Test
    public void whenPermissionHasBeenDeniedBeforeWithNeverAskAgain_showGotoSettingsMessage(){
        currentWeatherPresenter.locationPermissionPreviouslyDeniedWithNeverAskAgain();
        verify(mockCurrentWeatherView).hideProgressSpinner();
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).hideUpdateForecastButton();
        verify(mockCurrentWeatherView).hidePermissionRetryButton();
        verify(mockCurrentWeatherView).hideUpdateForecastButton();
        verify(mockCurrentWeatherView).showGotoSettingsMessage();
    }

    @Test
    public void whenLocationIsFound_refreshWeatherData_summaryIsClear_noPreciptiation(){
        when(mockCurrentlyModel.getSummary()).thenReturn("Clear");
        when(mockCurrentlyModel.getPrecipType()).thenReturn(null);
        when(mockForecastModel.getCurrentlyModel()).thenReturn(mockCurrentlyModel);
        currentWeatherPresenter.refreshWeatherDetails(mockLocation);
        verify(mockGetForecastDetailsUseCase).execute(observerArgumentCaptor.capture(), anyDouble(), anyDouble());
        capturedObserver = observerArgumentCaptor.getValue();
        capturedObserver.onSuccess(mockForecastModel);
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).hideRetryMessage();
        verify(mockCurrentWeatherView).hideGotoSettingsMessage();
        verify(mockCurrentWeatherView).hidePermissionRetryButton();
        verify(mockCurrentWeatherView).showUpdateForecastButton();
        verify(mockCurrentWeatherView).updateDateAndTime(anyString());
        verify(mockCurrentWeatherView).updateLatLong(anyDouble(), anyDouble());
        verify(mockCurrentWeatherView).updateSummary(anyString());
        verify(mockCurrentWeatherView).updateTemp(anyDouble());
        verify(mockCurrentWeatherView).noPrecipitation();
        verify(mockCurrentWeatherView).updateCloudCover(anyString());
        verify(mockCurrentWeatherView).hideProgressSpinner();
    }

    @Test
    public void whenLocationIsFound_refreshWeatherData_summaryIsCloudy_itIsRaining(){
        when(mockCurrentlyModel.getSummary()).thenReturn("Cloudy");
        when(mockCurrentlyModel.getPrecipType()).thenReturn("Rain");
        when(mockForecastModel.getCurrentlyModel()).thenReturn(mockCurrentlyModel);
        currentWeatherPresenter.refreshWeatherDetails(mockLocation);
        verify(mockGetForecastDetailsUseCase).execute(observerArgumentCaptor.capture(), anyDouble(), anyDouble());
        capturedObserver = observerArgumentCaptor.getValue();
        capturedObserver.onSuccess(mockForecastModel);
        verify(mockCurrentWeatherView).hideErrorMessage();
        verify(mockCurrentWeatherView).hideRetryMessage();
        verify(mockCurrentWeatherView).hideGotoSettingsMessage();
        verify(mockCurrentWeatherView).hidePermissionRetryButton();
        verify(mockCurrentWeatherView).showUpdateForecastButton();
        verify(mockCurrentWeatherView).updateDateAndTime(anyString());
        verify(mockCurrentWeatherView).updateLatLong(anyDouble(), anyDouble());
        verify(mockCurrentWeatherView).updateSummary(anyString());
        verify(mockCurrentWeatherView).updateTemp(anyDouble());
        verify(mockCurrentWeatherView).updatePrecipitation(anyString());
        verify(mockCurrentWeatherView).updateCloudCover(anyString());
        verify(mockCurrentWeatherView).hideProgressSpinner();
    }

    @Test
    public void whenLocationIsNotFound_showErrorMessage(){
        currentWeatherPresenter.refreshWeatherDetails(mockLocation);
        verify(mockGetForecastDetailsUseCase).execute(observerArgumentCaptor.capture(), anyDouble(), anyDouble());
        capturedObserver = observerArgumentCaptor.getValue();
        capturedObserver.onError(new Throwable());
        verify(mockCurrentWeatherView).hideProgressSpinner();
        verify(mockCurrentWeatherView).showErrorMessage();
    }
}