package com.lfq.diary.password;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseLayout;
import com.lfq.diary.callback.CharsCallback;
import com.lfq.diary.callback.NumberCallback;
import com.lfq.diary.util.LanguageTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class PasswordView extends BaseLayout{
    private final String TAG = "PasswordView";

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int attrView() {
        return R.layout.view_password;
    }

    @Override
    protected void onInitView() {
        for (View view:button){
            view.setOnClickListener(this);
        }
    }

    @BindViews({R.id.password_input_1,R.id.password_input_2,R.id.password_input_3,R.id.password_input_4,})
    ImageView input[];
    @BindViews({R.id.password_1,R.id.password_2,R.id.password_3,
            R.id.password_4,R.id.password_5,R.id.password_6,
            R.id.password_7,R.id.password_8,R.id.password_9,})
    TextView button[];
    @BindView(R.id.password_message)
    TextView message;
    @BindView(R.id.password_clear)
    TextView clear;

    private StringBuilder password = new StringBuilder();

    @Override
    protected void onClick(View v, int id) {
        if (password.length()==4){
            return;
        }
        for (TextView t:button){
            if (t==v){
                input[password.length()].setVisibility(VISIBLE);
                password.append(t.getText());
                break;
            }
        }
        if (password.length()==4){
            if (charsCallback!=null){
                charsCallback.onCallback(password.toString());
            }
            for (ImageView view:input){
                view.setVisibility(INVISIBLE);
            }
            password = new StringBuilder();
        }
    }

    /**
     * 密码输入完成后的回调
     */
    private CharsCallback charsCallback;

    public void setCharsCallback(CharsCallback charsCallback) {
        this.charsCallback = charsCallback;
    }

    /**
     * 设置提示信息
     * @param message
     */
    public void setMessage(String message) {
        this.message.setText(message);
    }

    /**
     * 清除密码的相关操作
     */
    private Runnable runnable;
    @OnClick(R.id.password_clear)
    public void onClear(View view){
        if (runnable!=null){
            runnable.run();
        }
    }
    public void setClearRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
    public void showClearOprateView(){
        LanguageTools language = LanguageTools.getHinstance();
        clear.setVisibility(VISIBLE);
        clear.setText(language.get("清除密码"));
    }
}
