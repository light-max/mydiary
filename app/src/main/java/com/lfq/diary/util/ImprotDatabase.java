package com.lfq.diary.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lfq.diary.home.content.diary.ModelDiary;
import com.lfq.diary.home.content.notepad.ModelNotepad;
import com.lfq.diary.home.content.phone.ModelContact;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImprotDatabase {
    public ImprotDatabase(File file) throws RuntimeException{
        db = SQLiteDatabase.openOrCreateDatabase(file,null);
        this.file = file;
    }

    private SQLiteDatabase db;
    private File file;

    private String[] TABNAME = new String[]{
            com.lfq.diary.password.DatabaseTools.TABNAME,
            com.lfq.diary.home.content.phone.DatabaseTools.TABNAME,
            com.lfq.diary.home.content.notepad.DatabaseTools.TABNAME,
            com.lfq.diary.home.content.diary.DatabaseTools.TABNAME,
    };

    public String getPassword(){
        Cursor cursor = db.rawQuery("select * from "+TABNAME[0]+" where id=?",new String[]{"1"});
        if (cursor.moveToFirst()){
            String value = cursor.getString(1);
            return value;
        }
        return "";
    }

    public List<ModelContact> getContacts(){
        List<ModelContact> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+TABNAME[1],null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                list.add(new ModelContact(id,name,phone));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public List<ModelNotepad> getNotepads(){
        List<ModelNotepad> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+TABNAME[2],null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                list.add(new ModelNotepad(id,title,content));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public List<ModelDiary> getDiarys(){
        List<ModelDiary> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+TABNAME[3],null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                long time = cursor.getLong(3);
                int mood = cursor.getInt(4);
                int weather = cursor.getInt(5);
                list.add(new ModelDiary(id,title,content,time,mood,weather));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void close(){
        db.close();
        file.delete();
    }
}
