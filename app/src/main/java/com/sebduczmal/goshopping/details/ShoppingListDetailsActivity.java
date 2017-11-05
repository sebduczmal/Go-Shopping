package com.sebduczmal.goshopping.details;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.sebduczmal.goshopping.BaseActivity;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.common.Constants;
import com.sebduczmal.goshopping.databinding.ActivityShoppingListDetailsBinding;
import com.sebduczmal.goshopping.details.di.DaggerShoppingListDetailsComponent;
import com.sebduczmal.goshopping.details.di.ShoppingListDetailsComponent;
import com.sebduczmal.goshopping.details.dialog.CreateShoppingItemDialog;
import com.sebduczmal.goshopping.details.dialog.OnShoppingItemCreateListener;
import com.sebduczmal.goshopping.details.list.OnItemClickListener;
import com.sebduczmal.goshopping.details.list.OnRemoveItemClickListener;
import com.sebduczmal.goshopping.details.list.ShoppingListDetailsAdapter;
import com.sebduczmal.goshopping.model.ShoppingItem;

import javax.inject.Inject;

public class ShoppingListDetailsActivity extends BaseActivity implements ShoppingListDetailsView,
        OnItemClickListener, OnRemoveItemClickListener, OnShoppingItemCreateListener {

    private static final String EXTRA_SHOPPING_LIST_ID = "extra_shopping_list_id";
    private static final String EXTRA_SHOPPING_LIST_ARCHIVED = "extra_shopping_list_archived";

    @Inject protected ShoppingListDetailsPresenter shoppingListDetailsPresenter;
    private ShoppingListDetailsAdapter shoppingListDetailsAdapter;
    private ActivityShoppingListDetailsBinding binding;
    private long shoppingListId;
    private boolean archived;

    public static Intent forShoppingListId(Activity callingActivity, long shoppingListId, boolean
            archived) {
        final Intent result = new Intent(callingActivity, ShoppingListDetailsActivity.class);
        result.putExtra(EXTRA_SHOPPING_LIST_ID, shoppingListId);
        result.putExtra(EXTRA_SHOPPING_LIST_ARCHIVED, archived);
        return result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_list_details);
        getParentListState();
        setupItemsList();
        setupViews();
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
    public void onLoadingItemsStarted() {
        showProgressDialog(R.string.loading_items);
    }

    @Override
    public void onLoadingItemsListsFinished() {
        hideProgressDialog();
    }

    private void setupItemsList() {
        shoppingListDetailsAdapter = new ShoppingListDetailsAdapter(this, archived);
        shoppingListDetailsAdapter.setOnItemClickListener(this);
        shoppingListDetailsAdapter.setOnRemoveItemClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        binding.recyclerViewItems.setLayoutManager(linearLayoutManager);
        binding.recyclerViewItems.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.recyclerViewItems.setAdapter(shoppingListDetailsAdapter);
    }

    private void getParentListState() {
        final Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_SHOPPING_LIST_ARCHIVED)) {
            archived = startingIntent.getBooleanExtra(EXTRA_SHOPPING_LIST_ARCHIVED, false);
        }
    }

    private void loadShoppingListDetailsIfNeeded() {
        final Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_SHOPPING_LIST_ID)) {
            shoppingListId = startingIntent.getLongExtra(EXTRA_SHOPPING_LIST_ID, Constants
                    .NO_VALUE);
            shoppingListDetailsPresenter.loadShoppingListDetails(shoppingListDetailsAdapter,
                    shoppingListId);
        }
    }

    private void setupViews() {
        binding.setArchived(archived);
        binding.buttonAddItem.setOnClickListener(view -> {
            CreateShoppingItemDialog createShoppingItemDialog = new CreateShoppingItemDialog();
            createShoppingItemDialog.show(getSupportFragmentManager(), "create-item");
        });
    }

    @Override
    public void onShoppingItemCreated(String name, long quantity, String unit) {
        shoppingListDetailsPresenter.createShoppingItem(name, quantity, unit, shoppingListId);
    }
}
