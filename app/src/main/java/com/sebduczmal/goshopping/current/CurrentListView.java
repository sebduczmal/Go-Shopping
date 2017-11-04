package com.sebduczmal.goshopping.current;


import com.sebduczmal.goshopping.model.ShoppingList;

import java.util.List;

public interface CurrentListView {
    void showCurrentShoppingLists(List<ShoppingList> shoppingLists);

    void onLoadingShoppingListsStarted();

    void onLoadingShoppingListsFinished();
}
