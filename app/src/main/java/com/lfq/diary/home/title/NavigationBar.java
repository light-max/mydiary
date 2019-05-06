package com.lfq.diary.home.title;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseLayout;
import com.lfq.diary.callback.NumberCallback;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import butterknife.BindView;

/**
 * 主界面的导航栏
 */
public class NavigationBar extends BaseLayout {
    private String TAG = "Navigation";

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int attrView() {
        return R.layout.home_navigation;
    }

    private ColorTools color;
    private LanguageTools language;

    @BindView(R.id.hn_diary)
    TextView diary;
    @BindView(R.id.hn_phone)
    TextView phone;
    @BindView(R.id.hn_notepad)
    TextView notpad;
    @BindView(R.id.hn_line1)
    View line1;
    @BindView(R.id.hn_line2)
    View line2;

    @Override
    protected void onInitData() {
        color = ColorTools.getInstance();
        language = LanguageTools.getHinstance();
        diary.setText(language.get("日记"));
        phone.setText(language.get("电话"));
        notpad.setText(language.get("记事本"));
    }

    @Override
    protected void onInitView() {
        bindViewClick(diary,phone,notpad);
        super.setBackground(color.getNavigationBackground());
        line1.setBackgroundColor(color.getProspectColor());
        line2.setBackgroundColor(color.getProspectColor());
        select(0);
    }

    @Override
    protected void onClick(View v, int id) {
        int i = 0;
        switch (id){
            case R.id.hn_diary:
                i = 0;
                break;
            case R.id.hn_phone:
                i = 1;
                break;
            case R.id.hn_notepad:
                i = 2;
                break;
        }
        select(i);
        callback.onCallback(i);
    }

    /**
     * 切换已选择的按钮
     * @param postion
     */
    public void select(int postion){
        TextView bt[] = new TextView[]{diary,phone,notpad};
        Drawable bg[] = new Drawable[]{
                color.getNavigationBackgroundLeft(),
                color.getProspectDrawable(),
                color.getNavigationBackgroundRight(),
        };
        for (int i=0;i<bt.length;i++){
            bt[i].setBackground(null);
            bt[i].setTextColor(color.getProspectColor());
        }
        bt[postion].setTextColor(color.getBackgroundColor());
        bt[postion].setBackground(bg[postion]);
    }

    private NumberCallback callback;

    public void setCallback(NumberCallback callback) {
        this.callback = callback;
    }
}
