package com.lfq.diary.home.content.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseLayout;
import com.lfq.diary.home.content.phone.local.ModelLC;

import java.util.List;

import butterknife.BindView;


public class ContactsView extends BaseLayout implements AdapterView.OnItemClickListener {
    private String TAG = "ContactsView";

    public ContactsView(Context context) {
        super(context);
        this.activity = (AppCompatActivity) context;
    }

    @Override
    protected int attrView() {
        return R.layout.view_contacts;
    }

    @BindView(R.id.vc_list)
    ListView listView;
    private ContactsAdapter adapter;
    private DatabaseTools databaseTools;
    // 拨打电话要用到的Activity
    private AppCompatActivity activity;

    @Override
    protected void onInitData() {
        databaseTools = DatabaseTools.getHinstance();
        List<ModelContact> list = databaseTools.query();
        adapter = new ContactsAdapter(getContext());
        adapter.addData(list);
        registerBroadcast();
    }

    @Override
    protected void onInitView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new ContactDialog(activity).setContact(adapter.get(i)).show();
    }

    /**
     * 自定义广播
     */
    public static final String INSERT_CONTACT = "com.lfq.diary.home.content.phone.ContactsView.INSERT";// 添加一个联系人
    public static final String INSERT_CONTACT_ALL = "com.lfq.diary.home.content.phone.ContactsView.INSERT_ALL";// 添加多个联系人
    public static final String DELETE_CONTACT = "com.lfq.diary.home.content.phone.ContactsView.DELETE";// 删除联系人
    public static final String UPDATE_CONTACT = "com.lfq.diary.home.content.phone.ContactsView.UPDATE";// 更新联系人信息
    public static final String RELOAD_CONTACTS = "com.lfq.diary.home.content.phone.ContactsView..RELOAD";// 重新加载
    public static final String CLEARALL_CONTACTS = "com.lfq.diary.home.content.phone.ContactsView.CLEARALL";// 清除所有联系人
    private class UpdateViewBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(INSERT_CONTACT)){
                onInsert(intent);
            }else if (action.equals(INSERT_CONTACT_ALL)){
                onInsertAll(intent);
            }else if (action.equals(DELETE_CONTACT)){
                onDelete(intent);
            }else if (action.equals(UPDATE_CONTACT)){
                onUpdate(intent);
            }else if (action.equals(RELOAD_CONTACTS)){
                onReLoad();
            }else if (action.equals(CLEARALL_CONTACTS)){
                onClearAll();
            }
        }
        private void onInsert(Intent intent){
            ModelContact contact = intent.getParcelableExtra(ModelContact.TAG);
            adapter.add(contact);
            adapter.notifyDataSetChanged();
            listView.setSelection(adapter.getCount());
        }
        private void onInsertAll(Intent intent){
            List<ModelLC> list = intent.getParcelableArrayListExtra(ModelLC.TAG);
            Action action = Action.getHinstance();
            List<ModelContact> obj = action.save(list);
            adapter.addData(obj);
            adapter.notifyDataSetChanged();
        }
        private void onDelete(Intent intent){
            ModelContact contact = intent.getParcelableExtra(ModelContact.TAG);
            List<ModelContact> list = adapter.getList();
            for (int i=0;i<list.size();i++){
                if (list.get(i).getId()==contact.getId()){
                    list.remove(i);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        private void onUpdate(Intent intent){
            ModelContact contact = intent.getParcelableExtra(ModelContact.TAG);
            List<ModelContact> list = adapter.getList();
            for (int i=0;i<list.size();i++){
                if (list.get(i).getId()==contact.getId()){
                    list.set(i,contact);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        private void onReLoad(){
            adapter.clear();
            List<ModelContact> list = databaseTools.query();
            adapter.addData(list);
            adapter.notifyDataSetChanged();
        }
        private void onClearAll(){
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }
    private void registerBroadcast(){
        UpdateViewBroadcastReceiver receiver = new UpdateViewBroadcastReceiver();
        IntentFilter insert = new IntentFilter(INSERT_CONTACT);
        IntentFilter insert_all = new IntentFilter(INSERT_CONTACT_ALL);
        IntentFilter delete = new IntentFilter(DELETE_CONTACT);
        IntentFilter update = new IntentFilter(UPDATE_CONTACT);
        IntentFilter reload = new IntentFilter(RELOAD_CONTACTS);
        IntentFilter clearaall = new IntentFilter(CLEARALL_CONTACTS);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.registerReceiver(receiver,insert);
        manager.registerReceiver(receiver,insert_all);
        manager.registerReceiver(receiver,delete);
        manager.registerReceiver(receiver,update);
        manager.registerReceiver(receiver,reload);
        manager.registerReceiver(receiver,clearaall);
    }
}
