package com.lfq.diary.home.content.phone.local;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.home.content.phone.ContactsView;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MyPermission;
import com.lfq.diary.util.ToastTools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 浏览本地联系人
 */
public class LocalContacts extends BaseActivity implements AdapterView.OnItemClickListener {
    private String TAG = "LocalContacts";

    @Override
    protected int attrView() {
        return R.layout.activity_local_contacts;
    }

    @BindView(R.id.alc_list)
    ListView listView;

    private LanguageTools language;
    private ColorTools color;
    private AdapterLC adapter;

    @Override
    protected void onInitData() {
        language = LanguageTools.getHinstance();
        color = ColorTools.getInstance();
        adapter = new AdapterLC(this);
        MyPermission permission = MyPermission.getHinstance();
        if (permission.getContacts(this)){
            getLocalContacts();
        }
    }

    @Override
    protected void onInitView() {
        getView().setBackground(color.getAppThemeDrawable());
    }

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        title.set(language.get("导入本地联系人"), R.mipmap.ic_selectall, R.drawable.ic_save, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ModelLC> list = adapter.getList();
                for (ModelLC lc:list){
                    lc.setCheck(true);
                }
                adapter.notifyDataSetChanged();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ModelLC> listl = adapter.getList();
                ArrayList<ModelLC> list = new ArrayList<>();
                for (ModelLC lc:listl){
                    if (lc.isCheck()){
                        list.add(lc);
                    }
                }
                Intent intent = new Intent(ContactsView.INSERT_CONTACT_ALL);
                intent.putParcelableArrayListExtra(ModelLC.TAG,list);
                LocalBroadcastManager.getInstance(LocalContacts.this).sendBroadcast(intent);
                finish();
            }
        });
    }

    private void getLocalContacts(){
        LocalContactsReadTools tools = LocalContactsReadTools.getHinstance();
        List<ModelLC> list = tools.readContacts(this);
        adapter.addData(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        if (adapter.getCount()==0){
            ToastTools.show("Empty data");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            // 得到了读取联系人的权限
            case MyPermission.PERMISSIONS_REQUEST_READ_CONTACTS:
                // 这样才算成功拿到了权限
                if (permissions[0].equals(Manifest.permission.READ_CONTACTS)&&grantResults[0]==0){
                    getLocalContacts();
                } else {
                    // 拒绝了？
                    finish();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        View bg = view.findViewById(R.id.ilc_bg);
        ModelLC lc = adapter.get(i);
        lc.setCheck(!lc.isCheck());
        if (lc.isCheck()){
            bg.setBackgroundColor(color.getProspectColor());
        }else {
            bg.setBackgroundColor(color.getBackgroundColor());
        }
    }
}
