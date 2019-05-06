package com.lfq.diary.home.content.phone;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.lfq.diary.home.content.phone.local.ModelLC;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MessageBox;
import com.lfq.diary.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人操作类
 */
public class Action {
    private static Action hinstance;
    private Action(){
        databaseTools = DatabaseTools.getHinstance();
        languageTools = LanguageTools.getHinstance();
    }
    public static Action getHinstance() {
        if (hinstance==null){
            hinstance = new Action();
        }
        return hinstance;
    }
    private DatabaseTools databaseTools;
    private LanguageTools languageTools;

    /**
     * 保存联系人
     * @param dialog
     * @param contact
     * @param insert 是否是插入联系人，如果不是，那就更新
     */
    public void save(final CreateContactsDialog dialog, final ModelContact contact,boolean insert){
        DatabaseTools databaseTools = DatabaseTools.getHinstance();
        if (insert){
            databaseTools.insert(contact);
        }else {
            databaseTools.update(contact);
        }
        // 插入就发送插入广播，更新就发送更新广播
        Intent intent = new Intent(insert?ContactsView.INSERT_CONTACT:ContactsView.UPDATE_CONTACT);
        intent.putExtra(ModelContact.TAG,contact);
        LocalBroadcastManager.getInstance(dialog.getmContext()).sendBroadcast(intent);
        dialog.dismiss();
    }

    /**
     * 删除联系人
     * @param dialog 联系人对话框
     * @param contact 要删除的联系人
     */
    public void delete(final ContactDialog dialog, final ModelContact contact){
        new MessageBox(dialog.getmContext()){
            @Override
            public void leftClick(View view) {
                super.leftClick(view);
                databaseTools.delete(contact);
                Intent intent = new Intent(ContactsView.DELETE_CONTACT);
                intent.putExtra(ModelContact.TAG,contact);
                LocalBroadcastManager.getInstance(dialog.getmContext()).sendBroadcast(intent);
                dialog.dismiss();
            }
            @Override
            public String[] get() {
                return new String[]{
                        languageTools.get("你确定要删除这个联系人吗？"),
                        languageTools.get("此操作无法逆转"),
                        languageTools.get("确定删除"),
                        languageTools.get("取消"),
                };
            }
        }.show();
    }

    /**
     * 保存多个ModelLC类型的数据，然后返回这些数据的ModelContant类型
     * @return
     */
    public List<ModelContact> save(List<ModelLC> modelLCS){
        List<ModelContact> list = new ArrayList<>();
        for (ModelLC lc:modelLCS){
            ModelContact contact = new ModelContact(0,lc.getName(),lc.getPhone());
            databaseTools.insert(contact);
            list.add(contact);
        }
        return list;
    }
}
