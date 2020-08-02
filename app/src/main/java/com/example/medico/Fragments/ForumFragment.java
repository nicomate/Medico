package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.medico.Adapter.ForumPostAdapter;
import com.example.medico.Adapter.NoteItemsAdapter;
import com.example.medico.LandingPageActivity;
import com.example.medico.LoginActivity;
import com.example.medico.MessageActivity;
import com.example.medico.NewForumPostActivity;
import com.example.medico.R;
import com.example.medico.model.ForumPost;
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

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class ForumFragment extends Fragment {

    private static final String TAG = "ForumFragment";

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private RecyclerView rv_forum;
    private ForumPostAdapter forumPostAdapter;

    List<ForumPost> forumPostList;
    String fuser;
    ImageView postedImage;

    public ForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        Log.d(TAG, "onCreateView: inflate view");



        rv_forum = view.findViewById(R.id.rv_forum);
        rv_forum.setHasFixedSize(true);
        rv_forum.setLayoutManager(new LinearLayoutManager(getContext()));

        forumPostList = new ArrayList<>();
        postedImage = view.findViewById(R.id.postedimage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

  /*      fuser = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(fuser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                if (user.getImageURL().equals("default")) {
                    postedImage.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getActivity())
                            .load(user.getImageURL())
                            .into(postedImage);
                }


            }



            @Override

            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
     */ ReadPosts();
        Log.d(TAG, "onCreateView: ReadPosts method");

        FloatingActionButton newPost = view.findViewById(R.id.newPost);

        if (firebaseUser == null) {
            newPost.setVisibility(View.GONE);
        } else {
            newPost.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), NewForumPostActivity.class);
                startActivity(i);
            });
        }

        return view;
    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((LandingPageActivity) getActivity())
                .setActionBarTitle("Forum");

    }

    private void ReadPosts() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String fuser = firebaseUser.getUid();


        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                forumPostList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ForumPost forumPost = snapshot.getValue(ForumPost.class);
                        forumPostList.add(forumPost);
                    }
                    forumPostAdapter =  new ForumPostAdapter(getContext(),forumPostList);
                    rv_forum.setAdapter(forumPostAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");
            }
        });

    }

}
