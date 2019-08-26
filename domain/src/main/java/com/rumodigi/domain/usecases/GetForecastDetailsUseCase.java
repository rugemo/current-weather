package com.rumodigi.domain.usecases;

import com.rumodigi.domain.executors.PostExecutorThread;
import com.rumodigi.domain.executors.ThreadExecutor;
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
                              ThreadExecutor threadExecutor,
                              PostExecutorThread postExecutorThread) {
        super(threadExecutor, postExecutorThread);
        this.forecastRepository = forecastRepository;
    }

    private Single<Forecast> buildUseCase() {
        return this.forecastRepository.getForecast();
    }

    @Override
    public void execute(DisposableSingleObserver<Forecast> disposableSingleObserver) {
        final Single<Forecast> forecastSingle = buildUseCase()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutorThread.getScheduler());
        addDisposable(forecastSingle.subscribeWith(disposableSingleObserver));
    }
}
