package com.example.mynotepad;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Note implements Parcelable {
    public final String id;
    public final String title;
    public final Date date;
    public final String description;

    public Note(String id, String title, Date date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        date = null;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
    }
}
