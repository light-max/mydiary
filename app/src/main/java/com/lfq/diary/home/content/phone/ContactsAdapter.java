package com.lfq.diary.home.content.phone;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseAdapter;
import com.lfq.diary.util.ColorTools;

public class ContactsAdapter extends BaseAdapter<ModelContact> {
    public ContactsAdapter(Context context) {
        super(context);
        color = ColorTools.getInstance();
    }

    private ColorTools color;

    @Override
    protected int attrView() {
        return R.layout.item_contact;
    }

    @Override
    protected void onGetView(View v, int position, ModelContact obj) {
        TextView name = VH.get(v,R.id.ic_name);
        TextView phone = VH.get(v,R.id.ic_phone);
        if (obj.getName().length()==0){
            name.setText("未命名");
        }else {
            name.setText(obj.getName());
        }
        phone.setText(obj.getPhone());
        name.setTextColor(color.getProspectColor());
        phone.setTextColor(color.getProspectColor());
    }
}
