package com.lfq.diary.home.content.phone.local;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 本地联系人数据模型
 */
public class ModelLC implements Parcelable {
    public static final String TAG = "ModelLocalContact";
    private String name,phone;

    // 是否被选中
    private boolean check = false;

    protected ModelLC(Parcel in) {
        name = in.readString();
        phone = in.readString();
        check = in.readByte() != 0;
    }

    public static final Creator<ModelLC> CREATOR = new Creator<ModelLC>() {
        @Override
        public ModelLC createFromParcel(Parcel in) {
            return new ModelLC(in);
        }

        @Override
        public ModelLC[] newArray(int size) {
            return new ModelLC[size];
        }
    };

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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ModelLC(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeByte((byte) (check ? 1 : 0));
    }
}
