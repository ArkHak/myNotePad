package com.example.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotes();

    }

    private void createNotes() {
        Note note1 = new Note("Покупки","Что-то купить","01.07.2021");
        Note note2 = new Note("Учеба","Сделай домашку!","21.07.2021");
        Note note3 = new Note("Идеи","А что если сделать так:","13.07.2021");
    }
}