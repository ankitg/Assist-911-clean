package com.stmichaelshospital.assist911;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by ankitg on 2015-04-28.
 */
public class Assist911Application extends Application {

    public static SharedPreferences _SharedPreferences = null;
    public static SharedPreferences.Editor _SharedPreferencesEditor = null;
    public static ArrayList<VideoItem> _VideosArray = new ArrayList<VideoItem>();

    public static final Boolean isDevMode = true;

    @Override
    public void onCreate() {
        super.onCreate();
        populateVideosArray();
        configureSharedPreferences();
    }

    private void configureSharedPreferences() {
        _SharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        _SharedPreferencesEditor = _SharedPreferences.edit();
    }

    private void populateVideosArray() {
        _VideosArray.add(new VideoItem("Flames", "flame", VideoItem.EMERGENCYTYPE.FIRE));
        _VideosArray.add(new VideoItem("Smoke", "smoke", VideoItem.EMERGENCYTYPE.FIRE));
        _VideosArray.add(new VideoItem("Car Thief", "car", VideoItem.EMERGENCYTYPE.POLICE));
        _VideosArray.add(new VideoItem("Passing Out", "passed", VideoItem.EMERGENCYTYPE.AMBULANCE));
        _VideosArray.add(new VideoItem("Drowning", "drowning", VideoItem.EMERGENCYTYPE.AMBULANCE));
        _VideosArray.add(new VideoItem("Children Biking", "a", VideoItem.EMERGENCYTYPE.NONE));
        _VideosArray.add(new VideoItem("Family Playing Soccer", "b", VideoItem.EMERGENCYTYPE.NONE));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        _VideosArray = null;
    }
}