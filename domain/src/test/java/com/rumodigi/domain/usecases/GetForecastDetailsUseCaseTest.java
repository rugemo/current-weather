package com.rumodigi.domain.usecases;

import com.rumodigi.domain.executors.PostExecutionThread;
import com.rumodigi.domain.models.ForecastModel;
import com.rumodigi.domain.repositories.ForecastRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetForecastDetailsUseCaseTest {

    @Mock
    ForecastRepository mockForecastRepository;
    @Mock
    PostExecutionThread mockPostExecutionThread;

    private DisposableSingleObserver<ForecastModel> testSingleObserverInstance;
    private Single<ForecastModel> testSingleForecast;

    private double latitude = 55.8685531;
    private double longitude = -4.2635288;


    @Before
    public void setUp() {
        testSingleForecast = new Single<ForecastModel>() {
            @Override
            protected void subscribeActual(SingleObserver<? super ForecastModel> observer) {}
        };
        testSingleObserverInstance = new DisposableSingleObserver<ForecastModel>() {
            @Override
            public void onSuccess(ForecastModel forecastModel) {}

            @Override
            public void onError(Throwable e) {}
        };
        when(mockPostExecutionThread.getScheduler()).thenReturn(Schedulers.newThread());
        when(mockForecastRepository.getForecast(latitude, longitude)).thenReturn(testSingleForecast);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void onExecute_verifyForecastIsRequested_andPostExecutionThreadIsObserved() {
        GetForecastDetailsUseCase getForecastDetailsUseCase = new GetForecastDetailsUseCase(mockForecastRepository, mockPostExecutionThread);
        getForecastDetailsUseCase.execute(testSingleObserverInstance, latitude, longitude);
        verify(mockForecastRepository).getForecast(latitude, longitude);
        verify(mockPostExecutionThread).getScheduler();
    }
}