package com.sebduczmal.goshopping.details;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.sebduczmal.goshopping.BaseActivity;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.common.Constants;
import com.sebduczmal.goshopping.databinding.ActivityShoppingListDetailsBinding;
import com.sebduczmal.goshopping.details.di.DaggerShoppingListDetailsComponent;
import com.sebduczmal.goshopping.details.di.ShoppingListDetailsComponent;
import com.sebduczmal.goshopping.details.list.OnItemClickListener;
import com.sebduczmal.goshopping.details.list.OnRemoveItemClickListener;
import com.sebduczmal.goshopping.details.list.ShoppingListDetailsAdapter;
import com.sebduczmal.goshopping.model.ShoppingItem;

import javax.inject.Inject;

public class ShoppingListDetailsActivity extends BaseActivity implements ShoppingListDetailsView,
        OnItemClickListener, OnRemoveItemClickListener {

    private static final String EXTRA_SHOPPING_LIST_ID = "extra_shopping_list_id";

    @Inject protected ShoppingListDetailsPresenter shoppingListDetailsPresenter;
    private ShoppingListDetailsAdapter shoppingListDetailsAdapter;
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
        setupItemsList();
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
    public void onShoppingItemClick(ShoppingItem shoppingItem) {
        shoppingListDetailsPresenter.markItemPurchased(shoppingItem);
    }

    @Override
    public void onRemoveItemClick(ShoppingItem shoppingItem) {
        shoppingListDetailsPresenter.removeItem(shoppingItem);
    }

    @Override
    public void setDetailsTitle(String title) {
        setTitle(title);
    }

    @Override
    public void onLoadingShoppingListsStarted() {
        showProgressDialog(R.string.loading_items);
    }

    @Override
    public void onLoadingShoppingListsFinished() {
        hideProgressDialog();
    }

    private void setupItemsList() {
        shoppingListDetailsAdapter = new ShoppingListDetailsAdapter(this);
        shoppingListDetailsAdapter.setOnItemClickListener(this);
        shoppingListDetailsAdapter.setOnRemoveItemClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        binding.recyclerViewItems.setLayoutManager(linearLayoutManager);
        binding.recyclerViewItems.setAdapter(shoppingListDetailsAdapter);
    }

    private void loadShoppingListDetailsIfNeeded() {
        final Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_SHOPPING_LIST_ID)) {
            shoppingListDetailsPresenter.loadShoppingListDetails(shoppingListDetailsAdapter,
                    startingIntent.getLongExtra(EXTRA_SHOPPING_LIST_ID, Constants.NO_VALUE));
        }
    }
}
