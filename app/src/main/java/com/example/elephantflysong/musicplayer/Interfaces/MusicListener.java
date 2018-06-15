package com.example.elephantflysong.musicplayer.Interfaces;

/**
 * Created by ElephantFlySong on 2018/6/15.
 * 可以用来修改界面
 */

public interface MusicListener {
    public void onStart();

    public void onPause();

    public void onStop();

    public void onNext();

    public void onPrevious();
}
