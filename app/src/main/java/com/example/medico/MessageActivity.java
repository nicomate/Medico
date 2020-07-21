package com.example.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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


public class MessageActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;

    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.usernamey);

       //Toolbar:
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");
       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View v) {
      //          finish();
       //     }
      //  });

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

    }

    private void setSupportActionBar(Toolbar toolbar) {


    }
}
