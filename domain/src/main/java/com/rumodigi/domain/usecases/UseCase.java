package com.rumodigi.domain.usecases;

import com.rumodigi.domain.executors.PostExecutionThread;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class UseCase {
    final PostExecutionThread postExecutionThread;
    private final CompositeDisposable compositeDisposable;

    UseCase(PostExecutionThread postExecutionThread){
        this.postExecutionThread = postExecutionThread;
        compositeDisposable = new CompositeDisposable();
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
