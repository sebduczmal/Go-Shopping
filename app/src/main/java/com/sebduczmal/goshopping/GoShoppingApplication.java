package com.sebduczmal.goshopping;

import android.app.Application;

import com.sebduczmal.goshopping.common.di.ApplicationComponent;
import com.sebduczmal.goshopping.common.di.DaggerApplicationComponent;

import timber.log.Timber;

public class GoShoppingApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupApplicationComponent();
        setupTimberLogging();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setupApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new
                ApplicationComponent.ApplicationModule(this)).build();
    }

    private void setupTimberLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
