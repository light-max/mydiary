package com.lfq.diary.home.content.diary;

import android.content.Context;
import android.util.AttributeSet;

import com.lfq.diary.R;

public class MoodSelector extends Selector{
    private String TAG = "MoodSelect";

    public MoodSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int[] createResIdArray() {
        return resId;
    }

    public static final int[] resId = new int[]{
            R.mipmap.ic_mood_happy,
            R.mipmap.ic_mood_soso,
            R.mipmap.ic_mood_unhappy
    };
}
