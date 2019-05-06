package com.lfq.diary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 日记的数据库模型
 */
public class DiarySQLiteOpenHelper extends SQLiteOpenHelper {
    public DiarySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static final String CREATE_TABLE = "create table diary(" +
            "id integer primary key autoincrement," +
            "title text," +
            "content text," +
            "time longeger," +
            "mood integer," +
            "weather integer);";
}
