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

import com.example.musicdownloader.Adapter.PlayListAdapter;
import com.example.musicdownloader.Interfaces.DownloadClickListener;
import com.example.musicdownloader.Model.Music;
import com.example.musicdownloader.ViewModel.MusicViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.musicRecyclerView)
    RecyclerView musicRecyclerView;
    private PlayListAdapter playListAdapter;
    private MusicViewModel musicViewModel;
    @BindView(R.id.progressMain)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();



        getMusics();
        playListAdapter.setDownloadClickListener(new DownloadClickListener() {
            @Override
            public void onClick(int position, Music music) {
               downloadMusic(position,music);

            }
        });



    }


    private void downloadMusic(int position, Music music) {

        if (music.isDownloaded()){
            Toast.makeText(this, getResources().getString(R.string.toast_music_downloaded), Toast.LENGTH_SHORT).show();
            return ;
        }

        if (playListAdapter.getLastDownloadPos()>0){
            Toast.makeText(this, getResources().getString(R.string.toast_music_one_download), Toast.LENGTH_SHORT).show();
            return ;
        }
        int downloadTime=5000;//5 sec
        int increaseValue=100/(downloadTime/100);
        final int[] percentage = {0};
//        playListAdapter.moveItem(position,0);
        int updatedPos=playListAdapter.getLastDownloadPos();
        musicRecyclerView.scrollToPosition(updatedPos);
        playListAdapter.moveItem(position,playListAdapter.getLastDownloadPos());

        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {



                boolean isUpdated=playListAdapter.updateProgressBarValue(musicRecyclerView,updatedPos, percentage[0]);
                if (isUpdated)
                    percentage[0] +=increaseValue;
                if (percentage[0]<=100)
                    handler.postDelayed(this,100);
                else {
                    music.setDownloaded(true);
                    playListAdapter.setLastDownloadPos(playListAdapter.getLastDownloadPos()-1);
//                    playListAdapter.moveItem(updatedPos,position);
//                    musicRecyclerView.scrollToPosition(playListAdapter.getLastDownloadPos());
//                    playListAdapter.notifyItemChanged(playListAdapter.getLastDownloadPos());
                    musicViewModel.update(music);
                }

            }
        });



    }

    private void getMusics() {
        musicViewModel.getAllMusics().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> musicList) {
                Log.d("OnChangeddd","called");
                playListAdapter.setMusicList(musicList);
                musicRecyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void init() {
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        musicRecyclerView.setHasFixedSize(true);
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playListAdapter=new PlayListAdapter(this);
        musicRecyclerView.setAdapter(playListAdapter);

    }


}
