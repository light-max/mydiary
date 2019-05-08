package com.lfq.diary.home.content.phone;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseDialog;
import com.lfq.diary.home.content.phone.local.LocalContacts;
import com.lfq.diary.util.ActivityTools;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建联系人使用的对话框
 */
public class CreateContactsDialog extends BaseDialog {
    private String TAG = "CreateContactsDialog";

    public CreateContactsDialog(Context context) {
        super(context);
    }

    @BindView(R.id.dcc_title)
    TextView title;
    @BindView(R.id.dcc_name)
    TextView name;
    @BindView(R.id.dcc_phone)
    TextView phone;
    @BindView(R.id.dcc_save)
    Button save;
    @BindView(R.id.dcc_cancel)
    Button cancel;
    @BindView(R.id.dcc_importlocal)
    TextView importlocal;
    @OnClick(R.id.dcc_cancel)
    public void onCancel(View view){
        dismiss();
    }
    // 保存电话，这里不做任何判断了，直接保存，反正也不影响使用
    @OnClick(R.id.dcc_save)
    public void onSave(View view){
        ModelContact contact = new ModelContact(0,name.getText().toString(),phone.getText().toString());
        /**
         *  如果contact为空，那么就是插入了新数据，否则只是修改了原来的数据
         */
        contact.setId(this.contact==null?0:this.contact.getId());
        action.save(this,contact,this.contact==null);
    }
    @OnClick(R.id.dcc_importlocal)
    public void onImportLocal(View view){
        dismiss();
        ActivityTools.startActivity(getmContext(), LocalContacts.class);
    }

    private Action action = Action.getHinstance();
    private ColorTools color;
    private LanguageTools language;

    private ModelContact contact = null;

    public CreateContactsDialog setContact(ModelContact contact) {
        this.contact = contact;
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        return this;
    }

    @Override
    protected int attrView() {
        return R.layout.dlg_create_contacts;
    }

    @Override
    protected void initData() {
        color = ColorTools.getInstance();
        language = LanguageTools.getHinstance();
    }

    @Override
    protected void initView() {
        title.setText(language.get("新建联系人"));
        title.setBackgroundColor(color.getProspectColor());
        name.setHint(language.get("备注名"));
        phone.setHint(language.get("电话号码"));
        save.setText(language.get("保存"));
        cancel.setText(language.get("取消"));
        importlocal.setText(language.get("导入本地联系人"));
        save.getBackground().setTint(color.getProspectColor());
        cancel.getBackground().setTint(color.getProspectColor());
    }
}
