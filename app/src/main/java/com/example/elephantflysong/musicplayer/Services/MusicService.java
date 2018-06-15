package com.example.elephantflysong.musicplayer.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.elephantflysong.musicplayer.AsyncTasks.MusicTask;
import com.example.elephantflysong.musicplayer.Interfaces.MusicListener;

import java.io.File;
import java.util.ArrayList;

public class MusicService extends Service {

    private MusicTask musicTask;
    private MusicBinder binder;

    private MusicListener listener = new MusicListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onNext() {

        }

        @Override
        public void onPrevious() {

        }
    };

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        ArrayList<File> files = (ArrayList<File>)intent.getSerializableExtra("files");
        musicTask = new MusicTask(files, listener);
        return binder;
    }

    public class MusicBinder extends Binder{
        public void startMusic(){
            musicTask.onStart();
        }

        public void pauseMusic(){
            musicTask.onPause();
        }

        public void stopMusic(){
            musicTask.onStop();
        }

        public void nextMusic(){
            musicTask.onNext();
        }

        public void previousMusic(){
            musicTask.onPrevious();
        }
    }
}
