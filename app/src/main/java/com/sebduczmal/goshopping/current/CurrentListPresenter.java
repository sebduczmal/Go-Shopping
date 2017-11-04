package com.sebduczmal.goshopping.current;

import com.sebduczmal.goshopping.BasePresenter;
import com.sebduczmal.goshopping.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;


public class CurrentListPresenter extends BasePresenter<CurrentListView> {

    public void loadCurrentShoppingLists() {
        view().onLoadingShoppingListsStarted();
        List<ShoppingList> shoppingLists = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ShoppingList shoppingList = new ShoppingList(i, "AAAAAAA");
            shoppingLists.add(shoppingList);
        }
        view().showCurrentShoppingLists(shoppingLists);
        view().onLoadingShoppingListsFinished();
    }

    @Override
    protected Class viewClass() {
        return CurrentListView.class;
    }
}
