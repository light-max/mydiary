package com.lfq.diary.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.lfq.diary.R;

public class ColorTools {
    private String TAG = "ColorTools";
    private static ColorTools hinstance;
    private static Resources resources;
    private SparseArray<Drawable> drawableSparseArray = new SparseArray<>();
    private int color;
    private int[] prospect_color;
    private int[] background_color;

    private ColorTools(){}

    public static ColorTools getInstance() {
        if (hinstance==null){
            hinstance = new ColorTools();
        }
        return hinstance;
    }

    /**
     * 设置颜色
     * @param i
     */
    public void setTheme(int i){
        color = i;
    }

    /**
     * 加载所有颜色
     * @param context
     */
    public void loadColors(Context context){
        resources = context.getResources();
        prospect_color = new int[]{
                resources.getColor(R.color.blue),
                resources.getColor(R.color.rad),
        };
        background_color = new int[]{
                resources.getColor(R.color.white),
                resources.getColor(R.color.white),
        };
    }

    /**
     * 前景颜色
     */
    public int getProspectColor(){
        return prospect_color[color];
    }
    public Drawable getProspectDrawable(){
        ColorDrawable drawable = new ColorDrawable();
        drawable.setColor(getProspectColor());
        return drawable;
    }

    /**
     * 背景颜色
     */
    public int getBackgroundColor(){
        return background_color[color];
    }

    /**
     * 主题图片
     * @return
     */
    public Drawable getAppThemeDrawable(){
        return getDrawable(new int[]{
                R.mipmap.ic_home_bg_blue,
                R.mipmap.ic_home_bg_rad,
        }[color]);
    }

    public Drawable getDrawable(int resId){
        Drawable drawable = drawableSparseArray.get(resId);
        if (drawable==null){
            drawable = resources.getDrawable(resId);
            drawableSparseArray.put(resId,drawable);
        }
        return drawable;
    }

    /**
     * 获取导航栏的背景
     * @return
     */
    public Drawable getNavigationBackground(){
        return getDrawable(new int[]{
                R.drawable.ic_navigation_bg_blue,
                R.drawable.ic_navigation_bg_rad,
        }[color]);
    }

    /**
     * 获取导航栏左边按钮的背景
     * @return
     */
    public Drawable getNavigationBackgroundLeft(){
        return getDrawable(new int[]{
                R.drawable.ic_navigation_left_blue,
                R.drawable.ic_navigation_left_rad,
        }[color]);
    }

    /**
     * 获取导航栏右边按钮的背景
     * @return
     */
    public Drawable getNavigationBackgroundRight(){
        return getDrawable(new int[]{
                R.drawable.ic_navigation_right_blue,
                R.drawable.ic_navigation_right_rad,
        }[color]);
    }
}
