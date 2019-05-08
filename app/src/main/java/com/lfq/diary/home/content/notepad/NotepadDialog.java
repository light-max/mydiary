package com.lfq.diary.home.content.notepad;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseDialog;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;

import butterknife.BindView;
import butterknife.OnClick;

public class NotepadDialog extends BaseDialog {
    private String TAG = "NotepadDialog";

    public NotepadDialog(Context context) {
        super(context);
    }

    @Override
    protected int attrView() {
        return R.layout.dlg_notepad;
    }

    @BindView(R.id.dn_bg1)
    View bg1;
    @BindView(R.id.dn_bg2)
    View bg2;
    @BindView(R.id.dn_title)
    TextView title;
    @BindView(R.id.dn_content)
    TextView content;

    private ColorTools color;
    private ModelNotepad notepad;

    @OnClick(R.id.dn_close)
    public void onClose(View view){
        dismiss();
    }
    @OnClick(R.id.dn_delete)
    public void onDelete(View view){
        Action action = Action.getHinstance();
        action.delete(this,getmContext(),notepad);
    }
    @OnClick(R.id.dn_edit)
    public void onEdit(View view){
        dismiss();
        Action action = Action.getHinstance();
        action.edit(getmContext(),notepad);
    }

    public NotepadDialog setNotepad(ModelNotepad notepad) {
        this.notepad = notepad;
        if (notepad.getTitle().length()==0){
            title.setText(LanguageTools.getHinstance().get("无标题"));
        }else {
            title.setText(notepad.getTitle());
        }
        content.setText(notepad.getContent());
        return this;
    }

    @Override
    protected void initData() {
        color = ColorTools.getInstance();
    }

    @Override
    protected void initView() {
        bg1.setBackgroundColor(color.getProspectColor());
        bg2.setBackgroundColor(color.getProspectColor());
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }
}
