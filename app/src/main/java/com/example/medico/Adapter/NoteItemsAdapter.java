package com.example.medico.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medico.R;
import com.example.medico.ViewNoteDataActivity;
import com.example.medico.model.NoteModel;
import android.util.Log;

import java.util.List;


public class NoteItemsAdapter extends RecyclerView.Adapter<NoteItemsAdapter.ViewHolder> {

    private static final String TAG = "NoteItemsAdapter";

    private Context context;
    private List<NoteModel> noteModelList;

    public NoteItemsAdapter(Context context, List<NoteModel> noteModelList) {
        this.context = context;
        this.noteModelList = noteModelList;
        Log.d(TAG, "NoteItemsAdapter: constructor");
    }


    @NonNull
    @Override
    public NoteItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row, parent,false);
        Log.d(TAG, "onCreateViewHolder");
        return new NoteItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteModel noteModel = noteModelList.get(position);
        holder.notetitle.setText(noteModel.getNote_title());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ViewNoteDataActivity.class);
            i.putExtra("notemodelid", noteModel.getId());
            context.startActivity(i);
            Log.d(TAG, "onBindViewHolder: to ViewNoteDataActivity");
        });
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return noteModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notetitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder");
            notetitle = itemView.findViewById(R.id.notetitle);
        }

    }

    public void removeItem(int position) {
        noteModelList.remove(position);
        notifyItemRemoved(position);
    }

    public String getId(int position){
        return noteModelList.get(position).getId();
    }
}