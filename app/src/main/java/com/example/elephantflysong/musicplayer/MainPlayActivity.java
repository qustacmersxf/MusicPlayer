package com.example.elephantflysong.musicplayer;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.elephantflysong.musicplayer.Serveices.SystemService;
import com.example.elephantflysong.musicplayer.Tools.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class MainPlayActivity extends AppCompatActivity {

    private SystemService systemService;
    private Cursor cursor;

    private String playingName;
    private String selectName;
    private ArrayList<String> musicList;

    private ImageButton bt_list;
    private ImageButton bt_back;
    private ImageButton bt_stop;
    private ImageButton bt_play;
    private ImageButton bt_moveUp;
    private ImageButton bt_moveDown;
    private TextView text_endTime;
    private TextView text_currentTime;
    private TextView text_currentMusic;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        systemService = new SystemService(this);
        SharedPreferences sp = getSharedPreferences("MUSIC", MODE_PRIVATE);

        if (sp != null){
            playingName = sp.getString("PLAYINGNAME", null);
            selectName = sp.getString("SELECTNAME", null);
            String s = sp.getString("MUSIC_LIST", null);
            if (s != null){
                musicList = StringHelper.splitString(s);
            }
        }

        initPlayPack();
    }

    private void initPlayPack(){

    }
}
