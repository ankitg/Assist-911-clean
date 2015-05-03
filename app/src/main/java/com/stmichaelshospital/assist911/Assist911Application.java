package com.stmichaelshospital.assist911;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ankitg on 2015-04-28.
 */
public class Assist911Application extends Application implements TextToSpeech.OnInitListener{

    public static SharedPreferences _SharedPreferences = null;
    public static SharedPreferences.Editor _SharedPreferencesEditor = null;
    public static ArrayList<VideoItem> _VideosArray = new ArrayList<VideoItem>();
    private static TextToSpeech _TextToSpeech;
    public static Boolean isTTSAvailable = false;

    public static final Boolean isDevMode = true;

    @Override
    public void onCreate() {
        super.onCreate();
        populateVideosArray();
        configureSharedPreferences();
        _TextToSpeech = new TextToSpeech(this, this);
    }

    private void configureSharedPreferences() {
        _SharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        _SharedPreferencesEditor = _SharedPreferences.edit();
    }

    private static void populateVideosArray() {
        _VideosArray.add(new VideoItem("Flames", "flame", VideoItem.EMERGENCYTYPE.FIRE));
        _VideosArray.add(new VideoItem("Smoke", "smoke", VideoItem.EMERGENCYTYPE.FIRE));
        _VideosArray.add(new VideoItem("Car Thief", "car", VideoItem.EMERGENCYTYPE.POLICE));
        _VideosArray.add(new VideoItem("Passing Out", "passed", VideoItem.EMERGENCYTYPE.AMBULANCE));
        _VideosArray.add(new VideoItem("Drowning", "drowning", VideoItem.EMERGENCYTYPE.AMBULANCE));
        _VideosArray.add(new VideoItem("Children Biking", "a", VideoItem.EMERGENCYTYPE.NONE));
        _VideosArray.add(new VideoItem("Family Playing Soccer", "b", VideoItem.EMERGENCYTYPE.NONE));
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = _TextToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language is not supported. Install the language.
                Toast.makeText(this, "Please install US English TTS pack for verbal prompts", Toast.LENGTH_LONG).show();
            } else {
                isTTSAvailable = true;
            }
        } else {
            // TODO: fallback to recorded audio
        }
    }

    public static void say(String text) {
        if(isTTSAvailable) {
            if (Integer.parseInt(Build.VERSION.SDK) < 21) {
                _TextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                _TextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

}
