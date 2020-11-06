package com.deepesh.notemaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 120;
    public static final int UPDATE_REQUEST_CODE = 130;
    Toolbar toolbar1;
    RecyclerView notes_recyclerView;
    NoteViewModel noteViewModel;
   private NoteAdapter noteAdapter;
   FloatingActionButton floatingActionButton1;
    // constants same as in other activity
    public static final String DATE_TIME_EXTRA ="Date_Time_extra";
    public static final String DATA_EXTRA ="Data_extra";
    public static final String ID_EXTRA ="id_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       init();

        noteViewModel.getAllUserNotes().observe(this, noteEntities -> {
            noteAdapter.setAllnotes(noteEntities);
            noteAdapter.notifyDataSetChanged();
            Log.d("TAG", "onCreate: DATA SET CHANGED INSIDE RECYCLERVIEW");
        });


        // Adds Swipable Functionality to Recyclerview
        ItemTouchHelper touchHelper = new ItemTouchHelper
                (new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // Gives Position on where swipe is taking place & deletes it.
                int viewPosition = viewHolder.getAdapterPosition();
                noteViewModel.delete(noteAdapter.getNoteAt(viewPosition));
                Toast.makeText(MainActivity.this, "NOTE DELETED", Toast.LENGTH_SHORT).show();
            }
        });
        touchHelper.attachToRecyclerView(notes_recyclerView);;

        // Custom OnClick for Note in RecyclerView
        noteAdapter.setOnClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(NoteEntity noteEntity) {

                Intent intent = new Intent(MainActivity.this,NewNote.class);
                intent.putExtra(DATA_EXTRA,noteEntity.getData());
                intent.putExtra(DATE_TIME_EXTRA,noteEntity.getDateAndtime());
                intent.putExtra(ID_EXTRA,noteEntity.getId());

                startActivityForResult(intent,UPDATE_REQUEST_CODE);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delete_all_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.delete_all_option){
            // Deletes All Notes
            noteViewModel.deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_REQUEST_CODE && resultCode==RESULT_OK){
            assert data != null;
            String userData = data.getStringExtra(NewNote.USER_DATA_KEY);
            NoteEntity newNote = new NoteEntity(getDataAndTime(),userData);
            //Insert new Note
            noteViewModel.insert(newNote);
        }else if (requestCode==UPDATE_REQUEST_CODE && resultCode==RESULT_OK){

            int id = data.getIntExtra(ID_EXTRA,-1);
            if (id==-1){
                Toast.makeText(this, "Note Can't be Updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String userData = data.getStringExtra(NewNote.USER_DATA_KEY);
            NoteEntity newNote = new NoteEntity(getDataAndTime(),userData);
            newNote.setId(id);
            noteViewModel.update(newNote);
        }
    }

    public String getDataAndTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd E  HH:mm a");
        return sdf.format(new Date());
    }


    void init(){
        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        noteAdapter = new NoteAdapter();
        notes_recyclerView = findViewById(R.id.notes_recyclerview);
        notes_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notes_recyclerView.setAdapter(noteAdapter);
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        noteViewModel =
                new ViewModelProvider(this,new NoteViewModelFactory(this.getApplication()))
                .get(NoteViewModel.class);

    }

    // used by floatingactionBar
    public void createNote(View view) {
        Intent intent = new Intent(MainActivity.this,NewNote.class);
        startActivityForResult(intent,ADD_REQUEST_CODE);
    }


}















