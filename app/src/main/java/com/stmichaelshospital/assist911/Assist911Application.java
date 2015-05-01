package com.stmichaelshospital.assist911;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by ankitg on 2015-04-28.
 */
public class Assist911Application extends Application {

    public static SharedPreferences mSharedPreferences = null;
    public static SharedPreferences.Editor mSharedPreferencesEditor = null;
    public static ArrayList<VideoItem> videosArray = new ArrayList<VideoItem>();

    @Override
    public void onCreate() {
        super.onCreate();
        populateVideosArray();
        configureSharedPreferences();
    }

    private void configureSharedPreferences() {
        mSharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    private static void populateVideosArray() {
        videosArray.add(new VideoItem("Flames", "flame", VideoItem.EMERGENCYTYPE.FIRE));
        videosArray.add(new VideoItem("Smoke", "smoke", VideoItem.EMERGENCYTYPE.FIRE));
        videosArray.add(new VideoItem("Car Thief", "car", VideoItem.EMERGENCYTYPE.POLICE));
        videosArray.add(new VideoItem("Passing Out", "passed", VideoItem.EMERGENCYTYPE.AMBULANCE));
        videosArray.add(new VideoItem("Drowning", "drowning", VideoItem.EMERGENCYTYPE.AMBULANCE));
        videosArray.add(new VideoItem("Children Biking", "a", VideoItem.EMERGENCYTYPE.NONE));
        videosArray.add(new VideoItem("Family Playing Soccer", "b", VideoItem.EMERGENCYTYPE.NONE));
    }

}
