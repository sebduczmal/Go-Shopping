package com.sebduczmal.goshopping.current;


public interface CurrentListView {
    void onLoadingShoppingListsStarted();

    void onLoadingShoppingListsFinished(boolean loadedArchived);
}
