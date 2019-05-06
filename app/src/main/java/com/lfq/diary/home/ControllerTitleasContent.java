package com.lfq.diary.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.lfq.diary.callback.NumberCallback;
import com.lfq.diary.home.content.diary.DatabaseTools;
import com.lfq.diary.home.content.diary.ModelDiary;
import com.lfq.diary.home.content.diary.WriteDiary;
import com.lfq.diary.home.content.notepad.WriteNotepad;
import com.lfq.diary.home.content.phone.CreateContactsDialog;
import com.lfq.diary.home.title.ApplicationTitle;
import com.lfq.diary.util.ActivityTools;

/**
 * 把导航栏和内容关联起来
 */
public class ControllerTitleasContent {
    private ApplicationTitle applicationTitle;
    private ViewPager viewPager;
    private int position = 0;

    public void setApplicationTitle(ApplicationTitle applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void bind(){
        /**
         * 界面滑动的回调
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                applicationTitle.navigationSelect(i);
                position = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        /**
         * 导航按钮选择的回调
         */
        applicationTitle.setNavigationBarCall(new NumberCallback() {
            @Override
            public void onCallback(int position) {
                viewPager.setCurrentItem(position);
                ControllerTitleasContent.this.position = position;
            }
        });
    }

    /**
     * 建立工作区
     * @param context
     */
    public void establishWorkspace(Context context){
        // 编辑日记
        if (position==0){
            Intent intent = new Intent(context, WriteDiary.class);
            com.lfq.diary.home.content.diary.DatabaseTools database = DatabaseTools.getHinstance();
            ModelDiary diary = database.isExist(System.currentTimeMillis());
            if (diary==null){
                diary = new ModelDiary(System.currentTimeMillis());
            }
            intent.putExtra(ModelDiary.TAG,diary);
            context.startActivity(intent);
        }// 添加联系人
        else if (position==1){
            new CreateContactsDialog(context).show();
        }// 添加记事本
        else if (position==2){
            ActivityTools.startActivity(context,WriteNotepad.class);
        }
    }

    /**
     * 解除绑定，防止内存泄漏
     */
    public void unbind(){
        setApplicationTitle(null);
        setViewPager(null);
    }
}
