package com.lfq.diary.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lfq.diary.db.ContactsSQLiteOpenHelper;
import com.lfq.diary.db.DiarySQLiteOpenHelper;
import com.lfq.diary.db.NotepadSQLiteOpenHelper;
import com.lfq.diary.db.PasswordSQLiteOpenHelper;
import com.lfq.diary.home.content.diary.ModelDiary;
import com.lfq.diary.home.content.notepad.ModelNotepad;
import com.lfq.diary.home.content.phone.ModelContact;

import java.io.File;
import java.util.List;

/**
 * 导出所有数据使用的工具类
 */
public class ExportDatabase {
    public ExportDatabase(File target){
        db = SQLiteDatabase.openOrCreateDatabase(target, null);
        db.execSQL(ContactsSQLiteOpenHelper.CREATE_TABLE);
        db.execSQL(DiarySQLiteOpenHelper.CREATE_TABLE);
        db.execSQL(NotepadSQLiteOpenHelper.CREATE_TABLE);
        db.execSQL(PasswordSQLiteOpenHelper.CREATE_TABLE);
    }

    private SQLiteDatabase db;
    private String [] TABNAME = new String[]{
            com.lfq.diary.home.content.diary.DatabaseTools.TABNAME,
            com.lfq.diary.home.content.phone.DatabaseTools.TABNAME,
            com.lfq.diary.home.content.notepad.DatabaseTools.TABNAME,
            com.lfq.diary.password.DatabaseTools.TABNAME,
    };

    public void insertDiary(List<ModelDiary> list){
        for (ModelDiary diary:list){
            ContentValues values = new ContentValues();
            values.put("title",diary.getTitle());
            values.put("content",diary.getContent());
            values.put("time",diary.getTime());
            values.put("mood",diary.getMood());
            values.put("weather",diary.getWeather());
            db.insert(TABNAME[0],null,values);
        }
    }

    public void insertContact(List<ModelContact> list){
        for (ModelContact contact:list){
            ContentValues values = new ContentValues();
            values.put("name",contact.getName());
            values.put("phone",contact.getPhone());
            db.insert(TABNAME[1],null,values);
        }
    }

    public void insertNotepad(List<ModelNotepad> list){
        for (ModelNotepad notepad:list){
            ContentValues values = new ContentValues();
            values.put("title",notepad.getTitle());
            values.put("content",notepad.getContent());
            db.insert(TABNAME[2],null,values);
        }
    }

    public void insertPassword(String password){
        ContentValues values = new ContentValues();
        values.put("value",password);
        db.insert(TABNAME[3],null,values);
    }

    public void close(){
        db.close();
    }
}
