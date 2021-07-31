package com.example.mynotepad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView dateTextView;
    private final TextView subjectTextView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTextView = itemView.findViewById(R.id.card_item_date_note);
        subjectTextView = itemView.findViewById(R.id.card_item_subject_note);
    }

    public void bind(Note note) {
        //todo current date/time
        dateTextView.setText("15.13.2021");
        subjectTextView.setText(note.subject);
    }
}
