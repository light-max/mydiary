package com.lfq.diary.util;

import android.widget.Toast;

public class ToastTools {
    private static Toast toast = null;

    public static void show(String msg){
        if (toast==null){
            MyApplication myApplication = MyApplication.getHinstance();
            toast = Toast.makeText(myApplication.getContext(),"",Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
