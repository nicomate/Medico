package com.example.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.medico.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandingPage extends AppCompatActivity {

    private static final String TAG = "LandingPage";

    // Widgets
    Button registerBtn, loginButton, chatButton;

    // Firebase
    FirebaseUser firebaseUser;
    DatabaseReference myRef;

    /*@Override
   protected void onStart() {
       super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
     if (firebaseUser == null) {
       Intent i = new Intent(LandingPage.this, LoginActivity.class);
        startActivity(i);
        finish();

         Checking for users existance: Saving the current user
         TO DO!!! - Change the end??
       }
    }            */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "landingPage - onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //Initialising Widgets
        registerBtn = findViewById(R.id.buttonRegister);
        loginButton = findViewById(R.id.loginBtn);
        chatButton = findViewById(R.id.chatBtn);

       registerBtn.setOnClickListener(v -> {
           Intent intent = new Intent(LandingPage.this, RegisterActivity.class);
           startActivity(intent);
       });

       //TO DO - make login button invisible/not visible if user is logged in
       loginButton.setOnClickListener(v -> {
           Intent i = new Intent(LandingPage.this, LoginActivity.class);
           startActivity(i);
           Log.d(TAG, "landingPage - login button");
       });

       chatButton.setOnClickListener(v -> {
           Intent chat = new Intent(LandingPage.this, ChatActivity.class);
           startActivity(chat);
       });

        Log.d(TAG, "landingPage - work");


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //try catch to bypass null exception if user is not logged in
        try{
            myRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser
                    .getUid());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e){
        }

    }

    // Adding Logout Functionality
    // TO DO switch as a button on the landing page instead of menu
    // only show visible if user is logged in
    //remove menu.xml items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                //TO CHANGE THIS?? ~1:11:49
                startActivity(new Intent(LandingPage.this, LoginActivity.class));
                finish();
                return true;
        }
        return false;
    }

}
