package com.lfq.diary.appset.activity;

import android.view.View;
import android.widget.Button;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.AppIniTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import butterknife.BindView;
import butterknife.OnClick;

public class ThemeSytle extends BaseActivity {
    @Override
    protected int attrView() {
        return R.layout.activity_themesytle;
    }

    @BindView(R.id.at_blue)
    Button blue;
    @BindView(R.id.at_rad)
    Button rad;

    @OnClick(R.id.at_blue)
    public void onBlue(View view){
        AppIniTools.getHinstance().setTheme(0);
        ToastTools.show(tools.get("设置成功，重新打开后生效"));
    }

    @OnClick(R.id.at_rad)
    public void onRad(View view){
        AppIniTools.getHinstance().setTheme(1);
        ToastTools.show(tools.get("设置成功，重新打开后生效"));
    }

    @Override
    protected void onInitData() {
        tools = LanguageTools.getHinstance();
    }

    @Override
    protected void onInitView() {
        blue.setText(tools.get("淡蓝"));
        rad.setText(tools.get("粉红"));
    }

    private LanguageTools tools;

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        title.set(tools.get("主题与风格"), R.drawable.ic_quit, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }
}
