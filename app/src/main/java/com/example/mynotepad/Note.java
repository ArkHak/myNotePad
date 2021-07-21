package com.example.mynotepad;

public class Note {
    String title;
    String description;
    String dateCreate;

    public Note() {
    }

    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.dateCreate = date;
    }
}
