package com.example.elephantflysong.musicplayer.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElephantFlySong on 2018/6/12.
 */

public class StringHelper {

    public static ArrayList<String> splitString(String s){
        ArrayList<String> result = new ArrayList<>();
        String[] strings = s.split(","); //暂时设定为逗号
        for (String str : strings){
            result.add(str);
        }
        return result;
    }

}
