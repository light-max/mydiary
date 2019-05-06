package com.lfq.diary.util;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

public class MyPermission {
    private static MyPermission hinstance;
    private MyPermission(){
    }
    public static MyPermission getHinstance() {
        if (hinstance==null){
            hinstance = new MyPermission();
        }
        return hinstance;
    }

    /**
     * 获取联系人权限
     */
    public final static int PERMISSIONS_REQUEST_READ_CONTACTS = 0x1000;
    public boolean getContacts(AppCompatActivity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&context.checkSelfPermission(permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //发起申请
            context.requestPermissions(new String[]{permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打电话权限
     */
    public final static int PERMISSIONS_REQUEST_CALL_PHONE = 0x1001;
    public boolean getCallPhone(AppCompatActivity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&context.checkSelfPermission(permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //发起申请
            context.requestPermissions(new String[]{permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL_PHONE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 访问外部存储
     */
    public final static int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0x1002;
    public boolean getWriteExternalStorage(AppCompatActivity activity){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && activity.checkSelfPermission(permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // 发起申请
            activity.requestPermissions(new String[]{permission.WRITE_EXTERNAL_STORAGE},PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            return true;
        }else {
           return true;
        }
    }
}
