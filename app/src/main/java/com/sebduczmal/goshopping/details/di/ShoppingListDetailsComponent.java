package com.sebduczmal.goshopping.details.di;

import com.sebduczmal.goshopping.common.di.ApplicationComponent;
import com.sebduczmal.goshopping.common.di.PerActivity;
import com.sebduczmal.goshopping.details.ShoppingListDetailsActivity;
import com.sebduczmal.goshopping.details.ShoppingListDetailsPresenter;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {ShoppingListDetailsComponent.ShoppingListDetailsModule.class})
public interface ShoppingListDetailsComponent {

    void inject(ShoppingListDetailsActivity shoppingListDetailsActivity);

    @Module
    class ShoppingListDetailsModule {

        @Provides
        public ShoppingListDetailsPresenter provideShoppingListDetailsPresenter() {
            return new ShoppingListDetailsPresenter();
        }
    }
}
