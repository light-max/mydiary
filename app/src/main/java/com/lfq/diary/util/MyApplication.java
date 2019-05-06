package com.lfq.diary.util;

import android.app.Application;
import android.content.Context;

/**
 * 应用程序启动类
 */
public class MyApplication extends Application {
    private String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        hinstance = this;
        context = getApplicationContext();
    }

    private Context context;
    private static MyApplication hinstance;

    public static MyApplication getHinstance() {
        return hinstance;
    }

    public Context getContext() {
        return context;
    }

    public void loadIni(){
        AppIniTools appIniTools = AppIniTools.getHinstance();
        appIniTools.load(context);
    }
}
