package com.example.mynotepad;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class DescriptionFragment extends Fragment {

    private Note note;
    public static final String ARG_NOTE = "note";
    private int mYear, mMonth, mDay;

    public DescriptionFragment() {
    }

    interface Controller {
        void changeTitleToolbar(String titleToolbar);
    }

    public static DescriptionFragment newInstance(Note note) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDescription(view);
    }

    private void initDescription(View view) {
        TextView dateTextView = view.findViewById(R.id.date_text_view);
        TextView titleTextView = view.findViewById(R.id.title_text_view);
        TextView descriptionTextView = view.findViewById(R.id.description_text_view);

        dateTextView.setText(note.getDateCreate());
        dateTextView.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        dateTextView.setText(editTextDateParam);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        titleTextView.setText(note.getTitle());
        descriptionTextView.setText(note.getDescription());


        boolean isLandscape = getResources().getConfiguration().
                orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (!isLandscape){
            ((Controller) getActivity()).changeTitleToolbar(note.getTitle());
        }
    }
}