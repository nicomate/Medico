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

import java.util.List;


public class NoteItemsAdapter extends RecyclerView.Adapter<NoteItemsAdapter.ViewHolder> {

    private Context context;
    private List<NoteModel> noteModelList;

    public NoteItemsAdapter(Context context, List<NoteModel> noteModelList) {
        this.context = context;
        this.noteModelList = noteModelList;
    }


    @NonNull
    @Override
    public NoteItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row, parent);

        return new NoteItemsAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemsAdapter.ViewHolder holder, int position) {
        NoteModel noteModel = noteModelList.get(position);
        holder.notetitle.setText(noteModel.getNote_title());
        holder.create_time.setText(noteModel.getCreated_at());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewNoteDataActivity.class);
                i.putExtra("notemodelid", noteModel.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notetitle;
        public TextView create_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notetitle = itemView.findViewById(R.id.notetitle);
            create_time = itemView.findViewById(R.id.create_time);
        }
    }


}