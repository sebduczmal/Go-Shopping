package com.sebduczmal.goshopping.details;

import com.sebduczmal.goshopping.BasePresenter;
import com.sebduczmal.goshopping.model.ShoppingList;

public class ShoppingListDetailsPresenter extends BasePresenter<ShoppingListDetailsView> {

    public void loadShoppingListDetails(long shoppingListId) {
        view().showShoppingListDetails(new ShoppingList(shoppingListId, "RANDOM"));
    }

    @Override
    protected Class viewClass() {
        return ShoppingListDetailsView.class;
    }
}
