package com.lfq.diary.util;

import android.util.Log;

public class LanguageTools {
    private String TAG = "LanguageTools";

    private static LanguageTools hinstance = null;

    private LanguageTools(){}

    public static LanguageTools getHinstance() {
        if (hinstance==null){
            hinstance  = new LanguageTools();
        }
        return hinstance;
    }

    public static final int CHINA = 0;
    public static final int JAPAN = 1;
    public static final int ENGLISH = 2;

    /**
     * 默认中文
     */
    private int language = 0;

    /**
     * 设置语言
     * @param language
     */
    public void setLanguage(int language) {
        this.language = language;
    }

    /**
     * 获取语言
     * @param china
     * @return
     */
    public String get(String china){
        String table[][] = LanguageTable.getTable();
        for (String t[]:table){
            if (china==t[0]){
                return t[language];
            }
        }
        Log.e(TAG,"not exist "+china);
        return china;
    }

    /**
     * 获取现在是第几周
     * @param week 范围 1~7
     * @return
     */
    public String getWeek(int week){
        String[][] table = LanguageTable.getWeektable();
        return table[week-1][language];
    }

    /**
     * 获取现在是第几月
     * @param month 范围 1~12
     * @return
     */
    public String getMonth(int month){
        String[][] table = LanguageTable.getMonthtable();
        return table[month-1][language];
    }

    /**
     * 获取备份界面-》导出数据 的提示信息
     * @return
     */
    public String getBackupsTips(){
        String [] table = LanguageTable.getBackupsTips();
        return table[language];
    }
}
