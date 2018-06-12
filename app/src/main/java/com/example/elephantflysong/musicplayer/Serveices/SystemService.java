package com.example.elephantflysong.musicplayer.Serveices;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.MediaStore;

/**
 * Created by ElephantFlySong on 2018/6/12.
 */

public class SystemService {
    private Context context;
    private Cursor cursor;

    public SystemService(Context context){
        this.context = context;
    }

    public Cursor allSongs(){
        if (cursor != null){
            return cursor;
        }
        ContentResolver resolver = context.getContentResolver();
        cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        return cursor;
    }

    public String getArtist(){
        return cursor.getString(cursor
            .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
    }

    public String getTitle(){
        String title = cursor.getString(cursor
            .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
        try{
            title = new String(title.getBytes(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return title;
    }

    public String getAlbum() throws RemoteException {
        return cursor.getString(cursor
            .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
    }
}
