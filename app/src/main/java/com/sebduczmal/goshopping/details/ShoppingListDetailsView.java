package com.sebduczmal.goshopping.details;

public interface ShoppingListDetailsView {
    void setDetailsTitle(String title);

    void onLoadingShoppingListsStarted();

    void onLoadingShoppingListsFinished();
}
