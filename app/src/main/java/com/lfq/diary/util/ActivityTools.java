package com.lfq.diary.util;

import android.content.Context;
import android.content.Intent;

public class ActivityTools {
    public static void startActivity(Context context,Class<?> clasz){
        Intent intent = new Intent(context,clasz);
        context.startActivity(intent);
    }
}
