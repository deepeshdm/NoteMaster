package com.deepesh.notemaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    int id;

    String dateAndtime;

    String data;

    NoteEntity(String date_time , String data){
        this.data=data;
        this.dateAndtime=date_time;
    }

    NoteEntity(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateAndtime() {
        return dateAndtime;
    }

    public void setDateAndtime(String dateAndtime) {
        this.dateAndtime = dateAndtime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
