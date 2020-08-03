package com.example.medico.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medico.R;
import com.example.medico.ViewForumPostActivity;

import com.example.medico.model.ForumPost;
import com.example.medico.model.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ViewHolder> {

    private static final String TAG = "ForumPostAdapter";

    private Context context;
    private List<ForumPost> forumPostList;

    public ForumPostAdapter(Context context, List<ForumPost> forumPostList) {
        this.context = context;
        this.forumPostList = forumPostList;
    }

    @NonNull
    @Override
    public ForumPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forumpost_item, parent,false);
        return new ForumPostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumPostAdapter.ViewHolder holder, int position) {
        ForumPost forumPost = forumPostList.get(position);
        holder.postTitle.setText(forumPost.getPostQuestion());


        String postedby = forumPostList.get(position).getPostedBy();

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("MyUsers").child(postedby);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                String username = "By: " + user.getUsername();
                if (user.getImageURL().equals("default")) {
                    holder.postedImage.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(context).load(user.getImageURL()).into(holder.postedImage);
                }
                holder.postedBy.setText(username);

                holder.itemView.setOnClickListener(v -> {
                    Intent i = new Intent(context, ViewForumPostActivity.class);
                    i.putExtra("postid", forumPost.getPostId());
                    i.putExtra("postquestion", forumPost.getPostQuestion());
                    i.putExtra("postbody",forumPost.getPostBody());
                    i.putExtra("postbyimage", user.getImageURL());
                    i.putExtra("postedby", user.getUsername());
                    context.startActivity(i);
                    Log.d(TAG, "onBindViewHolder: to ViewForumPostActivity");
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
/*

*/


    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return forumPostList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView postTitle, postedBy;
        public ImageView postedImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder");
            postTitle = itemView.findViewById(R.id.posttitle);
            postedBy = itemView.findViewById(R.id.postedby);
            postedImage = itemView.findViewById(R.id.postedimage);
        }

    }

    public String getPostedBy(int position) {
        return forumPostList.get(position).getPostedBy();
    }

    public void removeItem(int position) {
        forumPostList.remove(position);
        notifyItemRemoved(position);
    }

    public String getId(int position){
        return forumPostList.get(position).getPostId();
    }
}

