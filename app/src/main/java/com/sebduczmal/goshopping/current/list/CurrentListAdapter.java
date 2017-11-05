package com.sebduczmal.goshopping.current.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.databinding.ListItemShoppingListBinding;
import com.sebduczmal.goshopping.model.ShoppingListsItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class CurrentListAdapter extends RecyclerView.Adapter<CurrentListAdapter
        .ShoppingListViewHolder> implements Consumer<List<ShoppingListsItem>> {

    private final Context context;
    private List<ShoppingListsItem> shoppingLists;
    private OnShoppingListClickListener onShoppingListClickListener;
    private OnArchiveButtonClickListener onArchiveButtonClickListener;

    public CurrentListAdapter(Context context) {
        this.context = context;
        this.shoppingLists = new ArrayList<>();
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ListItemShoppingListBinding viewDataBinding = DataBindingUtil.inflate
                (LayoutInflater.from(context), R.layout.list_item_shopping_list, parent, false);
        return new ShoppingListViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        final ShoppingListsItem shoppingList = shoppingLists.get(position);
        final ListItemShoppingListBinding viewDataBinding = holder.viewDataBinding;

        viewDataBinding.setOnShoppingListClickListener(onShoppingListClickListener);
        viewDataBinding.setOnArchiveButtonClickListener(onArchiveButtonClickListener);
        viewDataBinding.setModel(shoppingList);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public void setOnShoppingListClickListener(OnShoppingListClickListener listener) {
        onShoppingListClickListener = listener;
    }

    public void setOnArchiveButtonClickListener(OnArchiveButtonClickListener listener) {
        onArchiveButtonClickListener = listener;
    }

    @Override
    public void accept(List<ShoppingListsItem> shoppingListsItems) {
        this.shoppingLists = shoppingListsItems;
        notifyDataSetChanged();
    }

    static class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        private final ListItemShoppingListBinding viewDataBinding;

        public ShoppingListViewHolder(ListItemShoppingListBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }
}
