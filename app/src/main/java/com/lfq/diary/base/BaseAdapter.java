package com.lfq.diary.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends ArrayAdapter {
    public BaseAdapter(Context context) {
        super(context, 0);
        this.context = context;
        this.list = new ArrayList<>();
    }

    protected abstract int attrView();
    protected abstract void onGetView (View v,int position,T obj);

    private LayoutInflater inflater;
    private Context context;
    private List<T> list;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView==null){
            view = getInflater().inflate(attrView(),null);
        } else {
            view = convertView;
        }
        onGetView(view,position,list.get(position));
        return view;
    }

    public LayoutInflater getInflater() {
        if (inflater==null){
            inflater = LayoutInflater.from(context);
        }
        return inflater;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    /**
     * 用于缓存View中的子控件
     */
    protected static class VH{
        public static <T extends View> T get(View v,int id){
            SparseArray<View> vTag = (SparseArray<View>) v.getTag();
            if (vTag==null){
                vTag = new SparseArray<>();
                v.setTag(vTag);
            }
            T t = (T) vTag.get(id);
            if (t==null){
                t = v.findViewById(id);
                vTag.put(id,t);
            }
            return t;
        }
    }

    /**
     * list 的各种添加和获取
     */
    public void addData(T obj){
        if (obj!=null){
            if (list==null){
                list = new ArrayList<>();
            }
            list.add(obj);
        }
    }
    public void addData(T[] objs){
        if (objs!=null){
            List<T> temp = new ArrayList<>();
            for (T t:objs){
                temp.add(t);
            }
            if (list==null){
                list = temp;
            }else{
                list.addAll(temp);
            }
        }
    }
    public void addData(List<T> objs){
        if (objs!=null){
            if (list==null){
                list = objs;
            }else{
                list.addAll(objs);
            }
        }
    }
    public List<T> getList() {
        return list;
    }
    public T get(int position){
        return list.get(position);
    }
    public void clear(){
        list.clear();
    }
}
