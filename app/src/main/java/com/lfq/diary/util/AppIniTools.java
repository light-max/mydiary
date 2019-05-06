package com.lfq.diary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 应用程序配置工具类
 */
public class AppIniTools {
    private String TAG = "AppIniTools";
    private AppIniTools(){
    }
    private static AppIniTools hinstance;
    public static AppIniTools getHinstance() {
        if (hinstance==null){
            hinstance = new AppIniTools();
        }
        return hinstance;
    }

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    /**
     * 加载本类
     * @param context
     */
    public void load(Context context){
        preferences = context.getSharedPreferences("appini",0);
        editor = preferences.edit();

        ColorTools colorTools = ColorTools.getInstance();
        colorTools.loadColors(context);
        colorTools.setTheme(getTheme());

        LanguageTools languageTools = LanguageTools.getHinstance();
        languageTools.setLanguage(getLanguage());
    }

    /**
     * 获取主题
     * @return
     */
    public int getTheme(){
        int value = preferences.getInt("theme",0);
        return value;
    }

    /**
     * 设置主题
     * @param theme
     */
    public void setTheme(int theme){
        editor.putInt("theme",theme);
        editor.commit();
    }

    /**
     * 获取语言
     * @return
     */
    public int getLanguage(){
        int value = preferences.getInt("language",0);
        return value;
    }

    /**
     * 设置语言
     * @param language
     */
    public void setLanguage(int language){
        editor.putInt("language",language);
        editor.commit();
    }
}
