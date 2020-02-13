package com.example.musicdownloader.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.musicdownloader.Model.Music;
import com.example.musicdownloader.Repository.MusicRepository;

import java.util.List;

public class MusicViewModel extends AndroidViewModel {

    private MusicRepository musicRepository;
    public MusicViewModel(@NonNull Application application) {
        super(application);
        musicRepository=new MusicRepository(application);
    }

    public void insert(Music music){
        musicRepository.insert(music);
    }

    public void update(Music music){
        musicRepository.update(music);
    }
    public void delete(Music music){
        musicRepository.delete(music);
    }

    public LiveData<List<Music>>getAllMusics(){
        return musicRepository.getAllMusics();
    }
}
