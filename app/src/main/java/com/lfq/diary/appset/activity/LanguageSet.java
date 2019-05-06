package com.lfq.diary.appset.activity;

import android.view.View;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.AppIniTools;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import butterknife.OnClick;

public class LanguageSet extends BaseActivity {
    @Override
    protected int attrView() {
        return R.layout.activity_language;
    }

    private AppIniTools ini;
    private LanguageTools language;

    @Override
    protected void onInitData() {
        ini = AppIniTools.getHinstance();
        language = LanguageTools.getHinstance();
    }

    @Override
    protected void onInitView() {
        ColorTools color = ColorTools.getInstance();
        findViewById(R.id.al_chinese).getBackground().setTint(color.getProspectColor());
        findViewById(R.id.al_japan).getBackground().setTint(color.getProspectColor());
        findViewById(R.id.al_english).getBackground().setTint(color.getProspectColor());
    }

    @OnClick(R.id.al_chinese)
    public void onChinese(View view){
        ini.setLanguage(LanguageTools.CHINA);
        ToastTools.show(language.get("设置成功，重新打开后生效"));
    }

    @OnClick(R.id.al_japan)
    public void onJapan(View view){
        ini.setLanguage(LanguageTools.JAPAN);
        ToastTools.show(language.get("设置成功，重新打开后生效"));
    }

    @OnClick(R.id.al_english)
    public void onEnglish(View view){
        ini.setLanguage(LanguageTools.ENGLISH);
        ToastTools.show(language.get("设置成功，重新打开后生效"));
    }

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        title.set(LanguageTools.getHinstance().get("语言"), R.drawable.ic_quit, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }
}
