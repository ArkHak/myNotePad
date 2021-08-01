package com.example.mynotepad;

import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView dateTextView;
    private final TextView subjectTextView;
    private Note note;
    private final CardView cardView;

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
        cardView = (CardView) itemView;
        dateTextView = itemView.findViewById(R.id.card_item_date_note);
        subjectTextView = itemView.findViewById(R.id.card_item_subject_note);
        if (clickListener != null) {
            cardView.setOnClickListener(v -> clickListener.onItemClick(note));
        }
    }

    public void bind(Note note) {
        this.note = note;
        //todo current date/time
        dateTextView.setText(note.date.toString());
        subjectTextView.setText(note.subject);
    }
}
