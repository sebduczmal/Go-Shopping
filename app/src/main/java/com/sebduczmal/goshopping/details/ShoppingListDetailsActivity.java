package com.sebduczmal.goshopping.details;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sebduczmal.goshopping.BaseActivity;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.common.Constants;
import com.sebduczmal.goshopping.databinding.ActivityShoppingListDetailsBinding;
import com.sebduczmal.goshopping.details.di.DaggerShoppingListDetailsComponent;
import com.sebduczmal.goshopping.details.di.ShoppingListDetailsComponent;
import com.sebduczmal.goshopping.model.ShoppingList;

import javax.inject.Inject;

public class ShoppingListDetailsActivity extends BaseActivity implements ShoppingListDetailsView {

    private static final String EXTRA_SHOPPING_LIST_ID = "extra_shopping_list_id";

    @Inject protected ShoppingListDetailsPresenter shoppingListDetailsPresenter;
    private ActivityShoppingListDetailsBinding binding;

    public static Intent forShoppingListId(Activity callingActivity, long shoppingListId) {
        final Intent result = new Intent(callingActivity, ShoppingListDetailsActivity.class);
        result.putExtra(EXTRA_SHOPPING_LIST_ID, shoppingListId);
        return result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_list_details);
    }

    private void injectDependencies() {
        DaggerShoppingListDetailsComponent.builder()
                .shoppingListDetailsModule(new ShoppingListDetailsComponent
                        .ShoppingListDetailsModule())
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingListDetailsPresenter.attachView(this);
        loadShoppingListDetailsIfNeeded();
    }

    @Override
    protected void onStop() {
        shoppingListDetailsPresenter.detachView();
        super.onStop();
    }

    @Override
    public void showShoppingListDetails(ShoppingList shoppingList) {
        binding.setModel(shoppingList);
    }

    private void loadShoppingListDetailsIfNeeded() {
        final Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_SHOPPING_LIST_ID)) {
            shoppingListDetailsPresenter.loadShoppingListDetails(startingIntent.getLongExtra
                    (EXTRA_SHOPPING_LIST_ID, Constants.NO_VALUE));
        }
    }
}
