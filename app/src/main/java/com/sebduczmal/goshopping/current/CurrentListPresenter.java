package com.sebduczmal.goshopping.current;

import com.sebduczmal.goshopping.BasePresenter;
import com.sebduczmal.goshopping.current.list.CurrentListAdapter;
import com.sebduczmal.goshopping.model.ShoppingListsItem;
import com.squareup.sqlbrite2.BriteDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class CurrentListPresenter extends BasePresenter<CurrentListView> {

    private final BriteDatabase db;

    public CurrentListPresenter(BriteDatabase db) {
        this.db = db;
    }

    public void loadCurrentShoppingLists(CurrentListAdapter adapter) {
        view().onLoadingShoppingListsStarted();
        final Disposable disposable = db.createQuery(ShoppingListsItem.TABLES, ShoppingListsItem
                .QUERY)
                .mapToList(ShoppingListsItem.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
        disposables.add(disposable);
        view().onLoadingShoppingListsFinished();
    }

    @Override
    protected Class viewClass() {
        return CurrentListView.class;
    }

    public void createShoppingList(String shoppingListName) {
    }
}
