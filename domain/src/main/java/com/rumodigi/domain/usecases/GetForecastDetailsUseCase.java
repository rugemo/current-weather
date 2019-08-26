package com.rumodigi.domain.usecases;

import com.rumodigi.domain.executors.PostExecutionThread;
import com.rumodigi.domain.models.Forecast;
import com.rumodigi.domain.repositories.ForecastRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GetForecastDetailsUseCase extends UseCase implements SingleUseCase<Forecast> {

    private final ForecastRepository forecastRepository;

    @Inject
    GetForecastDetailsUseCase(ForecastRepository forecastRepository,
                              PostExecutionThread postExecutionThread) {
        super(postExecutionThread);
        this.forecastRepository = forecastRepository;
    }

    private Single<Forecast> buildUseCase(double latitude, double longitude) {
        return this.forecastRepository.getForecast(latitude, longitude);
    }

    @Override
    public void execute(DisposableSingleObserver<Forecast> disposableSingleObserver,
                        double latitude,
                        double longitude) {
        final Single<Forecast> forecastSingle = buildUseCase(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(forecastSingle.subscribeWith(disposableSingleObserver));

    }
}
