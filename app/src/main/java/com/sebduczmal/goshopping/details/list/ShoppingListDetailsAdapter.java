package com.sebduczmal.goshopping.details.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.databinding.ListItemShoppingItemBinding;
import com.sebduczmal.goshopping.model.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ShoppingListDetailsAdapter extends RecyclerView.Adapter<ShoppingListDetailsAdapter
        .ShoppingListDetailsViewHolder> implements Consumer<List<ShoppingItem>> {

    private final Context context;
    private List<ShoppingItem> shoppingItems;
    private OnItemClickListener onItemClickListener;

    public ShoppingListDetailsAdapter(Context context) {
        this.context = context;
        shoppingItems = new ArrayList<>();
    }

    @Override
    public ShoppingListDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ListItemShoppingItemBinding viewDataBinding = DataBindingUtil.inflate
                (LayoutInflater.from(context), R.layout.list_item_shopping_item, parent, false);
        return new ShoppingListDetailsViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(ShoppingListDetailsViewHolder holder, int position) {
        final ShoppingItem shoppingItem = shoppingItems.get(position);
        final ListItemShoppingItemBinding viewDataBinding = holder.viewDataBinding;

        viewDataBinding.setOnShoppingItemClickListener(onItemClickListener);
        viewDataBinding.setModel(shoppingItem);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    @Override
    public void accept(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class ShoppingListDetailsViewHolder extends RecyclerView.ViewHolder {

        private final ListItemShoppingItemBinding viewDataBinding;

        public ShoppingListDetailsViewHolder(ListItemShoppingItemBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }
}
