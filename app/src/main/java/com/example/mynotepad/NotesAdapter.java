package com.example.mynotepad;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnDeleteItemListener onDeleteItemListener;

    interface OnItemClickListener {
        void onItemClick(Note note);
    }

    interface OnDeleteItemListener {
        void onDeleteItem(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void SetDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
        this.onDeleteItemListener = onDeleteItemListener;
    }


    public void setData(List<Note> notes) {
        data = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, onItemClickListener, onDeleteItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
