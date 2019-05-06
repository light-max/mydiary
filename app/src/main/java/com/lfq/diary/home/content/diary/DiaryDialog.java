package com.lfq.diary.home.content.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseDialog;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;
import com.lfq.diary.util.MessageBox;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class DiaryDialog extends BaseDialog {
    public DiaryDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int attrView() {
        return R.layout.dlg_diary;
    }

    private ColorTools color;
    private LanguageTools language;
    private ModelDiary diary;
    private Action action = Action.getHinstance();
    private Context mContext;

    public void setDiary(ModelDiary diary) {
        this.diary = diary;
        layout[0].setBackgroundColor(color.getProspectColor());
        layout[1].setBackgroundColor(color.getProspectColor());
        year_month.setText(diary.parseYM());
        date.setText(String.valueOf(diary.getDate()));
        week.setText(diary.parseWEEK());
        time.setText(diary.parseHM());
        title.setText(diary.getTitle());
        content.setText(diary.getContent());
    }

    @BindViews({R.id.dd_view1,R.id.dd_view2})
    View layout[];
    @BindView(R.id.dd_year_month)
    TextView year_month;
    @BindView(R.id.dd_date)
    TextView date;
    @BindView(R.id.dd_week)
    TextView week;
    @BindView(R.id.dd_time)
    TextView time;
    @BindView(R.id.dd_title)
    TextView title;
    @BindView(R.id.dd_content)
    TextView content;

    @Override
    protected void initData() {
        color = ColorTools.getInstance();
        language = LanguageTools.getHinstance();
    }

    @OnClick(R.id.dd_close)
    public void onClose(View view){
        dismiss();
    }

    @OnClick(R.id.dd_delete)
    public void onDelete(View view){
        action.delete(this,diary);
    }

    @OnClick(R.id.dd_edit)
    public void onEdit(View view){
        Intent intent = new Intent(mContext,WriteDiary.class);
        intent.putExtra(ModelDiary.TAG,diary);
        mContext.startActivity(intent);
        dismiss();
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
