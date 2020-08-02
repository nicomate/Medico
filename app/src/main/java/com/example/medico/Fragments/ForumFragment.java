package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medico.Adapter.ForumPostAdapter;
import com.example.medico.Adapter.NoteItemsAdapter;
import com.example.medico.LandingPageActivity;
import com.example.medico.LoginActivity;
import com.example.medico.NewForumPostActivity;
import com.example.medico.R;
import com.example.medico.model.ForumPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

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

        ReadPosts();
        Log.d(TAG, "onCreateView: ReadPosts method");

        FloatingActionButton newPost = view.findViewById(R.id.newPost);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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
        // TODO: Complete Method
    }

}
