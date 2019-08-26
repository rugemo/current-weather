package com.rumodigi.currentweather.di.module;

import android.content.Context;

import com.rumodigi.currentweather.di.qualifier.ApplicationContext;
import com.rumodigi.currentweather.framework.utils.ApiDetailsHandler;
import com.rumodigi.currentweather.ui.UIThread;
import com.rumodigi.data.api.ApiDetails;
import com.rumodigi.data.repository.ForecastDataRepository;
import com.rumodigi.domain.executors.PostExecutionThread;
import com.rumodigi.domain.repositories.ForecastRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    ForecastRepository provideForecastRepository(ForecastDataRepository forecastDataRepository) {
        return forecastDataRepository;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    ApiDetails provideApiDetails(ApiDetailsHandler apiDetailsHandler) {
        return apiDetailsHandler;
    }

}
