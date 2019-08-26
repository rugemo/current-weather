package com.rumodigi.data.api;

import com.rumodigi.data.datasource.DarkSkyApiDataSource;
import com.rumodigi.data.datasource.DarkSkyDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    DarkSkyService provideDarkSkyService(Retrofit retrofit) {
        return retrofit.create(DarkSkyService.class);
    }

    @Provides
    @Singleton
    DarkSkyDataSource provideDarkSkyDataSource(DarkSkyService darkSkyService, ApiDetails apiDetails){
        return new DarkSkyApiDataSource(darkSkyService, apiDetails);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, ApiDetails apiDetails) {
        return new Retrofit.Builder()
                .baseUrl(apiDetails.getHost())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
