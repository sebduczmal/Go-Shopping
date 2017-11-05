package com.sebduczmal.goshopping.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sebduczmal.goshopping.model.ShoppingItem;
import com.sebduczmal.goshopping.model.ShoppingList;

public class ShoppingListDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shopping-list.db";

    private static final String CREATE_LIST = ""
            + "CREATE TABLE " + ShoppingList.TABLE + "("
            + ShoppingList.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + ShoppingList.NAME + " TEXT NOT NULL,"
            + ShoppingList.ARCHIVED + " INTEGER NOT NULL DEFAULT 0,"
            + ShoppingList.DATE + " INTEGER NOT NULL"
            + ")";
    private static final String CREATE_ITEM = ""
            + "CREATE TABLE " + ShoppingItem.TABLE + "("
            + ShoppingItem.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + ShoppingItem.LIST_ID + " INTEGER NOT NULL REFERENCES " + ShoppingList.TABLE + "(" +
            ShoppingList.ID + "),"
            + ShoppingItem.NAME + " TEXT NOT NULL,"
            + ShoppingItem.QUANTITY + " INTEGER NOT NULL,"
            + ShoppingItem.UNIT + " TEXT NOT NULL,"
            + ShoppingItem.PURCHASED + " INTEGER NOT NULL DEFAULT 0"
            + ")";
    private static final String CREATE_ITEM_LIST_ID_INDEX =
            "CREATE INDEX item_list_id ON " + ShoppingItem.TABLE + " (" + ShoppingItem.LIST_ID +
                    ")";

    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_ITEM);
        db.execSQL(CREATE_ITEM_LIST_ID_INDEX);
        generateStubData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    private void generateStubData(SQLiteDatabase db) {
        long groceryShoppingListId = db.insert(ShoppingList.TABLE, null,
                new ShoppingList.Builder()
                        .name("Spaghetti")
                        .date(System.currentTimeMillis()).build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(groceryShoppingListId)
                .name("Noodles")
                .quantity(2)
                .unit("packages").build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(groceryShoppingListId)
                .name("Tomatoes")
                .quantity(1)
                .unit("kg").build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(groceryShoppingListId)
                .name("Mozzarella")
                .quantity(200)
                .unit("grams").build());

        long cleaningShoppingListId = db.insert(ShoppingList.TABLE, null,
                new ShoppingList.Builder()
                        .name("Cleaning house")
                        .date(System.currentTimeMillis() - 10000L).build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(cleaningShoppingListId)
                .name("Soap")
                .quantity(4)
                .unit("packages").build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(cleaningShoppingListId)
                .name("Air refresher")
                .quantity(1)
                .unit("package").build());

        long generalShoppingListId = db.insert(ShoppingList.TABLE, null,
                new ShoppingList.Builder()
                        .name("General")
                        .date(System.currentTimeMillis() - 1000L).build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(generalShoppingListId)
                .name("Deodorant")
                .quantity(1)
                .unit("package").build());
        db.insert(ShoppingItem.TABLE, null, new ShoppingItem.Builder()
                .listId(generalShoppingListId)
                .name("Cigarettes")
                .quantity(2)
                .unit("packages").build());
    }
}
