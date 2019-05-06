package com.lfq.diary.home.title;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.lfq.diary.R;
import com.lfq.diary.appset.AppSet;
import com.lfq.diary.base.BaseLayout;
import com.lfq.diary.callback.NumberCallback;
import com.lfq.diary.util.ColorTools;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面的标题栏
 */
public class ApplicationTitle extends BaseLayout{
    private String TAG = "ApplicationTitle";

    public ApplicationTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int attrView() {
        return R.layout.home_title;
    }

    @BindView(R.id.ht_navigation_bar)
    NavigationBar navigationBar;
    @BindView(R.id.ht_set)
    ImageView set;

    @Override
    protected void onInitView() {
        ColorTools color = ColorTools.getInstance();
        set.setColorFilter(color.getProspectColor());
    }

    @OnClick(R.id.ht_set)
    public void openSetActivity(View view){
        Intent intent = new Intent(getContext(), AppSet.class);
        getContext().startActivity(intent);
    }

    /**
     * 给导航栏控件设置回调方法
     * @param numberCallback
     */
    public void setNavigationBarCall(NumberCallback numberCallback){
        this.navigationBar.setCallback(numberCallback);
    }

    /**
     * 调用导航栏的select方法
     * @param position
     */
    public void navigationSelect(int position){
        navigationBar.select(position);
    }
}
