package com.example.musicdownloader.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.musicdownloader.Dao.MusicDao;
import com.example.musicdownloader.Model.Music;
import com.example.musicdownloader.Utils.Common;

@Database(entities = {Music.class}, version = 1,exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase {

    private static MusicDatabase instance;

    public abstract MusicDao musicDao();


    public static synchronized MusicDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MusicDatabase.class, "musicDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MusicDao musicDao;

        private PopulateDbAsyncTask(MusicDatabase db) {
            musicDao = db.musicDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
           // musicDao.insert(new Music("Eminem","Lose Yourself"));
            return null;
        }
    }

}
