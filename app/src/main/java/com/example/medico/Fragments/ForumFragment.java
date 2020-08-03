package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medico.Adapter.ForumPostAdapter;

import com.example.medico.LandingPageActivity;

import com.example.medico.NewForumPostActivity;
import com.example.medico.R;
import com.example.medico.SwipeToDeleteCallback;
import com.example.medico.model.ForumPost;

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

    private List<ForumPost> forumPostList;

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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        forumPostAdapter =  new ForumPostAdapter(getContext(),forumPostList);
        rv_forum.setAdapter(forumPostAdapter);
        ReadPosts();
        Log.d(TAG, "onCreateView: ReadPosts method");

        FloatingActionButton newPost = view.findViewById(R.id.newPost);

        if (firebaseUser == null) {
            newPost.setVisibility(View.GONE);
        } else {
            newPost.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), NewForumPostActivity.class);
                startActivity(i);
            });
            SwipeToDelete();
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
                    forumPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");
            }
        });

    }

    private void SwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                try{
                    if (forumPostAdapter.getPostedBy(viewHolder.getAdapterPosition()).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        //do nothing;
                    }
                    else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.getMovementFlags(recyclerView, viewHolder);
            }


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                reference = FirebaseDatabase.getInstance()
                        .getReference("Posts");
                reference.child(String.valueOf(forumPostAdapter.getId(position))).setValue(null);

                forumPostAdapter.removeItem(position);
                forumPostAdapter.notifyDataSetChanged();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv_forum);
    }



}



