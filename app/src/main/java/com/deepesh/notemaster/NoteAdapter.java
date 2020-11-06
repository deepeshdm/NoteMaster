package com.deepesh.notemaster;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public static List<NoteEntity> allnotes = new ArrayList<>();
    public static final String DATE_TIME_EXTRA ="Date_Time_extra";
    public static final String DATA_EXTRA ="Data_extra";
    public static final String ID_EXTRA ="id_extra";
    public static OnItemClickListener mListener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        NoteEntity currentNote = allnotes.get(position);
        holder.data_textview.setText(currentNote.getData());
        holder.dateAndTimeTextview.setText(currentNote.getDateAndtime());
    }

    @Override
    public int getItemCount() {
        return allnotes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView data_textview;
        TextView dateAndTimeTextview;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            data_textview = itemView.findViewById(R.id.DataTextview_singlerow);
            dateAndTimeTextview = itemView.findViewById(R.id.DateAndTimeTextview_singlerow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener!=null && position!= RecyclerView.NO_POSITION){
                        mListener.OnItemClick(allnotes.get(position));
                    }
                }
            });
        }

    }

    public void setAllnotes(List<NoteEntity> allnotes){
        NoteAdapter.allnotes =allnotes;
    }

    // Returns Note at particular Position to delete it.
    public NoteEntity getNoteAt(int position){
        return allnotes.get(position);
    }


// RECYCLERVIEW DOESNT COME WITH CLICKLISTENER SO WE CREATE ONE.
// Used by RecyclerView OnClick method.
    public interface OnItemClickListener{
        void OnItemClick(NoteEntity noteEntity);
    }

    public void setOnClickListener(OnItemClickListener listener){
         mListener = listener;
    }

}


























