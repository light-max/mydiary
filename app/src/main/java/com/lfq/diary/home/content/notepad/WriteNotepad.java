package com.lfq.diary.home.content.notepad;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.LanguageTools;

import butterknife.BindView;

/**
 * 编辑记事本的Activity
 */
public class WriteNotepad extends BaseActivity {
    private String TAG = "WriteNotepad";

    @Override
    protected int attrView() {
        return R.layout.activity_write_notepad;
    }

    @BindView(R.id.awn_title)
    EditText title;
    @BindView(R.id.awn_content)
    EditText content;

    private LanguageTools tools;
    private ModelNotepad notepad;

    @Override
    protected void onInitData() {
        tools = LanguageTools.getHinstance();
        Intent intent = getIntent();
        notepad = intent.getParcelableExtra(ModelNotepad.TAG);
    }

    @Override
    protected void onInitView() {
        title.setHint(tools.get("标题"));
        content.setHint(tools.get("内容"));
        if (notepad!=null){
            title.setText(notepad.getTitle());
            content.setText(notepad.getContent());
        }
    }

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        title.set("", R.drawable.ic_quit, R.drawable.ic_save, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Action action = Action.getHinstance();
                ModelNotepad data = new ModelNotepad(
                        notepad==null?0:notepad.getId(),
                        WriteNotepad.this.title.getText().toString(),
                        content.getText().toString());
                if (data.getTitle().length()==0){
                    data.setTitle(tools.get("无标题"));
                }
                action.save(WriteNotepad.this, data,notepad==null);
                finish();
            }
        });
    }
}
