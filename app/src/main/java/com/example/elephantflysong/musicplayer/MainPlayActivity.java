package com.example.elephantflysong.musicplayer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elephantflysong.musicplayer.Adapters.MusicAdapter;
import com.example.elephantflysong.musicplayer.Serveices.SystemService;

import java.io.File;
import java.util.ArrayList;

public class MainPlayActivity extends AppCompatActivity {

    private static final int REQUESTCODE_ACTION_GET_CONTENT = 1;

    private SystemService systemService;
    private Cursor cursor;

    private ImageButton bt_list;
    private ImageButton bt_back;
    private ImageButton bt_stop;
    private ImageButton bt_play;
    private ImageButton bt_moveUp;
    private ImageButton bt_moveDown;
    private TextView text_irc;
    private TextView text_endTime;
    private TextView text_currentTime;
    private TextView text_currentMusic;
    private SeekBar seekBar;
    private ProgressBar progressBar;

    private MediaPlayer mplayer;

    private RecyclerView rv_musics;
    private MusicAdapter adapter;
    private ArrayList<File> files;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1){
                rv_musics.setLayoutManager(new LinearLayoutManager(MainPlayActivity.this));
                rv_musics.setItemAnimator(new DefaultItemAnimator());
                rv_musics.setAdapter(adapter);
                return true;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initMainView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun_mainactivity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_menu_mainactivity_searchMP3:
                File file = Environment.getExternalStorageDirectory();
                browserFile(file);
                break;
            case R.id.item_menu_mainactivity_selectDir:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUESTCODE_ACTION_GET_CONTENT);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUESTCODE_ACTION_GET_CONTENT:
                    Uri uri = data.getData();
                    String path = uri.getPath();
                    Log.i("info", "onActivityResult() path=" + path);
                    break;
                default:
                    break;
            }
        }
    }

    private void initMainView(){
        bt_stop = findViewById(R.id.ibtn_stop);
        bt_moveDown = findViewById(R.id.ibtn_next);
        bt_play = findViewById(R.id.ibtn_start);
        bt_moveUp = findViewById(R.id.ibtn_before);
        bt_list = findViewById(R.id.ibtn_listplay);
        text_currentMusic = findViewById(R.id.text_currentMusic);
        text_currentTime = findViewById(R.id.text_currentTime);
        text_endTime = findViewById(R.id.text_endTime);
        seekBar = findViewById(R.id.seekBar);
        rv_musics = findViewById(R.id.rv_musics);


        text_currentMusic.setText("无");
    }

    private void browserFile(final File file){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("检索本地音频");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_search, null);
        builder.setView(view);
        builder.setCancelable(true);
        builder.setPositiveButton("后台检索", null);
        final AlertDialog dialog = builder.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                files = searchMP3(file);
                if (files.size() > 0){
                    adapter = new MusicAdapter(files);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }else{
                    Toast.makeText(MainPlayActivity.this, "未找到MP3文件", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        }).start();
    }

    private ArrayList<File> searchMP3(File file){
        ArrayList<File> result = new ArrayList<>();
        File[] localFiles = file.listFiles();
        if (localFiles == null){
            return result;
        }
        if (localFiles.length > 0){
            for (int i=0; i<localFiles.length; i++){
                if (!localFiles[i].isDirectory()){
                    if (localFiles[i].getPath().indexOf(".mp3") > -1){
                        result.add(localFiles[i]);
                    }
                }else{
                    result.addAll(searchMP3(localFiles[i]));
                }
            }
        }
        return result;
    }
}
