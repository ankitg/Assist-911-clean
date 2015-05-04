package com.stmichaelshospital.assist911;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by ankitg on 2015-04-28.
 */
public class Assist911Application extends Application // implements TextToSpeech.OnInitListener
{

    public static SharedPreferences _SharedPreferences = null;
    public static SharedPreferences.Editor _SharedPreferencesEditor = null;
    public static ArrayList<VideoItem> _VideosArray = new ArrayList<VideoItem>();

//    private static TextToSpeech _TextToSpeech;
//    public static Boolean isTTSAvailable = false;

//    private static MediaPlayer _MediaPlayer;

    public static final Boolean isDevMode = true;

    @Override
    public void onCreate() {
        super.onCreate();
        populateVideosArray();
        configureSharedPreferences();

//        _TextToSpeech = new TextToSpeech(this, this);
//        _MediaPlayer = new MediaPlayer();
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

//    @Override
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//            int result = _TextToSpeech.setLanguage(Locale.US);
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                // Language is not supported. Install the language.
//                Toast.makeText(this, "Please install US English TTS pack for verbal prompts", Toast.LENGTH_LONG).show();
//            } else {
//                isTTSAvailable = true;
//            }
//        } else {
//            isTTSAvailable = false;
//        }
//    }
//
//    public static void say(String text) {
//        if(isTTSAvailable) {
//            if (Integer.parseInt(Build.VERSION.SDK) < 21) {
//                _TextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//            } else {
//                _TextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        }
//    }

//    public static void say(String text, Context context, int resource, MediaPlayer.OnCompletionListener onCompletedPlaying) {
//        if(isTTSAvailable) {
//            say(text);
//        } else {
//            playAudio(context, resource, onCompletedPlaying);
//        }
//    }
//
//    public static void playAudio (Context context, int resource, MediaPlayer.OnCompletionListener onCompletedPlaying) {
//        _MediaPlayer = MediaPlayer.create(context, resource);
//        _MediaPlayer.setOnCompletionListener(onCompletedPlaying);
//        _MediaPlayer.start();
//    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        _VideosArray = null;

//        if (_TextToSpeech != null) {
//            _TextToSpeech.stop();
//            _TextToSpeech.shutdown();
//        }

//        if (_MediaPlayer != null) {
//            _MediaPlayer.stop();
//            _MediaPlayer = null;
//        }
    }
}
