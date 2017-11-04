package com.sebduczmal.goshopping.current;

import com.sebduczmal.goshopping.BasePresenter;
import com.sebduczmal.goshopping.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;


public class CurrentListPresenter extends BasePresenter<CurrentListView> {

    private final List<ShoppingList> shoppingLists;

    public CurrentListPresenter() {
        this.shoppingLists = new ArrayList<>();
    }

    public void loadCurrentShoppingLists() {
        view().onLoadingShoppingListsStarted();
        for (int i = 0; i < 10; i++) {
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

    public void createShoppingList(String shoppingListName) {
        shoppingLists.add(new ShoppingList(1, shoppingListName));
        view().showCurrentShoppingLists(shoppingLists);
    }
}
