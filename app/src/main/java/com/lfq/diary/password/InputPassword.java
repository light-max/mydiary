package com.lfq.diary.password;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.callback.CharsCallback;
import com.lfq.diary.home.Home;
import com.lfq.diary.util.ActivityTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import butterknife.BindView;

public class InputPassword extends BaseActivity implements CharsCallback {
    private String TAG = "InputPassword";

    @Override
    protected int attrView() {
        return R.layout.activity_set_password;
    }

    @BindView(R.id.password_view)
    PasswordView passwordView;

    private LanguageTools language;

    @Override
    protected void onInitData() {
        language = LanguageTools.getHinstance();
    }

    @Override
    protected void onInitView() {
        passwordView.setCharsCallback(this);
        passwordView.setMessage(language.get("请输入密码"));
    }

    @Override
    public void onCallback(String args) {
        DatabaseTools databaseTools = DatabaseTools.getHinstance();
        if (databaseTools.Verification(args)){
            finish();
            ActivityTools.startActivity(this, Home.class);
        }else {
            passwordView.setMessage(language.get("密码错误"));
        }
    }
}
