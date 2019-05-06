package com.lfq.diary.home.content.notepad;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lfq.diary.db.NotepadSQLiteOpenHelper;
import com.lfq.diary.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTools {
    private static DatabaseTools hinstance;
    private DatabaseTools(){
        MyApplication application = MyApplication.getHinstance();
        SQLiteOpenHelper helper = new NotepadSQLiteOpenHelper(application.getContext(),TABNAME,null,1);
        database = helper.getWritableDatabase();
    }
    public static DatabaseTools getHinstance() {
        if (hinstance==null){
            hinstance = new DatabaseTools();
        }
        return hinstance;
    }

    private SQLiteDatabase database;
    public static final String TABNAME = "notepad";

    /**
     * 查询的子方法
     * @param cursor
     * @return
     */
    public ModelNotepad query(Cursor cursor){
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String content = cursor.getString(2);
        return new ModelNotepad(id,title,content);
    }

    /**
     * 查询所有记事本的内容
     * @return
     */
    public List<ModelNotepad> query(){
        List<ModelNotepad> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from "+TABNAME,null);
        if (cursor.moveToFirst()){
            do {
                list.add(query(cursor));
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * insert data 自动获取id
     * @param notepad
     */
    public void insert(ModelNotepad notepad){
        ContentValues values = new ContentValues();
        values.put("title",notepad.getTitle());
        values.put("content",notepad.getContent());
        long id = database.insert(TABNAME,null,values);
        notepad.setId((int)id);
    }

    /**
     * 更新数据
     * @param notepad
     */
    public void update(ModelNotepad notepad){
        ContentValues values = new ContentValues();
        values.put("title",notepad.getTitle());
        values.put("content",notepad.getContent());
        database.update(TABNAME,values,"id=?",new String[]{String.valueOf(notepad.getId())});
    }

    /**
     * 删除数据
     * @param id
     */
    public void delete(int id){
        database.delete(TABNAME,"id=?",new String[]{String.valueOf(id)});
    }

    /**
     * 批量插入数据
     * @param list
     */
    public void insert(List<ModelNotepad> list){
        for (ModelNotepad notepad:list){
            insert(notepad);
        }
    }

    /**
     * 清除所有数据
     */
    public void clear(){
        database.execSQL("delete from "+TABNAME);
    }
}
