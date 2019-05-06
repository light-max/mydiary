package com.lfq.diary.home.content.notepad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 日记本数据模型
 */
public class ModelNotepad implements Parcelable {
    public static final String TAG = "ModelNotepad";

    public ModelNotepad(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    protected ModelNotepad(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<ModelNotepad> CREATOR = new Creator<ModelNotepad>() {
        @Override
        public ModelNotepad createFromParcel(Parcel in) {
            return new ModelNotepad(in);
        }

        @Override
        public ModelNotepad[] newArray(int size) {
            return new ModelNotepad[size];
        }
    };

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

    private int id;
    private String title,content;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
    }
}
