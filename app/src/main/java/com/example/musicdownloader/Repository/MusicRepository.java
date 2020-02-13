package com.example.musicdownloader.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.musicdownloader.Dao.MusicDao;
import com.example.musicdownloader.Database.MusicDatabase;
import com.example.musicdownloader.Model.Music;

import java.util.List;

public class MusicRepository {

    private MusicDao musicDao;

    public MusicRepository(Application application) {
        MusicDatabase musicDatabase=MusicDatabase.getInstance(application);
        musicDao=musicDatabase.musicDao();
    }
    public void insert(Music music){
        new InsertAsyncTask(musicDao).execute(music);
    }
    public void update(Music music){
        new UpdateAsyncTask(musicDao).execute(music);
    }
    public void delete(Music music){
        new DeleteAsyncTask(musicDao).execute(music);
    }

    public LiveData<List<Music>>getAllMusics(){

        return musicDao.getAllMusics();
    }



    private static class InsertAsyncTask extends AsyncTask<Music,Void,Void>{

        private MusicDao musicDao;

        public InsertAsyncTask(MusicDao musicDao) {
            this.musicDao = musicDao;
        }

        @Override
        protected Void doInBackground(Music... musics) {
            musicDao.insert(musics[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Music,Void,Void>{

        private MusicDao musicDao;

        public UpdateAsyncTask(MusicDao musicDao) {
            this.musicDao = musicDao;
        }

        @Override
        protected Void doInBackground(Music... musics) {
            musicDao.update(musics[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Music,Void,Void>{

        private MusicDao musicDao;

        public DeleteAsyncTask(MusicDao musicDao) {
            this.musicDao = musicDao;
        }

        @Override
        protected Void doInBackground(Music... musics) {
            musicDao.delete(musics[0]);
            return null;
        }
    }

}
