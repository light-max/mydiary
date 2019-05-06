package com.lfq.diary.password;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lfq.diary.db.PasswordSQLiteOpenHelper;
import com.lfq.diary.util.MyApplication;

public class DatabaseTools {
    private static DatabaseTools hinstance;
    private DatabaseTools(){
        MyApplication application = MyApplication.getHinstance();
        SQLiteOpenHelper helper  = new PasswordSQLiteOpenHelper(application.getContext(),TABNAME,null,1);
        database = helper.getWritableDatabase();
    }
    public static DatabaseTools getHinstance() {
        if (hinstance==null){
            hinstance = new DatabaseTools();
        }
        return hinstance;
    }

    private SQLiteDatabase database;
    public static final String TABNAME = "password";

    /**
     * 密码是否是空的？
     * @return
     */
    public boolean isEmpty(){
        Cursor cursor = database.rawQuery("select * from "+TABNAME+" where id=?",new String[]{"1"});
        if (cursor.moveToFirst()){
            String value = cursor.getString(1);
            if (value.length()==0){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证密码是否正确
     * @param password
     * @return
     */
    public boolean Verification(String password){
    //    Cursor cursor = database.rawQuery("select id=? from "+TABNAME,new String[]{"1"}); 不知道为什么，这里这么写会异常
        Cursor cursor = database.rawQuery("select * from "+TABNAME+" where id=?",new String[]{"1"});
        if (cursor.moveToFirst()){
            String value = cursor.getString(1);
            if (value.equals(password)){
                return true;
            }
        }
        return false;
    }

    /**
     * 设置密码
     * @param s
     */
    public void setPassword(String s){
        ContentValues values = new ContentValues();
        values.put("value",s);
        database.update(TABNAME,values,"id=?",new String[]{"1"});
    }

    /**
     * 获取密码，如果没有密码就返回长度为0的字符串
     * @return
     */
    public String getPassword(){
        Cursor cursor = database.rawQuery("select * from "+TABNAME+" where id=?",new String[]{"1"});
        if (cursor.moveToFirst()){
            String value = cursor.getString(1);
            return value;
        }
        return "";
    }
}
