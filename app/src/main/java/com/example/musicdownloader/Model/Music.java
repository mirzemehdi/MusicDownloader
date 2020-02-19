package com.example.musicdownloader.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.musicdownloader.Utils.Common;


import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_RESUME;

@Entity(tableName ="music_table" )
public class Music {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String artistName;
    private String musicName;
    private int downloadProgress;
    private int downloadLevel;





    public Music(String artistName, String musicName) {
        this.artistName = artistName;
        this.musicName = musicName;
        this.downloadLevel=DOWNLOAD_LEVEL_RESUME;
        downloadProgress=0;
    }




    public int getDownloadProgress() {
        return downloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        this.downloadProgress = downloadProgress;
    }


    public void setDownloadLevel(int downloadLevel) {
        this.downloadLevel = downloadLevel;
    }

    public int getDownloadLevel() {
        return downloadLevel;
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
