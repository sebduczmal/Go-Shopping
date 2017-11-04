package com.sebduczmal.goshopping.current.di;

import com.sebduczmal.goshopping.common.di.ApplicationComponent;
import com.sebduczmal.goshopping.common.di.PerActivity;
import com.sebduczmal.goshopping.current.CurrentListActivity;
import com.sebduczmal.goshopping.current.CurrentListPresenter;
import com.squareup.sqlbrite2.BriteDatabase;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {ShoppingListComponent.ShoppingListModule.class})
public interface ShoppingListComponent {

    void inject(CurrentListActivity currentListActivity);

    @Module
    class ShoppingListModule {

        @Provides
        public CurrentListPresenter provideCurrentListPresenter(BriteDatabase db) {
            return new CurrentListPresenter(db);
        }
    }
}
