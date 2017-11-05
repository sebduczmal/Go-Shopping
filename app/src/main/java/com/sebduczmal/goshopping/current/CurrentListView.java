package com.sebduczmal.goshopping.current;


public interface CurrentListView {
    void onLoadingShoppingListsStarted();

    void onLoadingShoppingListsFinished();

    void toggleAddButtonVisibility(boolean archived);
}
