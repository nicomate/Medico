package com.example.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.util.Log;

public class NewNoteActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteActivity";
    
    FloatingActionButton floatingActionButton;
    EditText etTitle, etNote;
    DatabaseReference reference;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        floatingActionButton = findViewById(R.id.createNote);
        etTitle = findViewById(R.id.edittext_title);
        etNote = findViewById(R.id.et_notedata);
        
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Notes");

        floatingActionButton.setOnClickListener(v -> {
            String note = etNote.getText().toString();
            String title = etTitle.getText().toString();
            
            createNote(fuser.getUid(), title, note);
            Log.d(TAG, "onCreate: createNote button");

        });
    }

    private void createNote(String author, String note_title, String note_data){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("author", author);
        hashMap.put("note_title", note_title);
        hashMap.put("note_data", note_data);

        reference.child("Notes").push().setValue(hashMap);
        Log.d(TAG, "createNote method: Push hashMap");
    }

}
