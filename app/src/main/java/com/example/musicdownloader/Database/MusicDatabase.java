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

                //It could be random Data ,but I wanted add my favorites ones
               addSampleMusics();

            return null;
        }

        private void addSampleMusics() {
            musicDao.insert(new Music("Eminem","Lose Yourself"));
            musicDao.insert(new Music("Linkin Park","In the End"));
            musicDao.insert(new Music("Eminem","Till I Collapse"));
            musicDao.insert(new Music("Halsey","Gasoline"));
            musicDao.insert(new Music("Eminem","Mockingbird"));
            musicDao.insert(new Music("Linkin Park","Numb"));
            musicDao.insert(new Music("Pixies","Where is My Mind"));
            musicDao.insert(new Music("Evgeny Grinko","Valse"));
            musicDao.insert(new Music("Imagine Dragons","Believer"));
            musicDao.insert(new Music("Twenty one pilots","Lane Boy"));
            musicDao.insert(new Music("Twenty one pilots","Fairly Local"));
            musicDao.insert(new Music("Twenty one pilots","Stressed out"));
            musicDao.insert(new Music("The Weeknd","False Alarm"));
            musicDao.insert(new Music("The Weeknd","Blinding Lights"));
            musicDao.insert(new Music("The Weeknd","Often"));
            musicDao.insert(new Music("Warriors (ft. Imagine Dragons","League of Legends"));
            musicDao.insert(new Music("Imagine Dragons","Natural"));
            musicDao.insert(new Music("Stromae","Formidable"));
            musicDao.insert(new Music("Manga","We could be the same"));
            musicDao.insert(new Music("Zayn ft. Sia","Dusk Till Dawn"));
            musicDao.insert(new Music("Hozier","Take Me To Church"));
            musicDao.insert(new Music("Ludovico Einaudi","Experience"));
            musicDao.insert(new Music("Don Toliver","No Idea"));
            musicDao.insert(new Music("Neffex","Fight Back"));
            musicDao.insert(new Music("Neffex","Greatest"));
            musicDao.insert(new Music("Manga","Dunyanin Sonuna Dogmusum"));
            musicDao.insert(new Music("Manga","Beni Benimle Birak"));
            musicDao.insert(new Music("Eminem","Arabic"));
            musicDao.insert(new Music("Rihanna","Bitch Better Have My Money"));
            musicDao.insert(new Music("Brennan Savage","Look At Me Now"));
            musicDao.insert(new Music("Comatose","Skillet"));
            musicDao.insert(new Music("Indila","Derniere Danse"));
            musicDao.insert(new Music("The Chainsmokers","Dont Let Me Down"));
            musicDao.insert(new Music("MARUV & BOOSIN","Drunk Groove"));

            musicDao.insert(new Music("Fleurie","Hurts Like Hell"));
            musicDao.insert(new Music("Brianna","Lost in Istanbul"));
            musicDao.insert(new Music("Norm Ender","Kaktus"));
            musicDao.insert(new Music("Akif Islamzade","Oten Gunlerimi Qaytaraydilar"));
            musicDao.insert(new Music("Polad Bulbuloglu","Gel ey seher"));
            musicDao.insert(new Music("Akif Islamzade","Ala gozlum"));
            musicDao.insert(new Music("Oqtay Agayev","Gel Qaytar Esqimi"));



        }
    }

}
