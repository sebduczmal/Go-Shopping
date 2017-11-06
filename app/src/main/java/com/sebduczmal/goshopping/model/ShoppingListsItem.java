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
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    private static String ALIAS_LIST = "list";
    private static String ALIAS_ITEM = "item";

    private static String LIST_ID = ALIAS_LIST + "." + ShoppingList.ID;
    private static String LIST_NAME = ALIAS_LIST + "." + ShoppingList.NAME;
    private static String LIST_ARCHIVED = ALIAS_LIST + "." + ShoppingList.ARCHIVED;
    private static String LIST_DATE = ALIAS_LIST + "." + ShoppingList.DATE;
    private static String ITEM_COUNT = "item_count";
    private static String ITEM_ID = ALIAS_ITEM + "." + ShoppingItem.ID;
    private static String ITEM_LIST_ID = ALIAS_ITEM + "." + ShoppingItem.LIST_ID;

    public static Collection<String> TABLES = Arrays.asList(ShoppingList.TABLE, ShoppingItem.TABLE);
    public static String QUERY = ""
            + "SELECT " + LIST_ID + ", " + LIST_NAME + ", " + LIST_ARCHIVED + ", " + LIST_DATE +
            ", COUNT(" + ITEM_ID + ") as " + ITEM_COUNT
            + " FROM " + ShoppingList.TABLE + " AS " + ALIAS_LIST
            + " LEFT OUTER JOIN " + ShoppingItem.TABLE + " AS " + ALIAS_ITEM + " ON " + LIST_ID +
            " = " + ITEM_LIST_ID
            + " WHERE " + LIST_ARCHIVED + " = ?"
            + " GROUP BY " + LIST_ID
            + " ORDER BY " + LIST_DATE + " %s";

    public abstract long id();

    public abstract String name();

    public abstract int itemCount();

    public abstract boolean archived();

    public abstract String date();

    public static String getQuery(boolean ascending) {
        return String.format(QUERY, ascending ? ASCENDING : DESCENDING);
    }

    public static Function<Cursor, ShoppingListsItem> MAPPER = cursor -> {
        long id = Db.getLong(cursor, ShoppingList.ID);
        String name = Db.getString(cursor, ShoppingList.NAME);
        int itemCount = Db.getInt(cursor, ITEM_COUNT);
        boolean archived = Db.getBoolean(cursor, ShoppingList.ARCHIVED);
        String date = Db.getDate(cursor, ShoppingList.DATE);
        return new AutoValue_ShoppingListsItem(id, name, itemCount, archived, date);
    };

}
