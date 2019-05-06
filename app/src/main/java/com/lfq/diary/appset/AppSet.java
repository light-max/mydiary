package com.lfq.diary.appset;

import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.appset.activity.About;
import com.lfq.diary.appset.activity.Backups;
import com.lfq.diary.appset.activity.LanguageSet;
import com.lfq.diary.appset.activity.PasswordSet;
import com.lfq.diary.appset.activity.ThemeSytle;
import com.lfq.diary.util.ActivityTools;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MessageBox;
import com.lfq.diary.util.ToastTools;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 应用程序设置界面
 */
public class AppSet extends BaseActivity {
    private String TAG = "AppSet";

    @Override
    protected int attrView() {
        return R.layout.activity_set;
    }

    @BindView(R.id.as_theme)
    TextView theme;
    @BindView(R.id.as_language)
    TextView language;
    @BindView(R.id.as_password)
    TextView password;
    @BindView(R.id.as_backups)
    TextView backups;
    @BindView(R.id.as_about)
    TextView about;
    @BindView(R.id.as_clear)
    TextView clear;

    private LanguageTools ltools;

    @Override
    protected void onInitData() {
        ltools = LanguageTools.getHinstance();
    }

    @Override
    protected void onInitView() {
        theme.setText(ltools.get("主题与风格"));
        language.setText(ltools.get("语言"));
        password.setText(ltools.get("设置密码"));
        backups.setText(ltools.get("备份"));
        about.setText(ltools.get("关于"));
        clear.setText(ltools.get("清除数据"));
    }

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        title.set(ltools.get("设置"), R.drawable.ic_quit, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }

    @OnClick(R.id.as_theme)
    public void onTheme(View view){
        ActivityTools.startActivity(this, ThemeSytle.class);
    }

    @OnClick(R.id.as_language)
    public void onLanguage(View view){
        ActivityTools.startActivity(this, LanguageSet.class);
    }

    @OnClick(R.id.as_password)
    public void onPassword(View view){
        ActivityTools.startActivity(this, PasswordSet.class);
    }

    @OnClick(R.id.as_backups)
    public void onOutput(View view){
        ActivityTools.startActivity(this, Backups.class);
    }

    @OnClick(R.id.as_about)
    public void onAbout(View view){
        ActivityTools.startActivity(this, About.class);
    }

    @OnClick(R.id.as_clear)
    public void setClear(View view){
        new MessageBox(this){
            @Override
            public void leftClick(View view) {
                super.leftClick(view);
                com.lfq.diary.home.content.diary.DatabaseTools.getHinstance().clear();
                com.lfq.diary.home.content.phone.DatabaseTools.getHinstance().clear();
                com.lfq.diary.home.content.notepad.DatabaseTools.getHinstance().clear();
                ToastTools.show("删除成功");
            }
            @Override
            public String[] get() {
                return new String[]{
                        ltools.get("你确定要删除所有的数据吗?"),
                        ltools.get("此操作无法逆转"),
                        ltools.get("确定删除"),
                        ltools.get("取消")
                };
            }
        }.show();
    }
}
