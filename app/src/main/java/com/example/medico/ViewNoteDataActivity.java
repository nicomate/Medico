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


    private static final String TAG = "NewNoteDataActivity";

    String id, notetext;
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

        note_data = view.findViewById(R.id.notedata);


        note_data.setText(notetext);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
