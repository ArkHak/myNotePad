package com.example.mynotepad;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Note implements Parcelable {
    public final String id;
    public String subject;
    public final Date date;
    public String description;

    public Note(String id, String title, Date date, String toString) {
        this.id = id;
        this.subject = title;
        this.date = date;
        this.description = toString;
    }


    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Note(String id, String title, String description, Date date) {
        this.id = id;
        this.subject = title;
        this.date = date;
        this.description = description;
    }

    protected Note(Parcel in) {
        id = in.readString();
        subject = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
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

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(subject);
        dest.writeString(description);
        dest.writeLong(date.getTime());
    }

    public void update(Note newNote) {
        description = newNote.description;
        subject = newNote.subject;
    }
}
