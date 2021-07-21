package com.example.mynotepad;

public class Note {
    String title;
    String description;
    String dateCreate;

    public Note() {
    }

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
}
