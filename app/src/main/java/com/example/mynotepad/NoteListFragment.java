package com.example.mynotepad;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteListFragment extends Fragment {
    private FloatingActionButton createButton;
    private LinearLayout listLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        createButton = view.findViewById(R.id.add_note_btn);
        listLayout = view.findViewById(R.id.list_linear_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void createNewNote();
    }
}
