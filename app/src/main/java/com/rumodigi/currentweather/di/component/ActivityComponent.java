package com.rumodigi.currentweather.di.component;

import android.app.Activity;
import android.content.Context;

import com.rumodigi.currentweather.di.module.ActivityModule;
import com.rumodigi.currentweather.di.qualifier.ActivityContext;
import com.rumodigi.currentweather.di.scope.ActivityScope;
import com.rumodigi.currentweather.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    @ActivityContext
    Context getContext();
    Activity getActivity();

    void injectMainActivity(MainActivity mainActivity);
}
