package com.lfq.diary.home.content.phone;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 联系人数据模型
 */
public class ModelContact implements Parcelable {
    public static final String TAG = "ModelContact";

    private int id;
    private String name;
    private String phone;

    protected ModelContact(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<ModelContact> CREATOR = new Creator<ModelContact>() {
        @Override
        public ModelContact createFromParcel(Parcel in) {
            return new ModelContact(in);
        }

        @Override
        public ModelContact[] newArray(int size) {
            return new ModelContact[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ModelContact(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.phone = contact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(phone);
    }
}
