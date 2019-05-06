package com.lfq.diary.appset.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.appset.BackupsTools;
import com.lfq.diary.appset.FilesAdapter;
import com.lfq.diary.appset.PathSelectorUtil;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MyPermission;
import com.lfq.diary.util.ToastTools;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 备份数据
 * 导入和导出
 */
public class Backups extends BaseActivity {
    private String TAG = "BAckups";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyPermission.getHinstance().getWriteExternalStorage(this)){

        }
    }

    @Override
    protected int attrView() {
        return R.layout.activity_backuups;
    }

    @BindView(R.id.ab_i_title)
    TextView i_title;
    @BindView(R.id.ab_i_path)
    EditText i_path;
    @BindView(R.id.ab_i_call)
    Button i_call;
    @BindView(R.id.ab_o_title)
    TextView o_title;
    @BindView(R.id.ab_o_path)
    EditText o_path;
    @BindView(R.id.ab_o_call)
    Button o_call;
    @BindView(R.id.ab_o_tips)
    TextView o_tips;

    private LanguageTools language;
    private ColorTools color;

    @Override
    protected void onInitData() {
        language = LanguageTools.getHinstance();
        color = ColorTools.getInstance();
    }

    @Override
    protected void onInitView() {
        i_title.setText(language.get("导入"));
        i_path.setHint(language.get("点击选择导入的文件"));
        i_call.setText(language.get("导入"));
        i_call.getBackground().setTint(color.getProspectColor());
        o_title.setText(language.get("导出"));
        o_path.setHint(language.get("点击选择导出的目录"));
        o_call.setText(language.get("导出"));
        o_call.getBackground().setTint(color.getProspectColor());
        o_tips.setText(language.getBackupsTips());
    }

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        title.set(language.get("备份"), R.drawable.ic_quit, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }

    @OnClick(R.id.ab_i_path)
    public void onI_path(View view){
        new PathSelectorUtil(this).setSelectFile(true).setEditText(i_path).show();
    }

    @OnClick(R.id.ab_o_path)
    public void onO_path(View view){
        new PathSelectorUtil(this).setSelectFile(false).setEditText(o_path).show();
    }

    /**
     * 把目标文件的所有数据导入到当前使用的数据库中
     * @param view
     */
    @OnClick(R.id.ab_i_call)
    public void onI_call(View view){
        BackupsTools.Import(this,new File(i_path.getText().toString()));
    }

    /**
     * 把所有数据打包输出 日记 联系人 记事本 密码
     * @param view
     */
    @OnClick(R.id.ab_o_call)
    public void onO_call(View view){
        BackupsTools.Export(this,new File(o_path.getText().toString()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MyPermission.PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){

                }else{
                    finish();
                }
                break;
        }
    }
}
