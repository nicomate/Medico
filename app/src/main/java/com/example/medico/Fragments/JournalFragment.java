package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medico.Adapter.NoteItemsAdapter;
import com.example.medico.Adapter.UserAdapter;
import com.example.medico.LandingPageActivity;
import com.example.medico.LoginActivity;
import com.example.medico.NewNoteActivity;
import com.example.medico.R;
import com.example.medico.model.NoteModel;
import com.example.medico.model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class JournalFragment extends Fragment {

    private static final String TAG = "JournalFragment";

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private RecyclerView recyclerView1;
    private NoteItemsAdapter noteAdapter;

    List<NoteModel> noteModels;


    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: oncreate");

        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        Log.d(TAG, "onCreateView: inflateview");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().onBackPressed();
            Toast.makeText(getActivity(), "You need to Log in to access your Journal!", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "onCreateView: auth");




        recyclerView1 = view.findViewById(R.id.notelist);
        Log.d(TAG, "onCreateView: recyclerview");
        recyclerView1.setHasFixedSize(true);
        Log.d(TAG, "onCreateView: recyclerview sethasfixedsize");
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d(TAG, "onCreateView: recyclerview setlayoutmanager");


        noteModels = new ArrayList<>();

        ReadNotes();
        Log.d(TAG, "onCreateView:  readnotes method");

        FloatingActionButton newNote = view.findViewById(R.id.newNote);
        newNote.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), NewNoteActivity.class);
            startActivity(i);
            Log.d(TAG, "onCreateView: new Note");

        });
        return view;
    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((LandingPageActivity) getActivity())
                .setActionBarTitle("Journal Notes");
        Log.d(TAG, "onResume: set actionbar title");
    }


    private void ReadNotes() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Notes");
        Log.d(TAG, "ReadNotes:  get ref, fuser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noteModels.clear();
                Log.d(TAG, "onDataChange: clear");

                try {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: for loop");
                        NoteModel noteModel = snapshot.getValue(NoteModel.class);
                        Log.d(TAG, "onDataChange: forloop - get Value");
                        try {
                            if(noteModel.getAuthor().equals(firebaseUser.getUid())){
                                Log.d(TAG, "onDataChange: if statement");
                                noteModels.add(noteModel);
                                Log.d(TAG, "onDataChange: if statement addnotemodel");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "onDataChange: if statement error");
                        }

                        noteAdapter =  new NoteItemsAdapter(getContext(),noteModels);
                        Log.d(TAG, "onDataChange: new adapter");
                        recyclerView1.setAdapter(noteAdapter);
                        Log.d(TAG, "onDataChange: set adapter");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onDataChange: for loop catch");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");
            }
        });

    }

}
