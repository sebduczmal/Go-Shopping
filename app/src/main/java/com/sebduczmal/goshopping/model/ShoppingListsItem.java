package com.sebduczmal.goshopping.model;

import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.sebduczmal.goshopping.data.Db;

import java.util.Arrays;
import java.util.Collection;

import io.reactivex.functions.Function;

@AutoValue
public abstract class ShoppingListsItem implements Parcelable {
    private static String ALIAS_LIST = "list";
    private static String ALIAS_ITEM = "item";

    private static String LIST_ID = ALIAS_LIST + "." + ShoppingList.ID;
    private static String LIST_NAME = ALIAS_LIST + "." + ShoppingList.NAME;
    private static String ITEM_COUNT = "item_count";
    private static String ITEM_ID = ALIAS_ITEM + "." + ShoppingItem.ID;
    private static String ITEM_LIST_ID = ALIAS_ITEM + "." + ShoppingItem.LIST_ID;

    public static Collection<String> TABLES = Arrays.asList(ShoppingList.TABLE, ShoppingItem.TABLE);
    private static String QUERY = ""
            + "SELECT " + LIST_ID + ", " + LIST_NAME + ", COUNT(" + ITEM_ID + ") as " + ITEM_COUNT
            + " FROM " + ShoppingList.TABLE + " AS " + ALIAS_LIST
            + " LEFT OUTER JOIN " + ShoppingItem.TABLE + " AS " + ALIAS_ITEM + " ON " + LIST_ID +
            " = " + ITEM_LIST_ID
            + " WHERE " + ShoppingList.ARCHIVED + " = " + "%d"
            + " GROUP BY " + LIST_ID
            + " ORDER BY " + ShoppingList.DATE + " DESC";

    public abstract long id();

    public abstract String name();

    public abstract int itemCount();

    public static String getQuery(boolean archived) {
        return String.format(QUERY, archived ? Db.BOOLEAN_TRUE : Db.BOOLEAN_FALSE);
    }

    public static Function<Cursor, ShoppingListsItem> MAPPER = cursor -> {
        long id = Db.getLong(cursor, ShoppingList.ID);
        String name = Db.getString(cursor, ShoppingList.NAME);
        int itemCount = Db.getInt(cursor, ITEM_COUNT);
        return new AutoValue_ShoppingListsItem(id, name, itemCount);
    };

}
