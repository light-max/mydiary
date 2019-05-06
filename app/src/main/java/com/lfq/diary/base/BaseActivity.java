package com.lfq.diary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.lfq.diary.R;

import butterknife.ButterKnife;

/**
 * 本项目中所有的Activity都要继承这个类
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        onInitData();
        onInitView();
        if (title!=null){
            onCreateTitleBar(title);
        }
    }

    private LayoutInflater inflater = null;
    private View view = null;
    private BaseTitle title = null;

    public View getView() {
        return view;
    }

    public BaseTitle getTitleBar(){
        return title;
    }

    private void setContentView(){
        int resId = attrView();
        if (resId!=0){
            view = getInflater().inflate(resId,null);
            super.setContentView(view);
            title = view.findViewById(R.id.base_title);
        }
    }

    protected LayoutInflater getInflater() {
        if (inflater==null){
            inflater = LayoutInflater.from(this);
        }
        return inflater;
    }

    abstract protected int attrView();

    protected void onInitData(){}
    protected void onInitView(){}
    protected void onCreateTitleBar(BaseTitle title){}
}
