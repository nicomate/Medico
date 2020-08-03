package com.example.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medico.Adapter.ForumCommentAdapter;

import com.example.medico.Adapter.ForumPostAdapter;
import com.example.medico.model.ForumComment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ViewForumPostActivity extends AppCompatActivity {

    private static final String TAG = "ViewForumPostActivity";

    Intent intent;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextView postq, postbody, postby;
    ImageView postbyimage;
    String postid, postquestion, posttext, author, authorimage;

    RecyclerView rv_comments;

    List<ForumComment> forumCommentList;
    ForumCommentAdapter forumCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forum_post);

        postq = findViewById(R.id. viewpostq);
        postbody = findViewById(R.id.viewpostbody);
        postby = findViewById(R.id.viewpostby);
        postbyimage = findViewById(R.id.viewpostbyimage);

        intent = getIntent();
        postid = intent.getStringExtra("postid");
        Log.d(TAG, "onCreate: postid is: " + postid);

        postquestion = intent.getStringExtra("postquestion");
        Log.d(TAG, "onCreate: postquestion is: " + postquestion);

        posttext = intent.getStringExtra("postbody");
        Log.d(TAG, "onCreate: posttext is: " + posttext);

        author = intent.getStringExtra("postedby");
        Log.d(TAG, "onCreate: author is: " + author);

        authorimage = intent.getStringExtra("postbyimage");
        Log.d(TAG, "onCreate: authorimgurl is: " + authorimage);

        postq.setText(postquestion);
        postbody.setText(posttext);
        postby.setText("By: " + author);
        if (authorimage.equals("default")) {
            postbyimage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(this).load(authorimage).into(postbyimage);
        }

        rv_comments = findViewById(R.id.viewpostrv);
        rv_comments.setHasFixedSize(true);
        rv_comments.setLayoutManager(new LinearLayoutManager(this));

        forumCommentList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreate: firebaseuser is " + firebaseUser);
        forumCommentAdapter =  new ForumCommentAdapter(ViewForumPostActivity.this,forumCommentList);
        rv_comments.setAdapter(forumCommentAdapter);


        FloatingActionButton newComment = findViewById(R.id.createComment);
        if (firebaseUser == null) {
            newComment.setVisibility(View.GONE);
        } else {
            newComment.setOnClickListener(v -> {
               onButtonShowPopupWindowClick(v);
            });
            SwipeToDelete();
        }
        ReadComments();
    }

    private void SwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(ViewForumPostActivity.this) {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            try{
                if (forumCommentAdapter.getAdapterCommentBy(viewHolder.getAdapterPosition()).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
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
            Log.d(TAG, "onSwiped: 1 " + position);
            reference = FirebaseDatabase.getInstance()
                    .getReference("Posts").child(postid).child("Comments");

            //TODO: delete comment from database - couldn't figure it out
            Log.d(TAG, "onSwiped: 2 " + forumCommentList.get(position));
            Log.d(TAG, "onSwiped: 3 " + forumCommentList.get(position).getCommentid());
            Log.d(TAG, "onSwiped: 4 " + forumCommentList.get(1).getCommentid());
            Log.d(TAG, "onSwiped: 5 " + viewHolder.getItemId());
            Log.d(TAG, "onSwiped: 6 " + viewHolder.getAdapterPosition());
            Log.d(TAG, "onSwiped: 6 " + forumCommentList.get(position).getCommentBy());
            String object = String.valueOf(forumCommentAdapter.getId(position));
            Log.d(TAG, "onSwiped: 7 " + object);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                  //  String id = forumCommentAdapter.getId(position);
                    Log.d(TAG, "onSwiped: 8 " + forumCommentList.get(position).getCommentid());
                    Log.d(TAG, "onSwiped: 9" + forumCommentList.get(position).getCommentBy());
                    Log.d(TAG, "onSwiped: 10 " + forumCommentAdapter.getId(position));
                    Log.d(TAG, "onSwiped: 11 " + forumCommentList.get(1).getCommentid());
                  //  reference.child(id).removeValue();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            forumCommentAdapter.removeItem(position);
            forumCommentAdapter.notifyDataSetChanged();

        }
    };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv_comments);
    }

    private void ReadComments() {
        DatabaseReference comments = FirebaseDatabase.getInstance()
                .getReference("Posts").child(postid).child("Comments");

        comments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                forumCommentList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ForumComment forumComment = snapshot.getValue(ForumComment.class);
                    forumCommentList.add(forumComment);
                }
                forumCommentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");
            }
        });
    }

    public void onButtonShowPopupWindowClick(View v) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.commentpopup, null);


        // create the popup window
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.update();

        EditText comment = popupView.findViewById(R.id.newComment);
        TextView postComment = popupView.findViewById(R.id.postcomment);

        postComment.setOnClickListener(v1 -> {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            String key = UUID.randomUUID().toString();

            String newComment = comment.getText().toString();
            String fuser = firebaseUser.getUid();
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("commentId", key);
            hashMap.put("commentText", newComment);
            hashMap.put("commentBy", fuser);

            databaseReference.child("Posts").child(postid).child("Comments").child(key).setValue(hashMap);

            popupWindow.dismiss();
            forumCommentAdapter.notifyDataSetChanged();
        });
    }
}
