package com.lfq.diary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    public BaseFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = getInflater().inflate(attrView(),null);
        ButterKnife.bind(view);
        onInitData();
        onInitView();
        return view;
    }

    private Context context;
    private LayoutInflater inflater = null;
    private View view = null;

    public LayoutInflater getInflater() {
        if (inflater==null){
            inflater = LayoutInflater.from(context);
        }
        return inflater;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    abstract protected int attrView();
    protected void onInitData(){}
    protected void onInitView(){}
}
