package com.example.musicdownloader.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.adprogressbarlib.AdCircleProgress;
import com.example.musicdownloader.Interfaces.DownloadClickListener;
import com.example.musicdownloader.Interfaces.PausePlayClickListener;
import com.example.musicdownloader.Model.Music;
import com.example.musicdownloader.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindColor;

import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_DOWNLOADED;
import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_PAUSE;
import static com.example.musicdownloader.Utils.Common.DOWNLOAD_LEVEL_RESUME;

public class PlayListAdapter extends ListAdapter<Music, PlayListAdapter.PlayListViewHolder> {

    private List<Music> musicList=new ArrayList<>();
    private Context context;
    private DownloadClickListener downloadClickListener;
    private PausePlayClickListener pausePlayClickListener;


    public PlayListAdapter( Context context) {
        super(itemCallback);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Music> itemCallback=new DiffUtil.ItemCallback<Music>() {
        @Override
        public boolean areItemsTheSame(@NonNull Music oldItem, @NonNull Music newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Music oldItem, @NonNull Music newItem) {
            return (oldItem.getId()==newItem.getId())
                    &&(oldItem.getDownloadProgress()==newItem.getDownloadProgress());
        }
    };

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.music_row,parent,false);

        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        Music music=getItem(position);
        holder.musicNameTxt.setText(music.getMusicName());
        holder.artistNameTxt.setText(music.getArtistName());


        if (music.getDownloadLevel()==DOWNLOAD_LEVEL_DOWNLOADED) {
            holder.musicDownloadBtn.setVisibility(View.VISIBLE);
            holder.musicProgressDownloadView.setVisibility(View.INVISIBLE);
            holder.musicDownloadBtn.setBackgroundResource(0);
            holder.musicDownloadBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));

            holder.musicDownloadBtn.setImageTintList(ContextCompat.getColorStateList(context, R.color.colorGreen));
        }
        else {
            if (music.getDownloadProgress()==0) {
                holder.musicProgressDownloadView.setVisibility(View.INVISIBLE);
                holder.musicDownloadBtn.setVisibility(View.VISIBLE);
                holder.musicCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.musicDownloadBtn.setBackgroundResource(R.drawable.circle_gradient);
                holder.musicDownloadBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_file_download));
                holder.musicDownloadBtn.setImageTintList(ContextCompat.getColorStateList(context, R.color.colorAccent));
            }
            else {
                if (music.getDownloadLevel()==DOWNLOAD_LEVEL_PAUSE)
                    holder.musicDownloadProgress.setAttributeResourceId(R.drawable.ic_play);
                else
                    holder.musicDownloadProgress.setAttributeResourceId(R.drawable.ic_pause);



                holder.musicDownloadBtn.setVisibility(View.INVISIBLE);
                holder.musicProgressDownloadView.setVisibility(View.VISIBLE);
                holder.musicCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorDividerLine));
                holder.musicDownloadProgress.setAdProgress(music.getDownloadProgress());




            }


        }






    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setDownloadClickListener(DownloadClickListener listener){
        this.downloadClickListener=listener;
    }

    public void setPausePlayClickListener(PausePlayClickListener pausePlayClickListener) {
        this.pausePlayClickListener = pausePlayClickListener;
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        private TextView musicNameTxt,artistNameTxt;
        private ImageView musicImage,musicDownloadBtn;
        private AdCircleProgress musicDownloadProgress;
        private CardView musicCardView;
        private LinearLayout musicProgressDownloadView;
        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);

            musicNameTxt = itemView.findViewById(R.id.row_music_musicName);
            artistNameTxt=itemView.findViewById(R.id.row_music_artistName);
            musicImage = itemView.findViewById(R.id.row_music_imageView);
            musicDownloadBtn = itemView.findViewById(R.id.row_music_download);
            musicDownloadProgress=itemView.findViewById(R.id.row_music_download_progress);
            musicCardView=itemView.findViewById(R.id.row_music_container);
            musicProgressDownloadView=itemView.findViewById(R.id.progressDownloadView);

            musicDownloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (downloadClickListener!=null){
                        if (getAdapterPosition()!=-1)
                            downloadClickListener.onClick(getAdapterPosition(),getItem(getAdapterPosition()));
                    }

                }
            });
        musicProgressDownloadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (pausePlayClickListener!=null){

                    if (getAdapterPosition()!=-1)
                        pausePlayClickListener.onClick(getAdapterPosition(),getItem(getAdapterPosition()));

                }
                return true;
            }
        });








        }


    }
}
