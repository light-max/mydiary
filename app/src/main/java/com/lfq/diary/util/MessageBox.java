package com.lfq.diary.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class MessageBox extends BaseDialog {
    public MessageBox(Context context) {
        super(context);
    }

    @BindView(R.id.messagebox_title)
    TextView title;
    @BindView(R.id.messagebox_content)
    TextView content;
    @BindView(R.id.messagebox_left)
    TextView left;
    @BindView(R.id.messagebox_right)
    TextView right;

    @OnClick(R.id.messagebox_left)
    public void leftClick(View view){
        super.dismiss();
    }
    @OnClick(R.id.messagebox_right)
    public void rightClick(View view){
        super.dismiss();
    }

    @Override
    protected int attrView() {
        return R.layout.messagebox;
    }

    @Override
    protected void initView() {
        String[] array = get();
        title.setText(array[0]);
        content.setText(array[1]);
        left.setText(array[2]);
        right.setText(array[3]);
    }

    /**
     * 返回一个一维数组，长度4，标题，内容，左按钮，右按钮
     * @return
     */
    public abstract String[] get();
}
