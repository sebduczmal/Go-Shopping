package com.sebduczmal.goshopping.details;

import com.sebduczmal.goshopping.BasePresenter;
import com.sebduczmal.goshopping.details.list.ShoppingListDetailsAdapter;
import com.sebduczmal.goshopping.model.ShoppingItem;
import com.squareup.sqlbrite2.BriteDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class ShoppingListDetailsPresenter extends BasePresenter<ShoppingListDetailsView> {

    private static final String LIST_QUERY = "SELECT * FROM "
            + ShoppingItem.TABLE
            + " WHERE "
            + ShoppingItem.LIST_ID
            + " = ? ORDER BY "
            + ShoppingItem.PURCHASED
            + " ASC";

    private final BriteDatabase db;

    public ShoppingListDetailsPresenter(BriteDatabase db) {
        this.db = db;
    }

    public void loadShoppingListDetails(ShoppingListDetailsAdapter adapter, long shoppingListId) {
        disposables.add(db.createQuery(
                ShoppingItem.TABLE, LIST_QUERY, String.valueOf(shoppingListId))
                .mapToList(ShoppingItem.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter));
    }

    @Override
    protected Class viewClass() {
        return ShoppingListDetailsView.class;
    }

    public void markItemPurchased(ShoppingItem shoppingItem) {
        boolean newValue = !shoppingItem.purchased();
        db.update(ShoppingItem.TABLE, new ShoppingItem.Builder().purchased(newValue).build(),
                shoppingItem.ID + " = ?",
                String.valueOf(shoppingItem.id()));
        Timber.d("%s changed purchase status to: %s", shoppingItem.name(), shoppingItem
                .purchased());
    }

    public void removeItem(ShoppingItem shoppingItem) {
        db.delete(ShoppingItem.TABLE, shoppingItem.ID + " = ?",
                String.valueOf(shoppingItem.id()));
        Timber.d("%s removed from database", shoppingItem.name());
    }
}
