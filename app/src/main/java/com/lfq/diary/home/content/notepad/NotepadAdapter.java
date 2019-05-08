package com.lfq.diary.home.content.notepad;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseAdapter;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;

public class NotepadAdapter extends BaseAdapter<ModelNotepad> {
    public NotepadAdapter(Context context) {
        super(context);
    }

    private ColorTools color = ColorTools.getInstance();
    private LanguageTools language = LanguageTools.getHinstance();

    @Override
    protected int attrView() {
        return R.layout.item_notepad;
    }

    @Override
    protected void onGetView(View v, int position, ModelNotepad obj) {
        TextView title = VH.get(v,R.id.in_title);
        if (obj.getTitle().length()==0){
            title.setText(language.get("无标题"));
        }else {
            title.setText(obj.getTitle());
        }
        title.setTextColor(color.getProspectColor());
    }
}
