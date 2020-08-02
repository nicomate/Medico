package com.example.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class NewForumPostActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    EditText etQuestion, etText;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forum_post);

        floatingActionButton = findViewById(R.id.createPost);
        etQuestion = findViewById(R.id.et_postquestion);
        etText = findViewById(R.id.et_postText);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Posts");

        floatingActionButton.setOnClickListener(v -> {
            String question = etQuestion.getText().toString();
            String text = etText.getText().toString();
            createPost(firebaseUser.getUid(), question, text);
            finish();
        });
    }

    private void createPost(String postedBy, String postQuestion, String postText){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = UUID.randomUUID().toString();
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("postId", key);
        hashMap.put("postedBy", postedBy);
        hashMap.put("postQuestion", postQuestion);
        hashMap.put("postBody", postText);

        databaseReference.child("Posts").child(key).setValue(hashMap);
    }


}
