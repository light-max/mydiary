package com.lfq.diary.home.content.diary;

import android.content.Context;
import android.util.AttributeSet;

import com.lfq.diary.R;

public class WeatherSelector extends Selector{
    private String TAG = "WeatherSelector";

    public WeatherSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int[] createResIdArray() {
        return resId;
    }

    public static final int[] resId = new int[]{
            R.mipmap.ic_weather_sunny,
            R.mipmap.ic_weather_rainy,
            R.mipmap.ic_weather_cloud,
            R.mipmap.ic_weather_snowy,
            R.mipmap.ic_weather_foggy,
            R.mipmap.ic_weather_windy,
    };
}
