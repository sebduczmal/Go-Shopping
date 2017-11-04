package com.sebduczmal.goshopping.current;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.sebduczmal.goshopping.BaseActivity;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.current.list.CurrentListAdapter;
import com.sebduczmal.goshopping.current.list.OnShoppingListClickListener;
import com.sebduczmal.goshopping.databinding.ActivityCurrentListBinding;
import com.sebduczmal.goshopping.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;


public class CurrentListActivity extends BaseActivity implements CurrentListView,
        OnShoppingListClickListener {

    private CurrentListAdapter currentListAdapter;
    private ActivityCurrentListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_list);

        setupShoppingLists();
    }

    @Override
    public void onShoppingListClick(ShoppingList shoppingList) {

    }

    private void setupShoppingLists() {
        currentListAdapter = new CurrentListAdapter(this);
        currentListAdapter.setOnShoppingListClickListener(this);
        currentListAdapter.updateShoppingLists(generateStubLists(50));
        currentListAdapter.notifyDataSetChanged();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        binding.recyclerViewCurrent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCurrent.setAdapter(currentListAdapter);
    }

    private List<ShoppingList> generateStubLists(int count) {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ShoppingList shoppingList = new ShoppingList(count, "AAAAAAA");
            shoppingLists.add(shoppingList);
        }
        return shoppingLists;
    }
}
