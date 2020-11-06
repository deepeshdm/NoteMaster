package com.deepesh.notemaster;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = NoteEntity.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    private static NoteDatabase instance;

    public static   NoteDatabase getDatabaseInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"notes_databaseFile").build();
        }
        return instance;
    }

}
