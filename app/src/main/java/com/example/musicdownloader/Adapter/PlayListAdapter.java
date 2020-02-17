package com.example.musicdownloader.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.adprogressbarlib.AdCircleProgress;
import com.example.musicdownloader.Interfaces.DownloadClickListener;
import com.example.musicdownloader.Model.Music;
import com.example.musicdownloader.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindColor;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    private List<Music> musicList=new ArrayList<>();
    private Context context;
    private DownloadClickListener downloadClickListener;
    private int lastDownloadPos;

    public PlayListAdapter( Context context) {
        this.context = context;
        lastDownloadPos=0;
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.music_row,parent,false);

        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        Music music=musicList.get(position);
        holder.musicNameTxt.setText(music.getMusicName());
        holder.artistNameTxt.setText(music.getArtistName());
        holder.musicDownloadBtn.setVisibility(View.VISIBLE);
        holder.musicDownloadProgress.setVisibility(View.INVISIBLE);
        holder.musicCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));


        if (music.isDownloaded()) {
            holder.musicDownloadBtn.setBackgroundResource(0);
            holder.musicDownloadBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));

            holder.musicDownloadBtn.setImageTintList(ContextCompat.getColorStateList(context, R.color.colorGreen));
        }
        else {

            holder.musicDownloadBtn.setImageTintList(ContextCompat.getColorStateList(context, R.color.colorAccent));
        }




    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setMusicList(List<Music>musicList){
        this.musicList=musicList;
        notifyDataSetChanged();
    }
    public void setDownloadClickListener(DownloadClickListener listener){
        this.downloadClickListener=listener;
    }

    public boolean updateProgressBarValue(RecyclerView recyclerView,int position,int percentage){
//        int position=0;
//        for (Music music1:musicList){
//            if (music1.getId()==music.getId()){
//                break;
//            }
//             position++;
//        }
//        Log.d("Postion: ",""+position);
        PlayListViewHolder viewHolder=(PlayListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

        if (viewHolder!=null) {

            viewHolder.musicDownloadBtn.setVisibility(View.INVISIBLE);

            viewHolder.musicDownloadProgress.setVisibility(View.VISIBLE);
            viewHolder.musicCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorDividerLine));


            viewHolder.musicDownloadProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (viewHolder.musicDownloadProgress.getAttributeResourceId()==R.drawable.ic_pause){
                        viewHolder.musicDownloadProgress.setAttributeResourceId(R.drawable.ic_play);
                        notifyDataSetChanged();

                    }
                    else
                       viewHolder.musicDownloadProgress.setAttributeResourceId(R.drawable.ic_pause);

                }
            });

            if (viewHolder.musicDownloadProgress.getAttributeResourceId()==R.drawable.ic_pause) {
                viewHolder.musicDownloadProgress.setAdProgress(percentage);
                return true;
            }
            else
                return false;

        }
        return false;

    }

    public void moveItem(int fromPosition,int toPosition){
        Music music=musicList.get(fromPosition);
        musicList.remove(fromPosition);
        musicList.add(lastDownloadPos, music);
        notifyItemMoved(fromPosition, lastDownloadPos++);

    }

    public int getLastDownloadPos() {
        return lastDownloadPos;
    }

    public void setLastDownloadPos(int lastDownloadPos) {
        this.lastDownloadPos = lastDownloadPos;
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        private TextView musicNameTxt,artistNameTxt;
        private ImageView musicImage,musicDownloadBtn;
        private AdCircleProgress musicDownloadProgress;
        private CardView musicCardView;
        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);

            musicNameTxt = itemView.findViewById(R.id.row_music_musicName);
            artistNameTxt=itemView.findViewById(R.id.row_music_artistName);
            musicImage = itemView.findViewById(R.id.row_music_imageView);
            musicDownloadBtn = itemView.findViewById(R.id.row_music_download);
            musicDownloadProgress=itemView.findViewById(R.id.row_music_download_progress);
            musicCardView=itemView.findViewById(R.id.row_music_container);

            musicDownloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (downloadClickListener!=null)
                        downloadClickListener.onClick(getAdapterPosition(),musicList.get(getAdapterPosition()));

                }
            });


        }


    }
}
