package com.sebduczmal.goshopping.current;


import com.sebduczmal.goshopping.model.ShoppingList;
import com.sebduczmal.goshopping.model.ShoppingListsItem;

import java.util.List;

public interface CurrentListView {
    void showCurrentShoppingLists(List<ShoppingListsItem> shoppingLists);

    void onLoadingShoppingListsStarted();

    void onLoadingShoppingListsFinished();
}
