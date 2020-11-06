package com.deepesh.notemaster;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    NoteRepository noteRepository;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository= new NoteRepository(application);
        allNotes= noteRepository.getAllNotes();
    }


    public void insert(NoteEntity noteEntity){
        noteRepository.insert(noteEntity);
    }

    public void update(NoteEntity noteEntity){
        noteRepository.update(noteEntity);
    }

    public void delete(NoteEntity noteEntity){
        noteRepository.delete(noteEntity);
    }

    public void deleteAll(){
        noteRepository.deleteAll();
    }

    public LiveData<List<NoteEntity>> getAllUserNotes(){
        return allNotes;
    }

}
