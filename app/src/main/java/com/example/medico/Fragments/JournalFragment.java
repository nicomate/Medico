package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medico.Adapter.NoteItemsAdapter;
import com.example.medico.LandingPageActivity;
import com.example.medico.LoginActivity;
import com.example.medico.NewNoteActivity;
import com.example.medico.R;
import com.example.medico.model.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class JournalFragment extends Fragment {

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private RecyclerView recyclerView;
    private NoteItemsAdapter noteAdapter;

    List<NoteModel> noteModels = new ArrayList<>();


    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().onBackPressed();
            Toast.makeText(getActivity(), "You need to Log in to access your Journal!", Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton newNote = view.findViewById(R.id.newNote);
        newNote.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), NewNoteActivity.class);
            startActivity(i);
        });

        recyclerView = view.findViewById(R.id.notelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        noteModels = new ArrayList<>();

        ReadNotes();

        return view;
    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((LandingPageActivity) getActivity())
                .setActionBarTitle("Journal Notes");
    }


    private void ReadNotes() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("MyUsers");
    }

}
