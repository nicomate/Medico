package com.example.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.medico.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MessageActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;

    RecyclerView recyclerView;
    EditText msg_editText;
    ImageButton sendBtn;


    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Widget
        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.usernamey);

        sendBtn = findViewById(R.id.btn_send);
        msg_editText = findViewById(R.id.text_send);


        intent = getIntent();
        String userid = intent.getStringExtra("userid");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                username.setText(user.getUsername());

                if (user.getImageURL().equals("default")) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(MessageActivity.this)
                            .load(user.getImageURL())
                            .into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msg_editText.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(), userid, msg);
                }else {
                    Toast.makeText(MessageActivity.this,"Please send a non empty message.", Toast.LENGTH_SHORT).show();
                }

                msg_editText.setText("");
            }
        });


    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", message);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }


    private void setSupportActionBar(Toolbar toolbar) {


    }
}
