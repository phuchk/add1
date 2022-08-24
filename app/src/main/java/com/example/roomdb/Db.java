package com.example.roomdb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
//@Database(entities = {User.class,Price.class,...}, version = 1)
public abstract class Db extends RoomDatabase {
    private static final String DB_NAME = "user.db";
    private static Db instance;
//    static Migration from1to2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("alter table user add column year text");
//        }
//    };

    public static synchronized Db getInstance(Context c) {
        if (instance == null) {
            instance = Room.databaseBuilder(c.getApplicationContext(), Db.class, DB_NAME)
                    .allowMainThreadQueries()
                    //.addMigrations(from1to2)
                    .build();
        }
        return instance;
    }

    public abstract DAO dao();
}
