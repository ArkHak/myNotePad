package com.example.mynotepad;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteListFragment extends Fragment {
    private static final int MY_DEFAULT_DURATION = 1000;
    private static int positionToList;
    private boolean SET_UPDATE_NOTE = false;
    private final String ACTION_DEL_NOTE = "ACTION_DEL_NOTE";
    private FloatingActionButton createButton;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private CollectionReference noteCollection;
    private FirebaseFirestore myDB;

    private static final String TAG = "[NoteSourceFirebaseImpl]";

    private final ArrayList<Note> noteList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        createButton = view.findViewById(R.id.add_note_btn);
        recyclerView = view.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);
        FirebaseApp.initializeApp(requireContext());
        myDB = FirebaseFirestore.getInstance();
        noteCollection = myDB.collection("notes");

        if (!SET_UPDATE_NOTE) {
            initListBD(noteList);
            SET_UPDATE_NOTE = false;
        }

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
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(R.string.warning)
                        .setMessage(R.string.clear_list_all)
                        .setIcon(R.drawable.ic_warning)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes_clear_list,
                                (dialog, which) -> {
                                    for (Note note : noteList) {
                                        deleteFromBD(note);
                                    }
                                    noteList.clear();
                                    renderList(noteList);
                                })
                        .setNegativeButton(R.string.no_clear_list, null);
                AlertDialog alertClearList = builder.create();
                alertClearList.show();
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
            updateNoteFromBD(newNote);
            SET_UPDATE_NOTE = true;
            sameNote.update(newNote);
        } else {
            addNoteToBD(newNote);
            noteList.add(newNote);
        }
        renderList(noteList);
    }

    public void deleteNote(Note delNote) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.warning)
                .setMessage(R.string.delete_note)
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_delete_note,
                        (dialog, which) -> {
                            Note sameNote = findNoteWithId(delNote.id);
                            if (sameNote != null) {
                                noteList.remove(sameNote);
                            }
                            deleteFromBD(sameNote);
                            renderList(noteList, ACTION_DEL_NOTE);
                        })
                .setNegativeButton(R.string.no_clear_list, null);
        AlertDialog alertClearList = builder.create();
        alertClearList.show();
    }

    private Note findNoteWithId(String id) {
        positionToList = -1;
        for (Note note : noteList) {
            positionToList++;
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
                adapter.notifyItemRemoved(positionToList);
        }

    }

    public void initListBD(ArrayList<Note> noteList) {
        noteList.clear();
        noteCollection.orderBy(NoteMapping.Fields.DATE,
                Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            Note note = NoteMapping.toNote(doc);
                            noteList.add(note);
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public void addNoteToBD(final Note note) {
        noteCollection.add(NoteMapping.toDocument(note)).
                addOnSuccessListener(documentReference ->
                        Log.d("BD", "Сделана новая запись в БД: " + note.getSubject()));
        adapter.notifyDataSetChanged();
    }

    private void updateNoteFromBD(Note sameNote) {
        noteCollection
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            if (doc.get("id").equals(sameNote.id)) {
                                String id = document.getId();
                                noteCollection.document(id).set(NoteMapping.toDocument(sameNote));
                            }
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w(TAG, "Error update documents.", task.getException());
                    }
                });

    }

    private void deleteFromBD(Note sameNote) {
        noteCollection
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            if (doc.get("id").equals(sameNote.id)) {
                                String id = document.getId();
                                noteCollection.document(id).delete();
                            }
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w(TAG, "Error remove documents.", task.getException());
                    }
                });
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {


        void createNewNote();

        void deleteNote(Note delNote);

        void editNote(Note note);
    }
}
