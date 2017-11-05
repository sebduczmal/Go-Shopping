package com.sebduczmal.goshopping.data;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Db {
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static String getDate(Cursor cursor, String columnName) {
        long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        final Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    private Db() {
        throw new AssertionError("No instances");
    }
}
