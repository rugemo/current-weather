package com.rumodigi.currentweather.ui.presenter;

import com.rumodigi.currentweather.framework.location.LocationHandler;
import com.rumodigi.currentweather.framework.location.LocationResultListener;
import com.rumodigi.currentweather.ui.view.CurrentWeatherView;
import com.rumodigi.domain.usecases.GetForecastDetailsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CurrentWeatherPresenterTest {

    private CurrentWeatherPresenter<CurrentWeatherView> currentWeatherPresenter;

    @Mock
    LocationHandler mockLocationHandler;
    @Mock
    GetForecastDetailsUseCase mockGetForecastDetailsUseCase;
    @Mock
    CurrentWeatherView mockCurrentWeatherView;
    @Mock
    LocationResultListener mockLocationResultListener;

    @Before
    public void setUp() {
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
}