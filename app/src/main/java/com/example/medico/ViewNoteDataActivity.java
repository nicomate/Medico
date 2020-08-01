package com.example.medico;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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


public class ViewNoteDataActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteDataActivity";

    Intent intent;
    TextView notetitle, noteData;
    String noteid;
    DatabaseReference reference;
    FirebaseUser fuser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note_data);

        notetitle = findViewById(R.id.notetitle);
        noteData = findViewById(R.id.notedata);

        intent = getIntent();
        noteid = intent.getStringExtra("notemodelid");
        Log.d(TAG, "onCreate: noteid is " + noteid);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreate: fuser is " + fuser);
        reference = FirebaseDatabase.getInstance().getReference("Notes").child(noteid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String title = dataSnapshot.child("note_title").getValue().toString();
                String data = dataSnapshot.child("note_data").getValue().toString();
                Log.d(TAG, "onDataChange: title is " + title);
                Log.d(TAG, "onDataChange: data is " + data);
                noteData.setText(data);
                notetitle.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FloatingActionButton updateNote = findViewById(R.id.updateNote);
        updateNote.setOnClickListener(v -> {

            String newTitle = notetitle.getText().toString();
            reference.child("note_title").setValue(newTitle);
            Log.d(TAG, "onCreate: new title is: " + newTitle);

            String newData = noteData.getText().toString();
            reference.child("note_data").setValue(newData);
            Log.d(TAG, "onCreate: new data is: " + newData);

            finish();
        });

    }

}
