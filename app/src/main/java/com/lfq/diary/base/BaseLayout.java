package com.lfq.diary.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

public abstract class BaseLayout extends LinearLayout implements OnClickListener {
    public BaseLayout(Context context) {
        super(context);
        create(context);
    }

    public BaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    private void create(Context context){
        inflater = LayoutInflater.from(context);
        getInflater().inflate(attrView(),this);
        ButterKnife.bind(this);
        onInitData();
        onInitView();
    }

    @Override
    public void onClick(View view){
        onClick(view,view.getId());
    }

    /**
     * 让控件的点击事件绑定在BaseLayout的接口上
     * @param objects
     */
    protected void bindViewClick(Object... objects){
        for (Object o:objects){
            if (o instanceof Integer){
                findViewById((Integer) o).setOnClickListener(this);
            }else if(o instanceof View){
                ((View) o).setOnClickListener(this);
            }
        }
    }

    /**
     * 点击事件重写这个回调就行了
     * @param v
     * @param id
     */
    protected void onClick(View v,int id){

    }

    private LayoutInflater inflater = null;

    public LayoutInflater getInflater() {
        return inflater;
    }

    abstract protected int attrView();
    protected void onInitData(){}
    protected void onInitView(){}
}
