package com.lfq.diary.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.lfq.diary.R;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context, R.style.BaseDialog);
        mContext = context;
        view = LayoutInflater.from(context).inflate(attrView(),null);
        super.setContentView(view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private Context mContext;
    private View view;
    protected View getView(){
        return view;
    }

    public Context getmContext() {
        return mContext;
    }

    protected abstract int attrView();

    protected void initData(){}
    protected void initView(){}
}
