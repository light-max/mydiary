package com.lfq.diary.home.content.diary;

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

/**
 * 日记列表界面
 */
public class DiaryView extends BaseLayout implements AdapterView.OnItemClickListener {
    private String TAG = "DiaryView";

    public DiaryView(Context context) {
        super(context);
    }

    @Override
    protected int attrView() {
        return R.layout.view_diary;
    }

    @BindView(R.id.vd_list)
    ListView listView;

    private DiaryAdapter adapter;
    private DatabaseTools databaseTools;

    @Override
    protected void onInitData() {
        databaseTools = DatabaseTools.getHinstance();
        List<ModelDiary> datas = databaseTools.query();
        adapter = new DiaryAdapter(getContext());
        adapter.addData(datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerBroadcast();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ModelDiary diary = adapter.get(i);
        DiaryDialog dialog = new DiaryDialog(getContext());
        dialog.setDiary(diary);
        dialog.show();
    }

    /**
     * 自定义广播接受器
     */
    public static final String SAVE_DIARY = "com.lfq.diary.home.content.diary.DiaryView.SAVE_DIARY";// 保存日记
    public static final String DELETE_DIARY = "com.lfq.diary.home.content.diary.DiaryView.DELETE_DIARY";// 删除日记
    public static final String RELOAD_DIARY = "com.lfq.diary.home.content.diary.DiaryView.RELOAD_DIARY";// 重新加载
    public static final String CLEARALL_DIARY = "com.lfq.diary.home.content.diary.DiaryView.CLEARALL_DIARY";// 清除所有
    private class UpdateViewBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SAVE_DIARY)){
                onSave(intent);
            } else if (action.equals(DELETE_DIARY)) {
                onDelete(intent);
            } else if (action.equals(RELOAD_DIARY)) {
                onReLoad();
            } else if (action.equals(CLEARALL_DIARY)) {
                onClearAll();
            }
        }
        private void onSave(Intent intent){
            ModelDiary diary = intent.getParcelableExtra(ModelDiary.TAG);
            List<ModelDiary> list = adapter.getList();
            if (diary.getId()==0){
                // 获取新的id
                int id = DatabaseTools.getHinstance().queryId(diary.getTime());
                diary.setId(id);
                list.add(0, diary);
            } else {
                ModelDiary q;
                for (int i=0;i<list.size();i++){
                    q = list.get(i);
                    if (q.getId()==diary.getId()){
                        // 比如q是第一个 把q删除 把新的diary插入到第一个
                        list.remove(q);
                        list.add(i,diary);
                        break;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
        private void onDelete(Intent intent){
            ModelDiary diary = intent.getParcelableExtra(ModelDiary.TAG);
            List<ModelDiary> list = adapter.getList();
            for (int i=0;i<list.size();i++){
                if (list.get(i).getId()==diary.getId()){
                    list.remove(i);
                    databaseTools.delete(diary.getId());
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        private void onReLoad(){
            adapter.clear();
            List<ModelDiary> list = databaseTools.query();
            adapter.addData(list);
            adapter.notifyDataSetChanged();
        }
        private void onClearAll(){
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }
    private void registerBroadcast(){
        BroadcastReceiver mReceiver = new UpdateViewBroadcastReceiver();
        IntentFilter delete = new IntentFilter(DELETE_DIARY);
        IntentFilter save = new IntentFilter(SAVE_DIARY);
        IntentFilter reload = new IntentFilter(RELOAD_DIARY);
        IntentFilter clearall = new IntentFilter(CLEARALL_DIARY);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.registerReceiver(mReceiver,delete);
        manager.registerReceiver(mReceiver,save);
        manager.registerReceiver(mReceiver,reload);
        manager.registerReceiver(mReceiver,clearall);
    }
}
