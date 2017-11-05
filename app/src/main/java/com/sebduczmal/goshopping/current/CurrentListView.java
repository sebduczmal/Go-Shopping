package com.sebduczmal.goshopping.current;


public interface CurrentListView {
    void onLoadingShoppingListsStarted();

    void onLoadingShoppingListsFinished(boolean loadedArchived);

    void toggleAddButtonVisibility(boolean archived);
}
