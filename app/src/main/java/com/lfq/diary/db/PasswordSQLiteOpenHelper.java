package com.lfq.diary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PasswordSQLiteOpenHelper extends SQLiteOpenHelper {
    public PasswordSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL("insert into password(value) values(?)",new String[]{""});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 随便弄一下了
     */
    public static final String CREATE_TABLE = "create table password(" +
            "id integer primary key autoincrement," +
            "value text);";
}
