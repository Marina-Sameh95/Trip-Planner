package com.example.tripplanner.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.R;
import com.example.tripplanner.Views.NotesView.NotesActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> items=new ArrayList<>();
    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row=null;
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row=layoutInflater.inflate(R.layout.note_row,parent,false);
        NotesAdapter.ViewHolder holder=new NotesAdapter.ViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, final int position) {
        holder.note.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView note;
        public View layout;

        public ViewHolder(View v){
            super(v);
            layout=v;
            note=v.findViewById(R.id.noteTextView);
        }

    }

    public NotesAdapter(@NonNull Context context, int resource, int textViewResourceId, List items) {
        this.context=context;
        this.items= (ArrayList<String>) items;
    }
}
