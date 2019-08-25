package com.rumodigi.currentweather.di.module;

import android.app.Activity;
import android.content.Context;

import com.rumodigi.currentweather.di.qualifier.ActivityContext;
import com.rumodigi.currentweather.di.scope.ActivityScope;
import com.rumodigi.currentweather.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private MainActivity mainActivity;

    public Context context;

    public ActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        context = mainActivity;
    }

    @Provides
    @ActivityScope
    Activity providesMainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    Context provideContext() {
        return context;
    }
}
