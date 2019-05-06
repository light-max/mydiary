package com.lfq.diary.home.content.diary;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseAdapter;
import com.lfq.diary.util.ColorTools;

public class DiaryAdapter extends BaseAdapter<ModelDiary> {
    public DiaryAdapter(Context context) {
        super(context);
    }

    private ColorTools color= ColorTools.getInstance();

    @Override
    protected int attrView() {
        return R.layout.item_diary;
    }

    @Override
    protected void onGetView(View v, int position, ModelDiary obj) {
        ViewHolder holder = new ViewHolder(v);
        if (obj.isShowMonth()){
            holder.month.setVisibility(View.VISIBLE);
            holder.month.setText(obj.parseMONTH());
        }else {
            holder.month.setVisibility(View.GONE);
        }
        holder.day.setText(obj.getDate()+"");
        holder.week.setText(obj.parseWEEK());
        holder.time.setText(obj.parseHM());
        holder.title.setText(obj.getTitle());
        holder.content.setText(obj.getContent());
        holder.mood.setImageResource(MoodSelector.resId[obj.getMood()]);
        holder.weather.setImageResource(WeatherSelector.resId[obj.getWeather()]);
    }

    private class ViewHolder{
        public ViewHolder(View v){
            month = VH.get(v,R.id.id_month);
            day = VH.get(v,R.id.id_day);
            week = VH.get(v,R.id.id_week);
            time = VH.get(v,R.id.id_time);
            title = VH.get(v,R.id.id_title);
            content = VH.get(v,R.id.id_content);
            mood = VH.get(v,R.id.id_mood);
            weather = VH.get(v,R.id.id_weather);
            int c = color.getProspectColor();
            day.setTextColor(c);
            week.setTextColor(c);
            time.setTextColor(c);
            title.setTextColor(c);
            content.setTextColor(c);
            mood.setColorFilter(c);
            weather.setColorFilter(c);
        }
        public TextView month;
        public TextView day,week,time;
        public TextView title,content;
        public ImageView mood,weather;
    }
}
