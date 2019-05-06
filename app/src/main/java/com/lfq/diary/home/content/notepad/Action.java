package com.lfq.diary.home.content.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MessageBox;

public class Action {
    private static Action hinstance;
    private Action(){
        database = DatabaseTools.getHinstance();
        language = LanguageTools.getHinstance();
    }
    public static Action getHinstance() {
        if (hinstance==null){
            hinstance = new Action();
        }
        return hinstance;
    }

    private DatabaseTools database;
    private LanguageTools language;

    /**
     * 保存记事本
     * @param notepad 要保存的内容
     * @param isInsert 是否是插入的方式？更新的方式
     */
    public void save(Context context, ModelNotepad notepad, boolean isInsert){
        Intent intent = new Intent();
        if (isInsert){
            database.insert(notepad);
            intent.setAction(NotepadView.INSERT_NOTEPAD);
        }else {
            database.update(notepad);
            intent.setAction(NotepadView.UPDATE_NOTEPAD);
        }
        intent.putExtra(ModelNotepad.TAG,notepad);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 删除记事本
     * @param context
     * @param notepad
     */
    public void delete(final NotepadDialog dialog, final Context context, final ModelNotepad notepad){
        new MessageBox(context){
            @Override
            public void leftClick(View view) {
                super.leftClick(view);
                dialog.dismiss();
                database.delete(notepad.getId());
                Intent intent = new Intent(NotepadView.DELETE_NOTEPAD);
                intent.putExtra(ModelNotepad.TAG,notepad);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public String[] get() {
                return new String[]{
                        language.get("你确定要删除这个记事本吗？"),
                        language.get("此操作无法逆转"),
                        language.get("确定删除"),
                        language.get("取消"),
                };
            }
        }.show();
    }

    /**
     * 编辑记事本
     * @param context
     * @param notepad
     */
    public void edit(Context context,ModelNotepad notepad){
        Intent intent = new Intent(context,WriteNotepad.class);
        intent.putExtra(ModelNotepad.TAG,notepad);
        context.startActivity(intent);
    }
}
