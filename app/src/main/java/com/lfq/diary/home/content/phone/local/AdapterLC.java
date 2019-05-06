package com.lfq.diary.home.content.phone.local;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseAdapter;
import com.lfq.diary.util.ColorTools;

/**
 * 浏览本地联系人使用的适配器
 */
public class AdapterLC extends BaseAdapter<ModelLC> {
    public AdapterLC(Context context) {
        super(context);
        color = ColorTools.getInstance();
    }

    private ColorTools color;

    @Override
    protected int attrView() {
        return R.layout.item_localcontact;
    }

    @Override
    protected void onGetView(View v, int position, ModelLC obj) {
        TextView name = VH.get(v,R.id.ilc_name);
        TextView phone = VH.get(v,R.id.ilc_phone);
        View bg = VH.get(v,R.id.ilc_bg);
        name.setText(obj.getName());
        phone.setText(obj.getPhone());
        if (obj.isCheck()){
            bg.setBackgroundColor(color.getProspectColor());
        }else{
            bg.setBackgroundColor(color.getBackgroundColor());
        }
    }
}
