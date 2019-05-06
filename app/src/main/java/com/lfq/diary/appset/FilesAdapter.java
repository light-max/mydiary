package com.lfq.diary.appset;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseAdapter;
import com.lfq.diary.util.ColorTools;

import java.io.File;

public class FilesAdapter extends BaseAdapter<File> {
    public FilesAdapter(Context context) {
        super(context);
        color = ColorTools.getInstance();
    }

    private ColorTools color;

    @Override
    protected int attrView() {
        return R.layout.item_files;
    }

    @Override
    protected void onGetView(View v, int position, File obj) {
        ImageView icon = VH.get(v,R.id.if_icon);
        TextView name = VH.get(v,R.id.if_name);
        if (obj.isFile()){
            icon.setImageResource(R.drawable.ic_file);
        }else {
            icon.setImageResource(R.drawable.ic_folder);
        }
        icon.setColorFilter(color.getProspectColor());
        name.setText(obj.getName());
    }
}
