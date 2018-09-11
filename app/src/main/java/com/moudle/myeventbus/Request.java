package com.moudle.myeventbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Request implements Parcelable{

    private String data;

    private int type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    protected Request(Parcel in) {
        data = in.readString();
        type = in.readInt();
    }

    public Request(String data, int type) {
        this.data = data;
        this.type = type;
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeInt(type);
    }
}
