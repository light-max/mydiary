package com.lfq.diary.home.content.phone.local;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取本地联系人的工具类
 */
public class LocalContactsReadTools {
    private String TAG = "LocalContactsReadTools";
    private static LocalContactsReadTools hinstance;
    private LocalContactsReadTools(){
    }
    public static LocalContactsReadTools getHinstance() {
        if (hinstance==null){
            hinstance = new LocalContactsReadTools();
        }
        return hinstance;
    }

    public List<ModelLC> readContacts(AppCompatActivity activity){
        Cursor cursor = activity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null,
                null);
        List<ModelLC> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()){
            String key[] = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
            };
            do {
                String name = cursor.getString(cursor.getColumnIndex(key[0]));
                String number = cursor.getString(cursor.getColumnIndex(key[1]));
                list.add(new ModelLC(name,number));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
