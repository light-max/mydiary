package com.lfq.diary.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BasePagerAdapter;
import com.lfq.diary.home.content.diary.DiaryView;
import com.lfq.diary.home.content.notepad.NotepadView;
import com.lfq.diary.home.content.phone.ContactDialog;
import com.lfq.diary.home.content.phone.ContactsView;
import com.lfq.diary.home.title.ApplicationTitle;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.MyPermission;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 这个是主页
 */
public class Home extends BaseActivity {
    private String TAG = "Home";

    @Override
    protected int attrView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controllerTitleasContent.unbind();
    }

    @BindView(R.id.ah_content)
    ViewPager pager;
    @BindView(R.id.ah_title)
    ApplicationTitle applicationTitle;
    @BindView(R.id.ah_establish)
    ImageView establish;

    private ColorTools colorTools = ColorTools.getInstance();
    private ControllerTitleasContent controllerTitleasContent = new ControllerTitleasContent();
    private BasePagerAdapter<View> adapter = new BasePagerAdapter<>();
    private DiaryView diaryView;
    private ContactsView contactsView;
    private NotepadView notepadView;

    @Override
    protected void onInitData() {
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setCancelable(false);
        dlg.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                diaryView = new DiaryView(Home.this);
                contactsView = new ContactsView(Home.this);
                notepadView = new NotepadView(Home.this);
                adapter.addView(diaryView);
                adapter.addView(contactsView);
                adapter.addView(notepadView);
                Home.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pager.setAdapter(adapter);
                        dlg.dismiss();
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onInitView() {
        establish.setColorFilter(colorTools.getProspectColor());
        pager.setBackground(colorTools.getAppThemeDrawable());
        controllerTitleasContent.setApplicationTitle(applicationTitle);
        controllerTitleasContent.setViewPager(pager);
        controllerTitleasContent.bind();
    }

    /**
     * 建立工作区，写日记或者添加联系人或者添加记事本
     * @param view
     */
    @OnClick(R.id.ah_establish)
    public void establishWorkspace(View view){
        controllerTitleasContent.establishWorkspace(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            // 得到了打电话的权限
            case MyPermission.PERMISSIONS_REQUEST_CALL_PHONE:
                // 这样才算成功拿到了权限
                if (permissions[0].equals(Manifest.permission.CALL_PHONE)&&grantResults[0]==0){
                    Intent intent = new Intent(ContactDialog.CALLPHONE);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                break;
        }
    }
}
