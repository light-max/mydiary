package com.lfq.diary.home.content.phone;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lfq.diary.db.ContactsSQLiteOpenHelper;
import com.lfq.diary.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTools {
    private static DatabaseTools hinstance;
    private DatabaseTools(){
        MyApplication application = MyApplication.getHinstance();
        ContactsSQLiteOpenHelper helper = new ContactsSQLiteOpenHelper(application.getContext(),TABNAME,null,1);
        database = helper.getWritableDatabase();
    }
    public static DatabaseTools getHinstance() {
        if (hinstance==null){
            hinstance = new DatabaseTools();
        }
        return hinstance;
    }

    public static final String TABNAME = "contacts";
    private SQLiteDatabase database;

    /**
     * 查询联系人，子方法
     * @param cursor
     * @return
     */
    public ModelContact query(Cursor cursor){
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String phone = cursor.getString(2);
    //    Log.e(TABNAME,id+","+name+","+phone);
        return new ModelContact(id,name,phone);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<ModelContact> query(){
        List<ModelContact> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from "+TABNAME,null);
        if (cursor.moveToFirst()){
            do {
                list.add(query(cursor));
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 向数据库插入一个联系人，立即获得id
     * @param contact
     */
    public void insert(ModelContact contact){
        ContentValues values = new ContentValues();
        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        // 2019年4月26日get到的新知识，原来insert可以返回id
        long id = database.insert(TABNAME,null,values);
        contact.setId((int) id);
    }

    /**
     * 修改联系人信息
     * @param contact
     */
    public void update(ModelContact contact){
        ContentValues values = new ContentValues();
        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        database.update(TABNAME,values,"id=?",new String[]{String.valueOf(contact.getId())});
    }

    /**
     * 删除联系人，根据id来删除
     * @param contact
     */
    public void delete(ModelContact contact){
        database.delete(TABNAME,"id=?",new String[]{String.valueOf(contact.getId())});
    }

    /**
     * 批量插入数据
     * @param list
     */
    public void insert(List<ModelContact> list){
        for (ModelContact contact:list){
            insert(contact);
        }
    }

    /**
     * 清除所有数据
     */
    public void clear(){
        database.execSQL("delete from "+TABNAME);
    }
}
