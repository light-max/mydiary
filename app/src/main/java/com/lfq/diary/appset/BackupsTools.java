package com.lfq.diary.appset;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.lfq.diary.R;
import com.lfq.diary.callback.CharsCallback;
import com.lfq.diary.home.content.diary.DiaryView;
import com.lfq.diary.home.content.diary.ModelDiary;
import com.lfq.diary.home.content.notepad.ModelNotepad;
import com.lfq.diary.home.content.notepad.NotepadView;
import com.lfq.diary.home.content.phone.ContactsView;
import com.lfq.diary.home.content.phone.ModelContact;
import com.lfq.diary.password.PasswordView;
import com.lfq.diary.util.EncryptionTools;
import com.lfq.diary.util.ExportDatabase;
import com.lfq.diary.util.ImprotDatabase;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.ToastTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BackupsTools {
    private static String TAG = "BackupsTools";

    /**
     * 导入数据
     * @param activity
     * @param source 数据源
     */
    public static void Import(AppCompatActivity activity, File source){
        new ImprotThread(activity,source).start();
    }

    /**
     * 导出数据
     * @param activity
     * @param target 目标目录
     */
    public static void Export(AppCompatActivity activity, File target){
        File file = new File(target,System.currentTimeMillis()+".db");
        try {
            if (file.createNewFile()) {
                new ExportThread(activity, file).start();
            }
        } catch (IOException e) {
            // 创建失败？别导了 正常情况是不会出现这种状态的，如果出现了，那就是不正常，直接来个error
            ToastTools.show("error export");
        }
    }

    private static LanguageTools language = LanguageTools.getHinstance();

    private static class MyDlg extends ProgressDialog{
        public MyDlg(Context context) {
            super(context);
            super.setCancelable(false);
            super.setTitle("Run ing...");
        }
    }

    private static class ExportThread extends Thread{
        public ExportThread(AppCompatActivity activity,File target){
            this.activity = activity;
            this.target = target;
            dlg = new MyDlg(activity);
            dlg.show();
        }

        private File target;
        private AppCompatActivity activity;
        private MyDlg dlg;

        @Override
        public void run() {
            ExportDatabase database = new ExportDatabase(target);
            List<ModelDiary> diaries = com.lfq.diary.home.content.diary.DatabaseTools.getHinstance().query();
            List<ModelContact> contacts = com.lfq.diary.home.content.phone.DatabaseTools.getHinstance().query();
            List<ModelNotepad> notepads = com.lfq.diary.home.content.notepad.DatabaseTools.getHinstance().query();
            String password = com.lfq.diary.password.DatabaseTools.getHinstance().getPassword();
            database.insertDiary(diaries);
            database.insertContact(contacts);
            database.insertNotepad(notepads);
            database.insertPassword(password);
            diaries.clear();
            contacts.clear();
            notepads.clear();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dlg.dismiss();
                    ToastTools.show("export ok");
                }
            });
            database.close();
            EncryptionTools.encryption(target);
            // 可以选择文件发送
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, target);
            intent.setType("*/*");
            activity.startActivity(Intent.createChooser(intent, LanguageTools.getHinstance().get("分享到...")));
        }
    }

    private static class ImprotThread extends Thread {
        public ImprotThread(AppCompatActivity activity, File source) {
            this.activity = activity;
            this.source = source;
            dlg = new MyDlg(activity);
            dlg.show();
        }

        private File source;
        private AppCompatActivity activity;
        private MyDlg dlg;
        private ImprotDatabase database = null;

        @Override
        public void run() {
            // 为了不损坏用户的数据，先弄一个副本，然后用这个副本里的数据
            try {
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(new File(source.getPath()+".temp"));
                int value;
                while ((value = in.read())!=-1){
                    out.write(value);
                }
                in.close();
                out.flush();
                out.close();
                source = new File(source.getPath()+".temp");
            } catch (IOException e) {
                error();
                return;
            }
            EncryptionTools.encryption(source);
            // 开始导入
            try {
                database = new ImprotDatabase(source);
                // 如果抛异常，可能是打不开这个数据库
            }catch (RuntimeException e){
                error();
                return;
            }
            // 输入密码后继续
            final String password = database.getPassword();
            // 无密码？
            if (password.length()==0){
                new ImprotThread_(activity,database).start();
                return;
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dlg.dismiss();
                    final InputPasswordDialog dialog = new InputPasswordDialog(activity);
                    dialog.setPasswordCallback(new CharsCallback() {
                        @Override
                        public void onCallback(String args) {
                            if (args.equals(password)){
                                dialog.dismiss();
                                new ImprotThread_(activity,database).start();
                            }else {
                                ToastTools.show(language.get("密码错误"));
                            }
                        }
                    }).show();
                }
            });
        }

        // 出错了随便提示一下
        private void error(){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastTools.show("improt error");
                    dlg.dismiss();
                }
            });
        }
    }

    private static class ImprotThread_ extends Thread {
        public ImprotThread_(AppCompatActivity activity, ImprotDatabase database) {
            this.activity = activity;
            this.database = database;
            dlg = new MyDlg(activity);
            dlg.show();
        }

        private ImprotDatabase database;
        private AppCompatActivity activity;
        private MyDlg dlg;

        @Override
        public void run() {
            List<ModelContact> contacts = database.getContacts();
            List<ModelNotepad> notepads = database.getNotepads();
            List<ModelDiary> diaries = database.getDiarys();
            com.lfq.diary.home.content.phone.DatabaseTools c_db = com.lfq.diary.home.content.phone.DatabaseTools.getHinstance();
            com.lfq.diary.home.content.notepad.DatabaseTools n_db = com.lfq.diary.home.content.notepad.DatabaseTools.getHinstance();
            com.lfq.diary.home.content.diary.DatabaseTools d_db = com.lfq.diary.home.content.diary.DatabaseTools.getHinstance();
            // 联系人和记事本直接写入就行了
            c_db.insert(contacts);
            n_db.insert(notepads);
            // 日记的话需要做特殊处理，按时间来排序 时间小的在前面
            diaries.addAll(d_db.query());
            Collections.sort(diaries, new Comparator<ModelDiary>() {
                @Override
                public int compare(ModelDiary diary, ModelDiary t1) {
                    if (diary.getTime() < t1.getTime()){
                        return -1;
                    }else if (diary.getTime() > t1.getTime()){
                        return 1;
                    }
                    return 0;
                }
            });
            d_db.clear();
            d_db.insert(diaries);
            database.close();
            // 提醒三个listview重新加载
            senBroadcast(DiaryView.RELOAD_DIARY);
            senBroadcast(ContactsView.RELOAD_CONTACTS);
            senBroadcast(NotepadView.RELOAD_NOTEPAD);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastTools.show("improt ok");
                    dlg.dismiss();
                }
            });
        }

        private void senBroadcast(String action){
            Intent intent = new Intent(action);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
    }

    private static class InputPasswordDialog extends Dialog{
        public InputPasswordDialog(Context context) {
            super(context,R.style.BaseDialog);
            view = new PasswordView(context,null);
            view.setMessage(language.get("请输入被导入的数据的密码"));
            super.setContentView(view);
        }

        public PasswordView view;

        public InputPasswordDialog setPasswordCallback(CharsCallback callback){
            view.setCharsCallback(callback);
            return this;
        }

        @Override
        public void show() {
            super.show();
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(params);
        }
    }
}
