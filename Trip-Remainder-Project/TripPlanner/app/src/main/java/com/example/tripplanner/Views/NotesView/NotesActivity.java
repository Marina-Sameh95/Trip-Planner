package com.example.tripplanner.Views.NotesView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tripplanner.Adapters.NotesAdapter;
import com.example.tripplanner.Adapters.TripAdapter;
import com.example.tripplanner.Models.NotesModel.NotesContract;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Presenters.NotesPresenter.NotesPresenter;
import com.example.tripplanner.R;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity implements NotesContract.IView {

    RecyclerView recyclerView;
    public NotesAdapter arrayAdapter;
    RecyclerView.LayoutManager recyce;
    public ArrayList<String> notes=new ArrayList<>();
    public Trip trip;
    NotesContract.IPresenter presenter;

    Button addNewNoteBtn;
    Button saveChanges;
    public TextView noteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        addNewNoteBtn=findViewById(R.id.addNoteBtn);
        saveChanges=findViewById(R.id.saveNotesBtn);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleNotesChange(trip);
                finish();
            }
        });
        noteTextView=findViewById(R.id.noteEditText);
        addNewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note =noteTextView.getText().toString();
               presenter.addNewNote(note);
            }
        });
        Intent intent=getIntent();
        trip= (Trip) intent.getSerializableExtra("Trip");
        notes=trip.getNotes();
        if(notes.contains("There are no notes!"))
            notes.remove("There are no notes!");
        presenter=new NotesPresenter(this);

        recyclerView=findViewById(R.id.notesRecyclerView);
        recyce = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(recyce);
        arrayAdapter=new NotesAdapter(this,R.layout.note_row ,R.id.noteTextView,notes);
        recyclerView.setAdapter(arrayAdapter);
    }

}
