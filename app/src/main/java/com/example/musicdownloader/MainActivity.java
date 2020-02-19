package com.example.musicdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.adprogressbarlib.AdCircleProgress;
import com.example.musicdownloader.Adapter.PlayListAdapter;
import com.example.musicdownloader.Interfaces.DownloadClickListener;
import com.example.musicdownloader.Interfaces.PausePlayClickListener;
import com.example.musicdownloader.Model.Music;
import com.example.musicdownloader.ViewModel.MusicViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_DOWNLOADED;
import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_PAUSE;
import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_RESUME;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.musicRecyclerView)
    RecyclerView musicRecyclerView;
    @BindView(R.id.downloadsMusicRecyclerView)
    RecyclerView downloadsMusicRecyclerView;
    private PlayListAdapter playListAdapter,downloadListAdapter;
    private MusicViewModel musicViewModel;
    @BindView(R.id.progressMain)
    ProgressBar progressBar;
    List<Music> mMusicList,downloadMusicList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();




        getMusics();
        playListAdapter.setDownloadClickListener(this::downloadMusic);

        //playListAdapter.setPausePlayClickListener((position, music) -> pausePlayMusic(music));

        downloadListAdapter.setPausePlayClickListener((position, music) -> pausePlayMusic(music));

    }

    @Override
    protected void onDestroy() {

        //This is for remain musics which didn't complete to download
        for (Music music: downloadMusicList){
            if(music.getDownloadLevel()==DOWNLOAD_LEVEL_PAUSE){
                music.setDownloadProgress(0);
                musicViewModel.insert(music);
            }
        }
        super.onDestroy();
    }

    private void pausePlayMusic(Music music) {

        if (music.getDownloadLevel()==DOWNLOAD_LEVEL_RESUME)
            music.setDownloadLevel(DOWNLOAD_LEVEL_PAUSE);
        else
            music.setDownloadLevel(DOWNLOAD_LEVEL_RESUME);

        downloadListAdapter.notifyDataSetChanged();
    }


    private void downloadMusic(int position, Music music) {

        if (music.getDownloadLevel()== DOWNLOAD_LEVEL_DOWNLOADED){
            Toast.makeText(this, getResources().getString(R.string.toast_music_downloaded), Toast.LENGTH_SHORT).show();
            return ;
        }

        musicViewModel.delete(music);
        music.setDownloadProgress(1);
        downloadMusicList.add(music);
        downloadListAdapter.notifyDataSetChanged();


        int downloadTime=5000;//5 sec
        int increaseValue=100/(downloadTime/100);
        final int[] percentage = {0};
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {




                if (percentage[0]<=100) {

                    if (music.getDownloadLevel()==DOWNLOAD_LEVEL_RESUME) {
                        percentage[0] = music.getDownloadProgress() + increaseValue;
                        music.setDownloadProgress(percentage[0]);
                        downloadListAdapter.notifyDataSetChanged();
                    }

                    handler.postDelayed(this, 100);

                }
                else {
                    music.setDownloadLevel(DOWNLOAD_LEVEL_DOWNLOADED);
                    musicViewModel.insert(music);
                    downloadMusicList.remove(music);
                    downloadListAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this,music.getMusicName()+" "
                            +getResources().getString(R.string.toast_music_download_finished)
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void getMusics() {
        musicViewModel.getAllMusics().observe(this, musicList -> {
            playListAdapter.submitList(musicList);
            mMusicList=musicList;
            musicRecyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

        });
    }

    private void init() {
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        musicRecyclerView.setHasFixedSize(true);
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playListAdapter=new PlayListAdapter(this);
        musicRecyclerView.setAdapter(playListAdapter);


        downloadsMusicRecyclerView.setHasFixedSize(true);
        downloadsMusicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        downloadListAdapter=new PlayListAdapter(this);
        downloadsMusicRecyclerView.setAdapter(downloadListAdapter);
        downloadMusicList=new ArrayList<>();
        downloadListAdapter.submitList(downloadMusicList);



    }


}
