package com.lfq.diary.home.content.diary;

import android.view.View;
import android.widget.EditText;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseActivity;
import com.lfq.diary.base.BaseTitle;
import com.lfq.diary.util.ColorTools;
import com.lfq.diary.util.LanguageTools;

import butterknife.BindView;

/**
 * 写日记的Activity，也可以用于修改日记
 */
public class WriteDiary extends BaseActivity {
    private String TAG = "WriteDiary";
    private LanguageTools language;
    private ColorTools color;
    private ModelDiary diary;
    private Action action;

    @Override
    protected int attrView() {
        return R.layout.activity_write_diary;
    }

    public ModelDiary getDiary() {
        return diary;
    }

    @BindView(R.id.aw_title)
    EditText title;
    @BindView(R.id.aw_content)
    EditText content;
    @BindView(R.id.aw_mood)
    MoodSelector mood;
    @BindView(R.id.aw_weather)
    WeatherSelector weather;

    @Override
    protected void onInitData() {
        color = ColorTools.getInstance();
        diary = getIntent().getParcelableExtra(ModelDiary.TAG);
        action = Action.getHinstance();
        language = LanguageTools.getHinstance();
    }

    @Override
    protected void onInitView() {
        title.setHint(language.get("标题"));
        content.setHint(language.get("内容"));
        title.setText(diary.getTitle());
        content.setText(diary.getContent());
        mood.setPosition(diary.getMood());
        weather.setPosition(diary.getWeather());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreateTitleBar(BaseTitle title) {
        // 这里的标题是设置一个时间
        title.set(diary.parseDAY(), R.drawable.ic_quit, R.drawable.ic_save,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action.save(WriteDiary.this);
                    }
                });
    }
}
