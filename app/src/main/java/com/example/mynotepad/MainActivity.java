package com.example.mynotepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements NoteListFragment.Contract,
        EditNoteFragment.Contract {
    private static final String NOTES_LIST_FRAG_TEG = "NOTES_LIST_FRAG_TEG";
    private boolean isLandscapeMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscapeMode = findViewById(R.id.optional_fragment_container) != null;
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
                .add(R.id.main_fragment_container, new NoteListFragment(), NOTES_LIST_FRAG_TEG)
                .commit();
    }

    private void showEditNote() {
        showEditNote(null);
    }

    private void showEditNote(@Nullable Note note) {
        if (!isLandscapeMode) {
            setTitle(note == null ? "Новая заметка" : note.subject);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!isLandscapeMode) {
            transaction.addToBackStack(null);
        }

        transaction.replace(isLandscapeMode ? R.id.optional_fragment_container : R.id.main_fragment_container,
                EditNoteFragment.newInstance(note))
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
        setTitle(R.string.app_name);
        NoteListFragment noteListFragment =
                (NoteListFragment) getSupportFragmentManager().findFragmentByTag(NOTES_LIST_FRAG_TEG);
        assert noteListFragment != null;
        noteListFragment.addNote(note);
    }
}