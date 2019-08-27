package com.rumodigi.domain.usecases;

import com.rumodigi.domain.executors.PostExecutionThread;
import com.rumodigi.domain.models.ForecastModel;
import com.rumodigi.domain.repositories.ForecastRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GetForecastDetailsUseCase extends UseCase implements SingleUseCase<ForecastModel> {

    private final ForecastRepository forecastRepository;

    @Inject
    GetForecastDetailsUseCase(ForecastRepository forecastRepository,
                              PostExecutionThread postExecutionThread) {
        super(postExecutionThread);
        this.forecastRepository = forecastRepository;
    }

    private Single<ForecastModel> buildUseCase(double latitude, double longitude) {
        return this.forecastRepository.getForecast(latitude, longitude);
    }

    @Override
    public void execute(DisposableSingleObserver<ForecastModel> disposableSingleObserver,
                        double latitude,
                        double longitude) {
        final Single<ForecastModel> forecastSingle = buildUseCase(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(forecastSingle.subscribeWith(disposableSingleObserver));

    }
}
