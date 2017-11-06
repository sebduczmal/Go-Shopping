package com.sebduczmal.goshopping.current;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import timber.log.Timber;


public class CurrentListActivity extends BaseActivity implements CurrentListView,
        OnShoppingListClickListener, OnShoppingListCreateListener, OnArchiveButtonClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String BUNDLE_KEY_DISPLAY_ARCHIVED = "display_archived_key";

    @Inject protected CurrentListPresenter currentListPresenter;
    private CurrentListAdapter currentListAdapter;
    private ActivityCurrentListBinding binding;
    private boolean displayArchived;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_list);
        setupShoppingLists();
        setupViews();
        setupDrawer();
    }

    private void injectDependencies() {
        DaggerShoppingListComponent.builder()
                .shoppingListModule(new ShoppingListComponent.ShoppingListModule())
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(BUNDLE_KEY_DISPLAY_ARCHIVED, displayArchived);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        displayArchived = savedInstanceState.getBoolean(BUNDLE_KEY_DISPLAY_ARCHIVED, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentListPresenter.attachView(this);
        setActivityTitle();
        currentListPresenter.loadShoppingLists(currentListAdapter, displayArchived ? true : false);
    }

    @Override
    protected void onStop() {
        currentListPresenter.detachView();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLoadingShoppingListsStarted() {
        showProgressDialog(R.string.loading);
    }

    @Override
    public void onLoadingShoppingListsFinished(boolean loadedArchived) {
        hideProgressDialog();
        displayArchived = loadedArchived;
        binding.buttonAddList.setVisibility(loadedArchived ? View.GONE : View.VISIBLE);
        setActivityTitle();
    }

    @Override
    public void onShoppingListClick(ShoppingListsItem shoppingList) {
        startActivity(ShoppingListDetailsActivity.forShoppingListId(this, shoppingList.id(),
                shoppingList.archived()));
    }

    @Override
    public void onArchived(ShoppingListsItem shoppingList) {
        currentListPresenter.archiveShoppingList(shoppingList);
        Toast.makeText(this, String.format(getString(R.string.list_archived), shoppingList.name()
        ), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_current:
                currentListPresenter.loadShoppingLists(currentListAdapter, false);
                break;
            case R.id.nav_archived:
                currentListPresenter.loadShoppingLists(currentListAdapter, true);
                break;
            case R.id.nav_clear_all:
                currentListPresenter.deleteShoppingLists();
                break;
            default:
                Timber.d("No action handled");
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupShoppingLists() {
        currentListAdapter = new CurrentListAdapter(this);
        currentListAdapter.setOnShoppingListClickListener(this);
        currentListAdapter.setOnArchiveButtonClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        binding.recyclerViewCurrent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCurrent.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.recyclerViewCurrent.setAdapter(currentListAdapter);
    }

    private void setupViews() {
        setSupportActionBar(binding.toolbar);
        binding.setAdapter(currentListAdapter);
        binding.setPresenter(currentListPresenter);
        binding.buttonAddList.setOnClickListener(view -> {
            CreateShoppingListDialog createShoppingListDialog = new CreateShoppingListDialog();
            createShoppingListDialog.show(getSupportFragmentManager(), "create-list");
        });
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R
                .string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
    }

    private void setActivityTitle() {
        setTitle(displayArchived ? R.string.archived : R.string.current);
    }

    @Override
    public void onShoppingListCreated(String shoppingListName) {
        currentListPresenter.createShoppingList(shoppingListName);
    }
}
