package com.sebduczmal.goshopping.current;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sebduczmal.goshopping.BaseActivity;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.current.di.DaggerShoppingListComponent;
import com.sebduczmal.goshopping.current.di.ShoppingListComponent;
import com.sebduczmal.goshopping.current.dialog.CreateShoppingListDialog;
import com.sebduczmal.goshopping.current.dialog.OnShoppingListCreateListener;
import com.sebduczmal.goshopping.current.list.CurrentListAdapter;
import com.sebduczmal.goshopping.current.list.OnArchiveButtonClickListener;
import com.sebduczmal.goshopping.current.list.OnShoppingListClickListener;
import com.sebduczmal.goshopping.databinding.ActivityCurrentListBinding;
import com.sebduczmal.goshopping.details.ShoppingListDetailsActivity;
import com.sebduczmal.goshopping.model.ShoppingListsItem;

import javax.inject.Inject;


public class CurrentListActivity extends BaseActivity implements CurrentListView,
        OnShoppingListClickListener, OnShoppingListCreateListener, OnArchiveButtonClickListener {

    @Inject protected CurrentListPresenter currentListPresenter;
    private CurrentListAdapter currentListAdapter;
    private ActivityCurrentListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_list);
        setupShoppingLists();
        setupViews();
    }

    private void injectDependencies() {
        DaggerShoppingListComponent.builder()
                .shoppingListModule(new ShoppingListComponent.ShoppingListModule())
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentListPresenter.attachView(this);
        currentListPresenter.loadShoppingLists(currentListAdapter, false);
    }

    @Override
    protected void onStop() {
        currentListPresenter.detachView();
        super.onStop();
    }

    @Override
    public void onLoadingShoppingListsStarted() {
        showProgressDialog(R.string.loading);
    }

    @Override
    public void onLoadingShoppingListsFinished() {
        hideProgressDialog();
    }

    @Override
    public void toggleAddButtonVisibility(boolean archived) {
        binding.buttonAddList.setVisibility(archived ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onShoppingListClick(ShoppingListsItem shoppingList) {
        startActivity(ShoppingListDetailsActivity.forShoppingListId(this, shoppingList.id(),
                shoppingList.archived()));
    }

    @Override
    public void onArchived(ShoppingListsItem shoppingList) {
        currentListPresenter.archiveShoppingList(shoppingList);
    }

    private void setupShoppingLists() {
        currentListAdapter = new CurrentListAdapter(this);
        currentListAdapter.setOnShoppingListClickListener(this);
        currentListAdapter.setOnArchiveButtonClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        binding.recyclerViewCurrent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCurrent.setAdapter(currentListAdapter);
    }

    private void setupViews() {
        binding.buttonAddList.setOnClickListener(view -> {
            CreateShoppingListDialog createShoppingListDialog = new CreateShoppingListDialog();
            createShoppingListDialog.show(getSupportFragmentManager(), "create-list");
        });
        binding.buttonShowArchived.setOnClickListener(view -> {
            currentListPresenter.loadShoppingLists(currentListAdapter, true);
        });
        binding.buttonShowCurrent.setOnClickListener(view -> {
            currentListPresenter.loadShoppingLists(currentListAdapter, false);
        });
    }

    @Override
    public void onShoppingListCreated(String shoppingListName) {
        currentListPresenter.createShoppingList(shoppingListName);
    }
}
