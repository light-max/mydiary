package com.lfq.diary.home.content.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lfq.diary.db.DiarySQLiteOpenHelper;
import com.lfq.diary.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 日记数据的各种操作的集合
 */
public class DatabaseTools {
    private String TAG = "DatabaseTools";
    private static DatabaseTools hinstance;

    private DatabaseTools(){
        Context context = MyApplication.getHinstance().getContext();
        DiarySQLiteOpenHelper sqLiteOpenHelper = new DiarySQLiteOpenHelper(context, TABNAME,null,1);
        database = sqLiteOpenHelper.getWritableDatabase();
    }

    public static DatabaseTools getHinstance() {
        if (hinstance==null){
            hinstance = new DatabaseTools();
        }
        return hinstance;
    }

    private SQLiteDatabase database;
    public static final String TABNAME = "diary";

    /**
     * 内部用的查询方法
     * @param cursor
     * @return
     */
    private ModelDiary query(Cursor cursor){
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String content = cursor.getString(2);
        long time = cursor.getLong(3);
        int mood = cursor.getInt(4);
        int weather = cursor.getInt(5);
    //    Log.e(TAG,id+","+title+","+content+","+time+","+mood+","+weather);
        return new ModelDiary(id,title,content,time,mood,weather);
    }

    /**
     * 查询所有的日记信息
     * @return
     */
    public List<ModelDiary> query(){
        List<ModelDiary> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from "+ TABNAME,null);
        if (cursor.moveToFirst()){
            do {
                ModelDiary d = query(cursor);
                // 如果上一个日记的月份小于这个月的月份，设置上一个日记的月份为“显示”
                if (list.size()!=0){
                    ModelDiary up = list.get(0);
                    if (d.getMonth() > up.getMonth()){
                        up.setShowMonth(true);
                    }
                }
                list.add(0, d);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 根据数据库已有的日记的时间来查询这个日记的id号
     * @param time
     * @return
     */
    public int queryId(long time){
        Cursor cursor = database.rawQuery("select time=? from "+ TABNAME,new String[]{String.valueOf(time)});
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return 0;
    }

    /**
     * 查询这个时间写日记是否是已存在的日记
     * @return
     */
    public ModelDiary isExist(long time){
        Cursor cursor = database.rawQuery("select * from "+ TABNAME,null);
        // 直接查最后一个就行了
        if (cursor.moveToLast()){
            ModelDiary modelDiary = query(cursor);
            ModelDiary today = new ModelDiary(time);
            // 同年同月同日
            if (modelDiary.getYear()==today.getYear()&&
                    modelDiary.getMonth()==today.getMonth()&&
                    modelDiary.getDate()==today.getDate()){
                return modelDiary;
            }
        }
        return null;
    }

    /**
     * 向数据库插入一条数据
     * @param diary
     */
    public void insert(ModelDiary diary){
    //    Log.e(TAG,diary.getId()+","+diary.getTitle()+","+diary.getContent()+","+diary.getTime()+","+diary.getMood()+","+diary.getWeather());
        ContentValues values = new ContentValues();
        values.put("title",diary.getTitle());
        values.put("content",diary.getContent());
        values.put("time",diary.getTime());
        values.put("mood",diary.getMood());
        values.put("weather",diary.getWeather());
        database.insert(TABNAME,null,values);
    }

    /**
     * 更新一条数据
     * @param diary
     */
    public void update(ModelDiary diary){
     //   Log.e(TAG,diary.getId()+","+diary.getTitle()+","+diary.getContent()+","+diary.getTime()+","+diary.getMood()+","+diary.getWeather());
        ContentValues values = new ContentValues();
        values.put("title",diary.getTitle());
        values.put("content",diary.getContent());
        values.put("time",diary.getTime());
        values.put("mood",diary.getMood());
        values.put("weather",diary.getWeather());
        database.update(TABNAME,values,"id=?",new String[]{String.valueOf(diary.getId())});
    }

    /**
     * 删除数据，根据id来
     * @param id
     */
    public void delete(int id){
        database.delete(TABNAME,"id=?",new String[]{String.valueOf(id)});
    }

    /**
     * 批量插入数据
     * @param list
     */
    public void insert(List<ModelDiary> list){
        for (ModelDiary diary:list){
            insert(diary);
        }
    }

    /**
     * 清除所有数据
     */
    public void clear(){
        database.execSQL("delete from "+TABNAME);
    }
}
