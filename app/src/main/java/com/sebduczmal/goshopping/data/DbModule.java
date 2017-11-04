package com.sebduczmal.goshopping.data;

import android.content.Context;

import com.sebduczmal.goshopping.common.di.PerApplication;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class DbModule {

    @Provides
    @PerApplication
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().logger(message -> Timber.tag("Database").v(message)).build();
    }

    @Provides
    @PerApplication
    BriteDatabase provideDatabase(SqlBrite sqlBrite, Context context) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(new ShoppingListDbHelper(context),
                Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }
}
