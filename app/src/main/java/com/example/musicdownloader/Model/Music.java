package com.example.musicdownloader.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.musicdownloader.Utils.Common;

@Entity(tableName ="music_table" )
public class Music {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String artistName;
    private String musicName;

    public Music() {

    }

    public Music(String artistName, String musicName) {
        this.artistName = artistName;
        this.musicName = musicName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
}