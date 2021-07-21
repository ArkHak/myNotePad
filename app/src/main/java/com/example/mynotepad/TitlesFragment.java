package com.example.mynotepad;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TitlesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_titles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initList(view);
    }

    private void initList(View view) {
        Note note1 = new Note("Покупки", "Что-то купить", "21.07.2021");
        Note note2 = new Note("Учеба", "Сделай домашку!", "16.07.2021");
        Note note3 = new Note("Идеи", "А что если сделать так:", "01.07.2021");
        Note[] notes = {note1, note2, note3};

        LinearLayout linearLayout = (LinearLayout) view;

        for (Note note : notes) {
            TextInputLayout dateText = new TextInputLayout(getContext(), null, R.style.Widget_MaterialComponents_TextInputLayout_FilledBox);
            TextInputEditText titleText = new TextInputEditText(dateText.getContext());

            dateText.setHint(note.getDateCreate());
            titleText.setText(note.getTitle());
            titleText.setFocusable(false);
            titleText.setCursorVisible(false);
            dateText.addView(titleText);

            titleText.setOnClickListener(v -> {
                showDescription(note);
            });

            linearLayout.addView(dateText);
        }
    }

    private void showDescription(Note note) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);
        intent.putExtra(DescriptionFragment.ARG_NOTE, note);
        startActivity(intent);
    }

}