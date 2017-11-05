package com.sebduczmal.goshopping.current;

import com.sebduczmal.goshopping.BasePresenter;
import com.sebduczmal.goshopping.current.list.CurrentListAdapter;
import com.sebduczmal.goshopping.data.Db;
import com.sebduczmal.goshopping.model.ShoppingList;
import com.sebduczmal.goshopping.model.ShoppingListsItem;
import com.squareup.sqlbrite2.BriteDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;


public class CurrentListPresenter extends BasePresenter<CurrentListView> {

    private final BriteDatabase db;

    public CurrentListPresenter(BriteDatabase db) {
        this.db = db;
    }

    public void loadShoppingLists(CurrentListAdapter adapter, boolean archived) {
        view().onLoadingShoppingListsStarted();
        int whereClauseValue = archived ? Db.BOOLEAN_TRUE : Db.BOOLEAN_FALSE;
        disposables.add(db.createQuery(ShoppingListsItem.TABLES, ShoppingListsItem
                .QUERY, String.valueOf(whereClauseValue))
                .mapToList(ShoppingListsItem.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter));
        view().toggleAddButtonVisibility(archived);
        view().onLoadingShoppingListsFinished(archived);
    }

    @Override
    protected Class viewClass() {
        return CurrentListView.class;
    }

    public void createShoppingList(String shoppingListName) {
        db.insert(ShoppingList.TABLE, new ShoppingList.Builder()
                .name(shoppingListName)
                .date(System.currentTimeMillis()).build());
        Timber.d("%s shopping list created", shoppingListName);
    }

    public void archiveShoppingList(ShoppingListsItem shoppingList) {
        db.update(ShoppingList.TABLE, new ShoppingList.Builder().archived(true).build(),
                ShoppingList.ID + " = ?", String.valueOf(shoppingList.id()));
        Timber.d("%s list archived", shoppingList.name());
    }
}
