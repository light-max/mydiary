package com.lfq.diary.home.content.notepad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseLayout;

import java.util.List;

import butterknife.BindView;

public class NotepadView extends BaseLayout implements AdapterView.OnItemClickListener {
    public NotepadView(Context context) {
        super(context);
        registerBroadcastReceiver();
    }

    @Override
    protected int attrView() {
        return R.layout.view_notepad;
    }

    @BindView(R.id.vn_list)
    ListView listView;

    private NotepadAdapter adapter;

    @Override
    protected void onInitData() {
        DatabaseTools databaseTools = DatabaseTools.getHinstance();
        List<ModelNotepad> list = databaseTools.query();
        adapter = new NotepadAdapter(getContext());
        adapter.addData(list);
    }

    @Override
    protected void onInitView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new NotepadDialog(getContext()).setNotepad(adapter.get(i)).show();
    }

    /**
     * 接收更新视图的广播
     */
    public static final String UPDATE_NOTEPAD = "com.lfq.diary.home.content.notepad.NotepadView.UPDATE";// 更新记事本
    public static final String INSERT_NOTEPAD = "com.lfq.diary.home.content.notepad.NotepadView.INSERT";// 添加记事本
    public static final String DELETE_NOTEPAD = "com.lfq.diary.home.content.notepad.NotepadView.DELETE";// 删除记事本
    public static final String RELOAD_NOTEPAD = "com.lfq.diary.home.content.notepad.NotepadView.RELOAD";// 重新加载
    public static final String CLEARALL_NOTEPAD = "com.lfq.diary.home.content.notepad.NotepadView.CLEARALL";// 清除所有
    private class UpdateViewBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            ModelNotepad notepad = intent.getParcelableExtra(ModelNotepad.TAG);
            if (action.equals(UPDATE_NOTEPAD)){
                onUpdate(notepad);
            } else if (action.equals(INSERT_NOTEPAD)) {
                onInsert(notepad);
            } else if (action.equals(DELETE_NOTEPAD)) {
                onDelete(notepad);
            } else if (action.equals(RELOAD_NOTEPAD)) {
                onReLoad();
            } else if (action.equals(CLEARALL_NOTEPAD)) {
                onClearAll();
            }
        }
        private void onUpdate(ModelNotepad notepad){
            List<ModelNotepad> list = adapter.getList();
            for (ModelNotepad model:list){
                if (model.getId()==notepad.getId()){
                    model.setTitle(notepad.getTitle());
                    model.setContent(notepad.getContent());
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        private void onInsert(ModelNotepad notepad){
            adapter.addData(notepad);
            adapter.notifyDataSetChanged();
            listView.setSelection(adapter.getCount());
        }
        private void onDelete(ModelNotepad notepad){
            List<ModelNotepad> list = adapter.getList();
            for (ModelNotepad model:list){
                if (model.getId()==notepad.getId()){
                    list.remove(model);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        private void onReLoad(){
            DatabaseTools databaseTools = DatabaseTools.getHinstance();
            adapter.clear();
            List<ModelNotepad> list = databaseTools.query();
            adapter.addData(list);
            adapter.notifyDataSetChanged();
        }
        private void onClearAll(){
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }
    private void registerBroadcastReceiver(){
        UpdateViewBroadcastReceiver broadcastReceiver = new UpdateViewBroadcastReceiver();
        IntentFilter update = new IntentFilter(UPDATE_NOTEPAD);
        IntentFilter insert = new IntentFilter(INSERT_NOTEPAD);
        IntentFilter delete = new IntentFilter(DELETE_NOTEPAD);
        IntentFilter reload = new IntentFilter(RELOAD_NOTEPAD);
        IntentFilter clearall = new IntentFilter(CLEARALL_NOTEPAD);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.registerReceiver(broadcastReceiver, update);
        manager.registerReceiver(broadcastReceiver, insert);
        manager.registerReceiver(broadcastReceiver, delete);
        manager.registerReceiver(broadcastReceiver, reload);
        manager.registerReceiver(broadcastReceiver, clearall);
    }
}
