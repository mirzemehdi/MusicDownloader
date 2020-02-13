package com.example.musicdownloader.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musicdownloader.Model.Music;

import java.util.List;

@Dao
public interface MusicDao {

    @Insert
    void insert(Music music);

    @Update
    void update(Music music);

    @Delete
    void delete(Music music);


    @Query("SELECT * FROM music_table")
    LiveData<List<Music>> getAllMusics();
}
