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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewNoteActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    EditText etTitle, etNote;
    DatabaseReference reference;
    FirebaseUser fuser;
    SimpleDateFormat format;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        floatingActionButton = findViewById(R.id.createNote);
        etTitle = findViewById(R.id.edittext_title);
        etNote = findViewById(R.id.et_notedata);

        format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = etNote.getText().toString();
                createNote();

            }
        });
    }

    private void createNote(String id, String author, String note_title, String note_data, String created_at){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("author", author);
        hashMap.put("note_title", note_title);
        hashMap.put("note_data", note_data);
        hashMap.put("created_at", created_at);

        reference.child("Notes").push().setValue(hashMap);


    }

}
