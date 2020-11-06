package com.deepesh.notemaster;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {


   private NoteDao noteDao;

    private LiveData<List<NoteEntity>> allNotes;

   NoteRepository(Application application){
       NoteDatabase noteDatabase=NoteDatabase.getDatabaseInstance(application);
       noteDao = noteDatabase.getNoteDao();
       //Room Automatically Gets LiveData in the Background.
       allNotes = noteDao.getAllNotes();
   }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public void insert(NoteEntity note){
        new InsertAsyncTask(noteDao).execute(note);
    }


    public void update(NoteEntity note){

       new UpdateAsyncTask(noteDao).execute(note);
    }



    public void delete(NoteEntity note){

       new DeleteAsyncTask(noteDao).execute(note);
    }



    public void deleteAll(){

       new DeleteAllAsyncTask(noteDao).execute();
    }


    private static class InsertAsyncTask extends AsyncTask<NoteEntity,Void ,Void>{
       NoteDao noteDao;

       InsertAsyncTask(NoteDao Dao){
           this.noteDao=Dao;
       }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
           noteDao.insertNote(noteEntities[0]);
           return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<NoteEntity,Void ,Void>{
        NoteDao noteDao;

        UpdateAsyncTask(NoteDao Dao){
            this.noteDao=Dao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.updateNote(noteEntities[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<NoteEntity,Void ,Void>{
        NoteDao noteDao;

        DeleteAsyncTask(NoteDao Dao){
            this.noteDao=Dao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.deleteNote(noteEntities[0]);
            return null;
        }
    }


    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void ,Void>{
        NoteDao noteDao;

        DeleteAllAsyncTask(NoteDao Dao){
            this.noteDao=Dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }



}



























