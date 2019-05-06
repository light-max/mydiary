package com.lfq.diary.base;

import android.support.annotation.Px;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BasePagerAdapter<T extends View> extends PagerAdapter {
    private String TAG = "BasePagerAdapter";

    private List<T> list;

    public void addView(T t){
        if (list==null){
            list = new ArrayList<>();
        }
        list.add(t);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
