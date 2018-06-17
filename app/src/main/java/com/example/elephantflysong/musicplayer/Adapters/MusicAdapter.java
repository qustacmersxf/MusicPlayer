package com.example.elephantflysong.musicplayer.Adapters;

import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elephantflysong.musicplayer.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ElephantFlySong on 2018/6/14.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private ArrayList<File> list;
    private OnItemClickListener listener;

    public MusicAdapter(ArrayList<File> list){
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musics,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setMusic(list.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image_cover;
        private TextView text_name;
        private TextView text_artist;
        public View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            image_cover = view.findViewById(R.id.item_musics_image_cover);
            text_name = view.findViewById(R.id.item_musics_text_munsicName);
            text_artist = view.findViewById(R.id.item_musics_text_artist);
        }

        public void setMusic(File music){
            image_cover.setBackgroundResource(R.drawable.music);
            text_name.setText(music.getName());
            /*MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(music.getPath());
            text_artist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));*/
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
