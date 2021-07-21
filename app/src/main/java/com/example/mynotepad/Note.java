package com.example.mynotepad;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    String title;
    String description;
    String dateCreate;

    public Note() {
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        dateCreate = in.readString();
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.dateCreate = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(dateCreate);
    }
}
