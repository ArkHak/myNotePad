package com.example.mynotepad;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView dateTextView;
    private final TextView subjectTextView;
    private Note note;
    private final CardView cardView;

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener,
                          NotesAdapter.OnDeleteItemListener deleteListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
        cardView = (CardView) itemView;
        dateTextView = itemView.findViewById(R.id.card_item_date_note);
        subjectTextView = itemView.findViewById(R.id.card_item_subject_note);
        if (clickListener != null) {
            cardView.setOnClickListener(v -> clickListener.onItemClick(note));
        }

        cardView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(parent.getContext(), cardView);
            popupMenu.inflate(R.menu.note_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_update:
                        if (clickListener != null) {
                            clickListener.onItemClick(note);
                        }
                        return true;
                    case R.id.action_delete:
                        if (deleteListener != null) {
                            deleteListener.onDeleteItem(note);
                        }
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    public void bind(Note note) {
        this.note = note;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateTextView.setText(df.format(note.date));
        subjectTextView.setText(note.subject);
    }
}
