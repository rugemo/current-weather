package com.rumodigi.domain.usecases;

import io.reactivex.observers.DisposableSingleObserver;

public interface SingleUseCase<T> {
    void execute(DisposableSingleObserver<T> disposableSingleObserver);
}
