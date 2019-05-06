package com.lfq.diary;

import com.lfq.diary.home.Home;
import com.lfq.diary.password.InputPassword;
import com.lfq.diary.util.ActivityTools;
import com.lfq.diary.password.DatabaseTools;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.util.MyApplication;

public class MainActivity extends BaseActivity {
    @Override
    protected void onInitData() {
        MyApplication.getHinstance().loadIni();
        DatabaseTools password = DatabaseTools.getHinstance();
        if (password.isEmpty()){
            ActivityTools.startActivity(this, Home.class);
        }else {
            ActivityTools.startActivity(this, InputPassword.class);
        }
        finish();
    }

    @Override
    protected int attrView() {
        return 0;
    }
}
