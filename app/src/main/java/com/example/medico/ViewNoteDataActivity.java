package com.example.medico;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.Constants;
import com.example.medico.R;


public class ViewNoteDataActivity extends Fragment {

    String id, notetext, create_time;
    TextView note_data, note_time;

    public ViewNoteDataActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_note_data, container, false);

        Bundle mbundle = new Bundle();
        mbundle = getArguments();
        id = mbundle.getString(Constants.id);
        notetext = mbundle.getString(Constants.note_text);
        create_time = mbundle.getString(Constants.create_time);

        note_data = view.findViewById(R.id.note_data);
        note_time = view.findViewById(R.id.create_time);

        note_data.setText(notetext);
        note_time.setText(create_time);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
