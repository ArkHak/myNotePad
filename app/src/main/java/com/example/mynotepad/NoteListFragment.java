package com.example.mynotepad;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteListFragment extends Fragment {
    private static final int MY_DEFAULT_DURATION = 1000;
    private static int position;
    private final String ACTION_DEL_NOTE = "ACTION_DEL_NOTE";
    private FloatingActionButton createButton;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private CollectionReference noteCollection;

    private final ArrayList<Note> noteList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        createButton = view.findViewById(R.id.add_note_btn);
        recyclerView = view.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.note_list_clear:
                noteList.clear();
                renderList(noteList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new NotesAdapter();
        adapter.setOnItemClickListener(getContract()::editNote);
        adapter.SetDeleteItemListener(getContract()::deleteNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        FirebaseApp.initializeApp(requireContext());
        FirebaseFirestore myDB = FirebaseFirestore.getInstance();

        noteCollection = myDB.collection("notes");


        renderList(noteList);
        recyclerView.setAdapter(adapter);
        createButton.setOnClickListener(v -> {
            getContract().createNewNote();
        });
    }


    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put("ID", note.id);
        answer.put("SUBJECT", note.subject);
        answer.put("DESCRIPTION", note.description);
        answer.put("DATE", note.date);
        return answer;
    }

    public void addNoteToBase(final Note note) {
        noteCollection.add(toDocument(note)).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
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
        addNoteToBase(newNote);
        noteList.add(newNote);
        renderList(noteList);
    }

    public void deleteNote(Note delNote) {
        Note sameNote = findNoteWithId(delNote.id);
        if (sameNote != null) {
            noteList.remove(sameNote);
        }
        renderList(noteList, ACTION_DEL_NOTE);
    }

    private Note findNoteWithId(String id) {
        position = -1;
        for (Note note : noteList) {
            position++;
            if (note.id.equals(id)) {
                return note;
            }
        }
        return null;
    }

    private void renderList(List<Note> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }

    private void renderList(List<Note> notes, String action) {
        adapter.setData(notes);
        switch (action) {
            case ACTION_DEL_NOTE:
                adapter.notifyItemRemoved(position);
        }

    }

//    NoteListFragment init(NotesSourceResponse notesSourceResponse);

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void createNewNote();

        void deleteNote(Note delNote);

        void editNote(Note note);
    }
}
