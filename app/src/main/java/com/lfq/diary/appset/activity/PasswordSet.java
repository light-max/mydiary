package com.lfq.diary.appset.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.callback.CharsCallback;
import com.lfq.diary.password.DatabaseTools;
import com.lfq.diary.password.PasswordView;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置密码的Activity
 */
public class PasswordSet extends BaseActivity implements CharsCallback {
    private String TAG = "PasswordSet";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int attrView() {
        return R.layout.activity_set_password;
    }

    @BindView(R.id.password_view)
    PasswordView passwordView;

    private LanguageTools language;
    private DatabaseTools database;
    private String password;

    @Override
    protected void onInitData() {
        language = LanguageTools.getHinstance();
        database = DatabaseTools.getHinstance();
    }

    @Override
    protected void onInitView() {
        passwordView.setCharsCallback(this);
        passwordView.setMessage(language.get("请输入密码"));
        passwordView.showClearOprateView();
        passwordView.setClearRunnable(new Runnable() {
            @Override
            public void run() {
                clearPassword();
            }
        });
    }

    @Override
    public void onCallback(String args) {
        if (password==null){
            password = args;
            passwordView.setMessage(language.get("请再次输入密码"));
        }else {
            if (password.equals(args)){
                ToastTools.show(language.get("设置成功"));
                database.setPassword(args);
                finish();
            }else {
                passwordView.setMessage("两次输入的密码不一致,请重试");
                password = null;
            }
        }
    }

    public void clearPassword(){
        database.setPassword("");
        ToastTools.show(language.get("已清除"));
        finish();
    }
}
