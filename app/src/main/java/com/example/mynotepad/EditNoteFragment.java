package com.example.mynotepad;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditNoteFragment extends Fragment {
    private static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";

    private Button saveButton;
    private EditText subjectEditText;
    private EditText descriptionEditText;

    @Nullable
    private Note note = null;

    public static Fragment newInstance(@Nullable Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_EXTRA_KEY, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        saveButton = view.findViewById(R.id.save_note_btn);
        subjectEditText = view.findViewById(R.id.subject_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        note = getArguments().getParcelable(NOTE_EXTRA_KEY);
        getActivity().setTitle(note == null ? "Новая заметка" : note.subject);
        fillNote(note);

        saveButton.setOnClickListener(v -> {
            getContract().saveNote(gatherNote());
            getParentFragmentManager().popBackStack();
        });
    }

    private void fillNote(Note note) {
        if (note == null) return;
        subjectEditText.setText(note.subject);
        descriptionEditText.setText(note.description);
    }

    private Note gatherNote() {
        return new Note(
                note == null ? Note.generateId() : note.id,
                subjectEditText.getText().toString(),
                note == null ? Note.getCurrentDate() : note.date,
                descriptionEditText.getText().toString()
        );
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof EditNoteFragment.Contract)) {
            throw new IllegalStateException(getString(R.string.err_contract));
        }
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void saveNote(Note note);
    }
}
