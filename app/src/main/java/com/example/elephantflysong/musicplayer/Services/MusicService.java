package com.example.elephantflysong.musicplayer.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.elephantflysong.musicplayer.AsyncTasks.MusicTask;
import com.example.elephantflysong.musicplayer.Interfaces.MusicListener;
import com.example.elephantflysong.musicplayer.MainPlayActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {

    private MusicTask musicTask;
    private MusicBinder binder;
    private ArrayList<File> files;
    private MediaPlayer player;

    private int position = -1;

    private MusicListener listener = new MusicListener() {
        @Override
        public void onStart() {
            Toast.makeText(MusicService.this, "onStart", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            Toast.makeText(MusicService.this, "onPause", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStop() {
            Toast.makeText(MusicService.this, "onStop", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext() {
            Toast.makeText(MusicService.this, "onNext", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPrevious() {
            Toast.makeText(MusicService.this, "onPrevious", Toast.LENGTH_SHORT).show();
        }
    };

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        binder = new MusicBinder();
        return binder;
    }

    public class MusicBinder extends Binder{
        public void startMusic(int index){
            if (player == null){
                player = new MediaPlayer();
            }
            try{
                player.reset();
                player.setDataSource(files.get(index).getPath());
                player.prepare();
                player.start();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp){
                        nextMusic();
                    }
                });
                position = index;
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void startMusic(){
            if (position == -1) {
                position = 0;
                this.startMusic(position);
            }else{
                player.start();
            }

        }

        public void pauseMusic(){
            player.pause();
        }

        public void stopMusic(){
            player.stop();
        }

        public int nextMusic(){
            player.stop();
            try{
                position = (position + 1) % files.size();
                File file = files.get(position);
                player.reset();
                player.setDataSource(file.getPath());
                player.prepare();
                player.start();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                return position;
            }
        }

        public int previousMusic(){
            player.stop();
            try{
                position = (position + files.size() - 1) % files.size();
                File file = files.get(position);
                player.reset();
                player.setDataSource(file.getPath());
                player.prepare();
                player.start();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                return position;
            }
        }

        public void setFiles(ArrayList<File> files_){
            files = files_;
        }
    }
}
