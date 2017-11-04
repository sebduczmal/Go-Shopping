package com.sebduczmal.goshopping.current.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class CurrentListAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        public ShoppingListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
