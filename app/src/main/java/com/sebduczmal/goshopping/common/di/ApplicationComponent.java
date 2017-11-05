package com.sebduczmal.goshopping.common.di;

import android.content.Context;

import com.sebduczmal.goshopping.data.DbModule;
import com.squareup.sqlbrite2.BriteDatabase;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerApplication
@Component(modules = {ApplicationComponent.ApplicationModule.class})
public interface ApplicationComponent {

    Context exposeAppContext();

    BriteDatabase exposeDatabase();

    @Module(includes = {DbModule.class})
    class ApplicationModule {
        private final Context appContext;

        public ApplicationModule(Context appContext) {
            this.appContext = appContext;
        }

        @Provides
        @PerApplication
        public Context providesAppContext() {
            return appContext;
        }
    }
}
