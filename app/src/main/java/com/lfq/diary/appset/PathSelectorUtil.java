package com.lfq.diary.appset;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseDialog;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 路径选择器，把选择到的路径以字符串的形式覆盖到调用者的EditText
 */
public class PathSelectorUtil extends BaseDialog implements AdapterView.OnItemClickListener {
    private String TAG = "PathSelectorUtil";

    public PathSelectorUtil(Context context) {
        super(context);
    }

    @BindView(R.id.dp_bg)
    View bg;
    @BindView(R.id.dp_path)
    TextView path;
    @BindView(R.id.dp_ok)
    Button ok;
    @BindView(R.id.dp_cancel)
    Button cancel;
    @BindView(R.id.dp_list)
    ListView list;

    @OnClick(R.id.dp_newfolder)
    public void onNewFolder(View view){
        final EditText name = new EditText(getmContext());
        new AlertDialog.Builder(getmContext())
                .setTitle(language.get("新建文件夹"))
                .setView(name)
                .setPositiveButton(language.get("确定"), new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File newfolder = new File(path.getText().toString(),name.getText().toString());
                        if (newfolder.mkdir()){
                            adapter.addData(newfolder);
                            adapter.notifyDataSetChanged();
                            ToastTools.show(language.get("创建成功"));
                        }else {
                            ToastTools.show(language.get("创建失败"));
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.dp_cancel)
    public void onCancel(View view){
        dismiss();
    }

    @OnClick(R.id.dp_ok)
    public void onOk(View view){
        if (selectFile){
        }else {
            editText.setText(path.getText().toString());
            dismiss();
        }
    }

    private LanguageTools language;
    private ColorTools color;
    private FilesAdapter adapter;

    @Override
    protected int attrView() {
        return R.layout.dlg_pathselector;
    }

    @Override
    protected void initData() {
        language = LanguageTools.getHinstance();
        color = ColorTools.getInstance();
        adapter = new FilesAdapter(getmContext());
    }

    @Override
    protected void initView() {
        bg.setBackgroundColor(color.getProspectColor());
        ok.getBackground().setTint(color.getProspectColor());
        cancel.getBackground().setTint(color.getProspectColor());
        ok.setText(language.get("确定"));
        cancel.setText(language.get("取消"));
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void onBackPressed() {
        File up = new File(path.getText().toString());
        if (up.getPath().equals(Environment.getExternalStorageDirectory().getPath())) {
            super.onBackPressed();
        }else {
            setCurrentPath(up.getParentFile());
        }
    }

    private EditText editText;

    public PathSelectorUtil setEditText(EditText editText) {
        this.editText = editText;
        return this;
    }

    // 选择文件？
    private boolean selectFile = false;

    public PathSelectorUtil setSelectFile(boolean selectFile) {
        this.selectFile = selectFile;
        File root = Environment.getExternalStorageDirectory();
        setCurrentPath(root);
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File fp = adapter.get(i);
        if (fp.isDirectory()){
            setCurrentPath(fp);
        }else {
            editText.setText(fp.getPath());
            dismiss();
        }
    }

    /**
     * 设置当前目录
     * @param file
     */
    private void setCurrentPath(File file){
        if (file != null){
            path.setText(file.getPath());
            adapter.clear();
            if (selectFile){
                adapter.addData(file.listFiles());
            }else {
                for (File fp:file.listFiles()){
                    if (fp.isDirectory()){
                        adapter.addData(fp);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
