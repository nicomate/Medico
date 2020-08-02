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
import com.example.medico.ViewForumPostActivity;

import com.example.medico.model.ForumPost;

import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ViewHolder> {

    private static final String TAG = "ForumPostAdapter";

    private Context context;
    private List<ForumPost> forumPostList;
    private String imgURL;

    public ForumPostAdapter(Context context, List<ForumPost> forumPostList, String imgURL) {
        this.context = context;
        this.forumPostList = forumPostList;
        this.imgURL = imgURL;
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
        holder.postedBy.setText(forumPost.getPostedBy());

        if (imgURL.equals("default")) {
            Log.d(TAG, "onBindViewHolder: if img");
            holder.postedImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(imgURL).into(holder.postedImage);
        }

       holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ViewForumPostActivity.class);
            i.putExtra("forumPostid", forumPost.getPostId());
            context.startActivity(i);
            Log.d(TAG, "onBindViewHolder: to ViewForumPostActivity");
       });
        Log.d(TAG, "onBindViewHolder");
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
}

