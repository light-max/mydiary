package com.lfq.diary.appset.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MyApplication;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

public class About extends BaseActivity {
    @Override
    protected int attrView() {
        return R.layout.activity_about;
    }

    @BindView(R.id.aa_content)
    TextView content;

    @Override
    protected void onInitData() {
        MyApplication application = MyApplication.getHinstance();
        final AssetManager assetManager = application.getResources().getAssets();
        new Thread(){
            @Override
            public void run() {
                try {
                    InputStream in = assetManager.open("about");
                    StringBuffer buffer = new StringBuffer();
                    byte[] aByte = new byte[1024];
                    do{
                        int len = in.read(aByte);
                        if (len > 0){
                            buffer.append(new String(aByte,0,len));
                        }else {
                            break;
                        }
                    }while (true);
                    Message msg = handler.obtainMessage();
                    msg.obj = buffer;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            content.setText(msg.obj.toString());
        }
    };

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        LanguageTools language = LanguageTools.getHinstance();
        title.set(language.get("关于"), R.drawable.ic_quit, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }
}
