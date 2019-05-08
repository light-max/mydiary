package com.lfq.diary.home.content.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseDialog;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MyPermission;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 联系人对话框
 */
public class ContactDialog extends BaseDialog {
    private String TAG = "ContactDialog";

    public ContactDialog(Context context) {
        super(context);
        this.activity = (AppCompatActivity) context;
        registerCallPhoneBroadcast();
    }

    @Override
    protected int attrView() {
        return R.layout.dlg_contact;
    }

    private Action action = Action.getHinstance();
    private AppCompatActivity activity;
    private ModelContact contact;
    private ColorTools color;
    private LanguageTools language;

    @BindView(R.id.dc_bg1)
    View ba1;
    @BindView(R.id.dc_bg2)
    View bg2;
    @BindView(R.id.dc_title)
    TextView title;
    @BindView(R.id.dc_name)
    TextView name;
    @BindView(R.id.dc_phone)
    TextView phone;
    @BindView(R.id.dc_call)
    Button call;

    @OnClick(R.id.dc_close)
    public void onClose(View view){
        dismiss();
    }
    @OnClick(R.id.dc_call)
    public void onCall(View view){
        MyPermission permission = MyPermission.getHinstance();
        if (permission.getCallPhone(activity)){
            Uri uri = Uri.parse("tel:"+contact.getPhone());
            Intent intent = new Intent(Intent.ACTION_CALL,uri);
            getContext().startActivity(intent);
            dismiss();
        }
    }
    @OnClick(R.id.dc_delete)
    public void onDelete(View view){
        action.delete(this,contact);
    }
    @OnClick(R.id.dc_edit)
    public void onEdit(View view){
        dismiss();
        new CreateContactsDialog(getmContext()).setContact(contact).show();
    }

    @Override
    protected void initData() {
        color = ColorTools.getInstance();
        language = LanguageTools.getHinstance();
    }

    @Override
    protected void initView() {
        ba1.setBackgroundColor(color.getProspectColor());
        bg2.setBackgroundColor(color.getProspectColor());
        title.setText(language.get("联系人"));
        call.setText(language.get("拨号"));
        call.getBackground().setTint(color.getProspectColor());
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }

    /**
     *
     * @param contact
     */
    public ContactDialog setContact(ModelContact contact) {
        this.contact = contact;
        if (contact.getName().length()==0){
            name.setText(language.get("未命名"));
        }else {
            name.setText(contact.getName());
        }
        phone.setText(contact.getPhone());
        return this;
    }

    /**
     * 自定义广播，成功拿到了拨号权限
     */
    public static final String CALLPHONE = "com.lfq.phone.permission.CALL_PHONE";
    private class CallPhoneBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onCall(null);
        }
    }
    private void registerCallPhoneBroadcast(){
        BroadcastReceiver mReceiver = new CallPhoneBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(CALLPHONE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver,intentFilter);
    }
}
