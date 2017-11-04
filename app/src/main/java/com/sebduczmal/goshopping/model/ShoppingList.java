package com.sebduczmal.goshopping.model;


import android.content.ContentValues;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ShoppingList implements Parcelable {

    public static final String TABLE = "shopping_list";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String ARCHIVED = "archived";
    public static final String DATE = "date";

    public abstract long id();

    public abstract String name();

    public abstract long date();

    public abstract boolean archived();

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public Builder archived(boolean archived) {
            values.put(ARCHIVED, archived);
            return this;
        }

        public Builder date(long date) {
            values.put(DATE, date);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
