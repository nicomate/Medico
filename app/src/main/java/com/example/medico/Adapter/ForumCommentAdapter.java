package com.example.medico.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.medico.model.ForumComment;


import java.util.List;

public class ForumCommentAdapter extends RecyclerView.Adapter<ForumCommentAdapter.ViewHolder> {

    private static final String TAG = "ForumCommentAdapter";

    private Context context;
    private List<ForumComment> forumCommentList;
    private String imgURL;

    public ForumCommentAdapter(Context context, List<ForumComment> forumCommentList, String imgURL) {
        this.context = context;
        this.forumCommentList = forumCommentList;
        this.imgURL = imgURL;
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
        holder.commentBy.setText(forumComment.getCommentBy());

        if (imgURL.equals("default")) {
            Log.d(TAG, "onBindViewHolder: if img");
            holder.commentImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(imgURL).into(holder.commentImage);
        }

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
}
