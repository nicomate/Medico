package com.example.medico.Adapter;


import android.content.Context;
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
import com.example.medico.model.ForumComment;
import com.example.medico.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class ForumCommentAdapter extends RecyclerView.Adapter<ForumCommentAdapter.ViewHolder> {

    private static final String TAG = "ForumCommentAdapter";

    private Context context;
    private List<ForumComment> forumCommentList;

    public ForumCommentAdapter(ViewForumPostActivity context, List<ForumComment> forumCommentList) {
        this.context = context;
        this.forumCommentList = forumCommentList;
    }


    @NonNull
    @Override
    public ForumCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.forumcomment_item, parent,false);
        return new ForumCommentAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ForumCommentAdapter.ViewHolder holder, int position) {
        ForumComment forumComment = forumCommentList.get(position);
        holder.comment.setText(forumComment.getCommentText());
        String commentBy = forumCommentList.get(position).getCommentBy();

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("MyUsers").child(commentBy);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                String username = "By: " + user.getUsername();
                if (user.getImageURL().equals("default")) {
                    holder.commentImage.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(context).load(user.getImageURL()).into(holder.commentImage);
                }
                holder.commentBy.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return forumCommentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment, commentBy;
        public ImageView commentImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
            commentBy = itemView.findViewById(R.id.commentby);
            commentImage = itemView.findViewById(R.id.commentimage);
        }

    }

    public void removeItem(int position) {
        forumCommentList.remove(position);
        notifyItemRemoved(position);
    }

    public String getId(int position){
        return forumCommentList.get(position).getCommentid();
    }
}
