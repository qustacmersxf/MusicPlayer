package com.example.elephantflysong.musicplayer.AsyncTasks;

import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.example.elephantflysong.musicplayer.Interfaces.MusicListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ElephantFlySong on 2018/6/15.
 */

public class MusicTask extends AsyncTask<File, Void, Integer> {

    File file;

    public static final int MUSIC_START = 0;
    public static final int MUSIC_PAUSE = 1;
    public static final int MUSIC_NEXT = 2;
    public static final int MUSIC_PREVIOUS = 3;
    public static final int MUSIC_STOP = 4;

    private ArrayList<File> musics;
    private MediaPlayer mediaPlayer;

    private MusicListener listener;

    private int position = -1;
    private int status = MUSIC_STOP;
    private int cmd = -1;

    public MusicTask(ArrayList<File> musics, MusicListener listener){
        this.musics = new ArrayList<>();
        this.musics.addAll(musics);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case MUSIC_START:
                listener.onStart();
                break;
            case MUSIC_PAUSE:
                listener.onPause();
                break;
            case MUSIC_STOP:
                listener.onStop();
                break;
            case MUSIC_NEXT:
                listener.onNext();
                break;
            case MUSIC_PREVIOUS:
                listener.onPrevious();
                break;
            default:
                break;
        }
    }

    @Override
    protected Integer doInBackground(File... params) {
        file = params[0];
        position = musics.indexOf(file);
        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
        try{
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            while (true){
                switch (cmd){
                    case MUSIC_START:
                        if (status != MUSIC_START){
                            mediaPlayer.start();
                            status = MUSIC_START;
                            cmd = -1;
                        }
                        break;
                    case MUSIC_PAUSE:
                        if (status == MUSIC_START){
                            mediaPlayer.pause();
                            status = MUSIC_PAUSE;
                            cmd = -1;
                            return status;
                        }
                        break;
                    case MUSIC_STOP:
                        if (status == MUSIC_START || status == MUSIC_PAUSE){
                            mediaPlayer.stop();
                            status = MUSIC_STOP;
                            return status;
                        }
                        break;
                    case MUSIC_NEXT:
                        position = (position+1) % musics.size();
                        mediaPlayer.stop();
                        mediaPlayer.setDataSource(musics.get(position).getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        return status;
                    case MUSIC_PREVIOUS:
                        position = (position + musics.size() - 1) % musics.size();
                        mediaPlayer.stop();
                        mediaPlayer.setDataSource(musics.get(position).getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        return status;
                    default:
                        break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void onStart(int position){
        this.position = position;
        cmd = MUSIC_START;
        listener.onStart();
    }

    public void onPause(){
        cmd = MUSIC_PAUSE;
    }

    public void onStop(){
        cmd = MUSIC_STOP;
    }

    public void onNext(){
        cmd = MUSIC_NEXT;
    }

    public void onPrevious(){
        cmd = MUSIC_PREVIOUS;
    }
}
