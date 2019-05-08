package com.lfq.diary.home.content.diary;

import android.os.Parcel;
import android.os.Parcelable;

import com.lfq.diary.util.LanguageTools;

import java.util.Calendar;
import java.util.Date;

public class ModelDiary implements Parcelable{
    public static final String TAG = "ModelDiary";
    // 原始数据
    private int id;
    private String title;
    private String content;
    private long time;// 创建日记的时间
    private int  mood;// 心情，仅代表心情图片数组的下标号
    private int weather;// 天气，也只是代表下标号

    /**
     * 构造函数，在数据库中读取的时候会读到id
     * @param id
     * @param title
     * @param content
     * @param time
     * @param mood
     * @param weather
     */
    public ModelDiary(int id, String title, String content, long time, int mood, int weather) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.mood = mood;
        this.weather = weather;
        create();
    }

    /**
     * 构造函数，创建的时候肯定没有id
     * @param title
     * @param content
     * @param time
     * @param mood
     * @param weather
     */
    public ModelDiary(String title, String content, long time, int mood, int weather) {
        this(0,title,content,time,mood,weather);
    }

    /**
     * 构造函数，用于查询是否是同一天
     * @param time
     */
    public ModelDiary(long time){
        this(0,"","",time,0,0);
    }

    // 需要根据time进一步处理才能得到的数据
    private int year;
    private int month;
    private int date;
    private int week;
    private int hour;
    private int minute;

    protected ModelDiary(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        time = in.readLong();
        mood = in.readInt();
        weather = in.readInt();
        year = in.readInt();
        month = in.readInt();
        date = in.readInt();
        week = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        showMonth = in.readByte() != 0;
    }

    public static final Creator<ModelDiary> CREATOR = new Creator<ModelDiary>() {
        @Override
        public ModelDiary createFromParcel(Parcel in) {
            return new ModelDiary(in);
        }

        @Override
        public ModelDiary[] newArray(int size) {
            return new ModelDiary[size];
        }
    };

    /**
     * 根据time来创建年月日周小时分钟
     * 注意：获取到周的范围是1~7，小时是0~23，英语国家星期天是第一天
     */
    public void create(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        date = cal.get(Calendar.DATE);
        week = cal.get(Calendar.DAY_OF_WEEK);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    /**
     * 获取时间的 年月日格式
     * @return
     */
    public String parseDAY(){
        LanguageTools tools = LanguageTools.getHinstance();
        return year+tools.get("年")+month+tools.get("月")+ date +tools.get("日");
    }

    /**
     * 获取时间 年和月
     * @return
     */
    public String parseYM(){
        LanguageTools tools = LanguageTools.getHinstance();
        return year+tools.get("年")+tools.getMonth(month);
    }

    /**
     * 获取时间 格式为：H:M
     * @return
     */
    public String parseHM(){
        return String.format("%d:%2d",hour,minute);
    }

    /**
     * 获取现在是第几周
     * @return
     */
    public String parseWEEK(){
        LanguageTools tools = LanguageTools.getHinstance();
        return tools.getWeek(week);
    }

    /**
     * 获取现在是第几月
     * @return
     */
    public String parseMONTH(){
        LanguageTools tools = LanguageTools.getHinstance();
        return tools.getMonth(month);
    }

    // 视图数据
    // 是否要显示月份
    private boolean showMonth = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isShowMonth() {
        return showMonth;
    }

    public void setShowMonth(boolean showMonth) {
        this.showMonth = showMonth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeLong(time);
        parcel.writeInt(mood);
        parcel.writeInt(weather);
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(date);
        parcel.writeInt(week);
        parcel.writeInt(hour);
        parcel.writeInt(minute);
        parcel.writeByte((byte) (showMonth ? 1 : 0));
    }
}
