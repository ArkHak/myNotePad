package com.example.mynotepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements NoteListFragment.Contract,
        EditNoteFragment.Contract {
    private static final String NOTES_LIST_FRAG_TEG = "NOTES_LIST_FRAG_TEG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToolbar();
        showNoteList();

    }

    private void showToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void showNoteList() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new NoteListFragment(), NOTES_LIST_FRAG_TEG)
                .commit();
    }

    private void showEditNote() {
        showEditNote(null);
    }

    private void showEditNote(@Nullable Note note) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, EditNoteFragment.newInstance(note))
                .commit();
    }

    @Override
    public void createNewNote() {
        showEditNote();
    }

    @Override
    public void editNote(Note note) {
        showEditNote(note);
    }

    @Override
    public void saveNote(Note note) {
        NoteListFragment noteListFragment =
                (NoteListFragment) getSupportFragmentManager().findFragmentByTag(NOTES_LIST_FRAG_TEG);
        noteListFragment.addNote(note);
    }
}