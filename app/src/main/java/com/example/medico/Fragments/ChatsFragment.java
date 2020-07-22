package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medico.Adapter.UserAdapter;
import com.example.medico.LoginActivity;
import com.example.medico.R;
import com.example.medico.model.Chatlist;
import com.example.medico.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private UserAdapter userAdapter;
    private List<Users> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<Chatlist> usersList;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chats,
                container,
                false);


        recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (fuser == null) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().onBackPressed();
            Toast.makeText(getActivity(), "You need to Log in to access your Chat!", Toast.LENGTH_SHORT).show();
        }

        usersList = new ArrayList<>();

        try {
            reference = FirebaseDatabase.getInstance().getReference("ChatList")
                    .child(fuser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    usersList.clear();

                    //Loop for all users
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Chatlist chatlist = snapshot.getValue(Chatlist.class);
                        usersList.add(chatlist);
                    }

                    chatList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }



        return view;
    }

    private void chatList() {

        // getting all previous chats
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Users user = snapshot.getValue(Users.class);
                    for (Chatlist chatlist : usersList){

                        if(user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
