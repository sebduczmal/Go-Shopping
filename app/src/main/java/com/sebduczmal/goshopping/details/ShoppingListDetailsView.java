package com.sebduczmal.goshopping.details;

public interface ShoppingListDetailsView {
    void setDetailsTitle(String title);

    void onLoadingItemsStarted();

    void onLoadingItemsListsFinished();
}
