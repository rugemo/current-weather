package com.rumodigi.domain.executors;

import io.reactivex.Scheduler;

public interface PostExecutorThread {
    Scheduler getScheduler();
}
