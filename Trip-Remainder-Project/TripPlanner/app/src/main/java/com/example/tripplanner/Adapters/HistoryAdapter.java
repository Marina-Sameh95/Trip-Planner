package com.example.tripplanner.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.R;
import com.example.tripplanner.Views.HistoryView.History;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("trips");

    private History context;
    HistoryAdapter.OnHistoryListener onHistoryListener;
    private ArrayList<Trip> items=new ArrayList<>();
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row=null;
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row=layoutInflater.inflate(R.layout.history_row,parent,false);
        ViewHolder holder=new ViewHolder(row,onHistoryListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, final int position) {
        holder.date.setText(items.get(position).getTripDate());
        holder.time.setText(items.get(position).getTripTime());
        holder.name.setText(items.get(position).getTripName());
        holder.src.setText(items.get(position).getStartPlaceName());
        holder.dest.setText(items.get(position).getEndPlaceName());
        holder.type.setText(items.get(position).getStatus());

        holder.notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotesDialog(position);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(position);
            }
        });

    }

    public void showDeleteDialog(final int position){
//        final ArrayList<Boolean> deleteFlag=new ArrayList<>();
        AlertDialog.Builder myQuittingDialogBox = new AlertDialog.Builder(context);
        myQuittingDialogBox.setTitle("Delete")
                .setMessage("Are you sure you want to Delete this trip ?")
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        Trip curTrip=items.get(position);
                        curTrip.setStatus("Deleted");
                        myRef.child(items.get(position).getId()).setValue(curTrip);
                        items.remove(position);
//                        context.trips.remove(position);
                        context.arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
    public void showNotesDialog(int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Trip Notes").setIcon(R.drawable.ic_note_black_24dp);
        ArrayList<String> li=items.get(position).getNotes();
        if(li.size()==0)
            li.add("There were no notes!");
        String[] tripNotes= new String[li.size()];
        for (int i=0;i<li.size();i++){
            tripNotes[i]=li.get(i);
        }
         builder.setItems(tripNotes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView date;
        public TextView time;
        public TextView name;
        public TextView src;
        public TextView dest;
        public TextView type;
        public Button notesBtn;
        public Button deleteBtn;
        public View layout;

        public void showNotes(View view) {
//            AlertDialog.Builder builder=new AlertDialog.Builder(context);
//            builder.setTitle("Notes");
//            builder.setIcon(R.drawable.ic_note_black_24dp).create().show();
        }
        OnHistoryListener onHistoryListener;
        public ViewHolder(View v,OnHistoryListener onHistoryListener){
            super(v);
            layout=v;
            this.onHistoryListener=onHistoryListener;
            v.setOnClickListener(this);
            Button noteBtn=v.findViewById(R.id.noteIdHist);
            noteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNotes(v);
                }
            });
            date=v.findViewById(R.id.dateIdHist);
            time=v.findViewById(R.id.timeIdHist);
            name=v.findViewById(R.id.tripIdHist);
            src=v.findViewById(R.id.srcIdHist);
            dest=v.findViewById(R.id.destIdHist);
            type=v.findViewById(R.id.statusIdHist);
            notesBtn=v.findViewById(R.id.noteIdHist);
            deleteBtn=v.findViewById(R.id.deleteBtnIdHist);
        }

        @Override
        public void onClick(View v) {
            onHistoryListener.onHistoryClick(getAdapterPosition());
        }
    }
    public  interface OnHistoryListener{
        public void  onHistoryClick(int position);
    }

    public HistoryAdapter(@NonNull Context context, int resource, int textViewResourceId, List items, OnHistoryListener onHistoryListener) {
        this.context= (History) context;
        this.items= (ArrayList<Trip>) items;
        this.onHistoryListener=onHistoryListener;
    }
}
