package com.lfq.diary.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.util.ColorTools;

import butterknife.BindView;

/**
 * 程序的标题控件，拥有两个按钮和一个标题，按钮一个在左一个在右
 * 标题的背景用程序的前景，前景用背景
 */
public class BaseTitle extends BaseLayout {
    public BaseTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int attrView() {
        return R.layout.base_title;
    }

    @BindView(R.id.title_icon_left)
    ImageView ic_left;
    @BindView(R.id.title_icon_right)
    ImageView ic_right;
    @BindView(R.id.title_content)
    TextView content;

    private OnClickListener listener_left,listener_right;
    private ColorTools color;

    @Override
    protected void onInitData() {
        color = ColorTools.getInstance();
    }

    @Override
    protected void onInitView() {
        super.bindViewClick(ic_left,ic_right);
        super.setBackgroundColor(color.getProspectColor());
    }

    /**
     * 设置标题内容
     * @param title
     * @return
     */
    public BaseTitle setContent(String title){
        content.setText(title);
        content.setTextColor(color.getBackgroundColor());
        return this;
    }

    /**
     * 设置左边的按钮图标和点击事件
     * @param resId
     * @param listener
     * @return
     */
    public BaseTitle setLeft(int resId, OnClickListener listener){
        if (resId!=0){
            ic_left.setImageResource(resId);
            ic_left.setColorFilter(color.getBackgroundColor());
            ic_left.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右边的按钮图标和点击事件
     * @param resId
     * @param listener
     * @return
     */
    public BaseTitle setRight(int resId, OnClickListener listener){
        if (resId!=0){
            ic_right.setImageResource(resId);
            ic_right.setColorFilter(color.getBackgroundColor());
            ic_right.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 所有设置的集合
     * @param content
     * @param ic_left
     * @param ic_right
     * @param listener_left
     * @param listener_right
     */
    public void set(String content,int ic_left,int ic_right,OnClickListener listener_left,OnClickListener listener_right){
        setContent(content).setLeft(ic_left,listener_left).setRight(ic_right,listener_right);
    }
}