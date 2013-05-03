package com.jameselsey.demos.androidsms.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Sms implements Parcelable{

    private String sender;
    private String body;

    public Sms(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    private Sms(Parcel in) {
        sender = in.readString();
        body = in.readString();
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(body);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static final Parcelable.Creator<Sms> CREATOR
            = new Parcelable.Creator<Sms>() {
        public Sms createFromParcel(Parcel in) {
            return new Sms(in);
        }

        public Sms[] newArray(int size) {
            return new Sms[size];
        }
    };
}
