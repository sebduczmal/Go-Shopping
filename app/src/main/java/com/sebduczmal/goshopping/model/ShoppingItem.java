package com.sebduczmal.goshopping.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.sebduczmal.goshopping.data.Db;

import io.reactivex.functions.Function;

@AutoValue
public abstract class ShoppingItem implements Parcelable {

    public static final String TABLE = "shopping_item";

    public static final String ID = "_id";
    public static final String LIST_ID = "shopping_list_id";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String UNIT = "unit";
    public static final String PURCHASED = "purchased";

    public abstract long id();

    public abstract long listId();

    public abstract String name();

    public abstract long quantity();

    public abstract String unit();

    public abstract boolean purchased();

    public static final Function<Cursor, ShoppingItem> MAPPER = cursor -> {
        long id = Db.getLong(cursor, ID);
        long listId = Db.getLong(cursor, LIST_ID);
        String name = Db.getString(cursor, NAME);
        long quantity = Db.getLong(cursor, QUANTITY);
        String unit = Db.getString(cursor, UNIT);
        boolean purchased = Db.getBoolean(cursor, PURCHASED);

        return new AutoValue_ShoppingItem(id, listId, name, quantity, unit, purchased);
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder listId(long listId) {
            values.put(LIST_ID, listId);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public Builder quantity(long quantity) {
            values.put(QUANTITY, quantity);
            return this;
        }

        public Builder unit(String unit) {
            values.put(UNIT, unit);
            return this;
        }

        public Builder purchased(boolean purchased) {
            values.put(PURCHASED, purchased ? Db.BOOLEAN_TRUE : Db.BOOLEAN_FALSE);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
