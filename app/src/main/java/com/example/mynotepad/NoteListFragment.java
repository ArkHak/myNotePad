package com.example.mynotepad;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment {
    private FloatingActionButton createButton;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;

    private ArrayList<Note> noteList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        createButton = view.findViewById(R.id.add_note_btn);
        recyclerView = view.findViewById(R.id.recycler_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new NotesAdapter();
        adapter.setOnItemClickListener(getContract()::editNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        renderList(noteList);
        recyclerView.setAdapter(adapter);
        createButton.setOnClickListener(v -> {
            getContract().createNewNote();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new IllegalStateException(getString(R.string.err_contract));
        }
    }

    public void addNote(Note newNote) {
        Note sameNote = findNoteWithId(newNote.id);
        if (sameNote != null) {
            noteList.remove(sameNote);
        }
        noteList.add(newNote);
        renderList(noteList);
    }

    private Note findNoteWithId(String id) {
        for (Note note : noteList) {
            if (note.id.equals(id)) {
                return note;
            }
        }
        return null;
    }

    private void renderList(List<Note> notes) {
        adapter.setData(noteList);
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void createNewNote();
        void editNote(Note note);
    }
}
