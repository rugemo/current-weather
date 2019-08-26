package com.rumodigi.domain.usecases;

import com.rumodigi.domain.executors.PostExecutorThread;
import com.rumodigi.domain.executors.ThreadExecutor;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class UseCase {
    final ThreadExecutor threadExecutor;
    final PostExecutorThread postExecutorThread;
    private final CompositeDisposable compositeDisposable;

    UseCase(ThreadExecutor threadExecutor, PostExecutorThread postExecutorThread){
        this.threadExecutor = threadExecutor;
        this.postExecutorThread = postExecutorThread;
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
