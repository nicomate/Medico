package com.example.medico;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.medico.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Information extends Fragment {

    private static final String TAG = "Information";
    // Widgets
    Button registerBtn, loginButton, chatButton;

    // Firebase
    FirebaseUser firebaseUser;
    DatabaseReference myRef;

    public Information() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        //Initialising Widgets
        registerBtn = view.findViewById(R.id.buttonRegister);
        loginButton = view.findViewById(R.id.loginBtn);
        chatButton = view.findViewById(R.id.chatBtn);

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        //TODO - make login button invisible/not visible if user is logged in
        loginButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            Log.d(TAG, "landingPage - login button");
        });

        chatButton.setOnClickListener(v -> {
            Intent chat = new Intent(getActivity(), ChatActivity.class);
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
        } catch (Exception e) {

        }


    return view;
    }
}
