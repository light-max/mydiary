package com.lfq.diary.home.content.diary;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MessageBox;

/**
 * 把写日记界面的图标选择器和WriteDiary绑定
 * 还有数据读写操作也用这个类来操作
 */
public class Action {
    private static Action hinstance;
    private Action(){
        languageTools = LanguageTools.getHinstance();
    }
    public static Action getHinstance() {
        if (hinstance==null){
            hinstance = new Action();
        }
        return hinstance;
    }

    private LanguageTools languageTools;

    /**
     * 保存日记
     */
    public void save(WriteDiary activity){
        ModelDiary diary = activity.getDiary();
        diary.setTitle(activity.title.getText().toString());
        diary.setContent(activity.content.getText().toString());
        diary.setMood(activity.mood.getPosition());
        diary.setWeather(activity.weather.getPosition());
        DatabaseTools tools = DatabaseTools.getHinstance();
        // id都是从1开始的，如果这个id为0的话就是个新的日记
        if (diary.getId()==0){
            tools.insert(diary);
        } else {
            tools.update(diary);
        }
        // 发送一个广播，更新DiaryView的listview
        Intent intent = new Intent(DiaryView.SAVE_DIARY);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        intent.putExtra(ModelDiary.TAG,diary);
        activity.finish();
    }

    public void delete(final DiaryDialog dialog,final ModelDiary diary){
        // 如果用了本对话框的Context就会影响按钮的美观
        new MessageBox(dialog.getmContext()){
            @Override
            public void leftClick(View view) {
                super.rightClick(view);
                Intent intent = new Intent(DiaryView.DELETE_DIARY);
                intent.putExtra(ModelDiary.TAG,diary);
                LocalBroadcastManager.getInstance(dialog.getContext()).sendBroadcast(intent);
                intent.putExtra(ModelDiary.TAG,diary);
                dialog.dismiss();
            }
            @Override
            public String[] get() {
                return new String[]{
                        languageTools.get("你真的要删除这个日记吗?"),
                        languageTools.get("此操作无法逆转"),
                        languageTools.get("确定删除"),
                        languageTools.get("取消") };
            }
        }.show();
    }
}
