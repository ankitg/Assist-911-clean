package com.stmichaelshospital.assist911.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.R;
import com.stmichaelshospital.assist911.VideoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class PracticeDialerActivity extends Activity implements TextToSpeech.OnInitListener {

    private MediaPlayer mMediaPlayer; // For the phone ringing.
    private TextToSpeech mTextToSpeech; // For the audio prompts.
    private Boolean isTTSAvailable = false;
    private SpeechRecognizer mSpeechRecognizer;
    private VideoItem mVideo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_dialer);

        if(getIntent().hasExtra("VIDEO") && mVideo == null)
        {
            mVideo = (VideoItem) getIntent().getExtras().getSerializable("VIDEO");
        }

        initializeViews();
        initializeTextToSpeech();
        initializeSpeechRecognizer();
        if(Assist911Application.isDevMode) {
            onDial();
        }
    }

    private void initializeViews() {
    }

    private void initializeTextToSpeech() {
        mTextToSpeech = new TextToSpeech(this, this);
    }

    private void initializeSpeechRecognizer() {
        if(mSpeechRecognizer == null) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
            if (!SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Speech Recognition is not available", Toast.LENGTH_LONG).show();
            }
            mSpeechRecognizer.setRecognitionListener(new VoiceRecognitionListener(this));
        }
    }

    private void onDial() {
        if(mVideo == null) {
            // Something bad has happened :(
        }
        else {
            // Play dialtone
            mMediaPlayer = MediaPlayer.create(this, R.raw.phone_ringing);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // say "911, do you need fire, ambulance or police?"
                    say("Nine one one, do you need fire, ambulance or police?", "01_EMERGENCY_TYPE");
                }
            });


        }
    }

    public void startListeningInBackground() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        Log.d("DEV", "Starting to listen in background");
        mSpeechRecognizer.startListening(intent);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language is not supported. Install the language.
                Toast.makeText(this, "Please install US English TTS pack for verbal prompts", Toast.LENGTH_LONG).show();
            } else {
                isTTSAvailable = true;
            }
        } else {
            isTTSAvailable = false;
        }
    }

    @SuppressLint("NewApi")
    private void say(String text, String id) {
        if(isTTSAvailable) {
            if (Build.VERSION.SDK_INT < 21) {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, id);
            } else {
                HashMap<String, String> mSpeechParams = new HashMap();
                mSpeechParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, id);
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, mSpeechParams);
            }
            mTextToSpeech.setOnUtteranceProgressListener(mUtteranceProgressListener);
        }
    }

    private UtteranceProgressListener mUtteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String s) {
        }

        @Override
        public void onDone(String utteranceId) {
            switch (utteranceId) {
                case "01_EMERGENCY_TYPE":

                // TODO: if level 1, show image prompt depending on the emergency.
//                switch (mVideo.type) {
//                    case FIRE:
//                        break;
//                    case AMBULANCE:
//                        break;
//                    case POLICE:
//                        break;
//                }
                // TODO: listen for answer
                PracticeDialerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startListeningInBackground();
                    }
                });


                // TODO: check answer
                switch (mVideo.type) {
                    case FIRE:
                        break;
                    case AMBULANCE:
                        break;
                    case POLICE:
                        break;
                }


                // TODO: say "what is your address?"
                // Assist911Application.say("What is your address?");
                // TODO: listen for answer
                // TODO: say "what is name?"
                // Assist911Application.say("What is your name?");
                // Assist911Application._SharedPreferences.getString("username","");
                // TODO: listen for answer

                    break;
            }
        }

        @Override
        public void onError(String s) {

        }
    };

    class VoiceRecognitionListener implements RecognitionListener {
        Activity mVoiceRecognition;

        public VoiceRecognitionListener(Activity instance) {
            mVoiceRecognition = instance;
        }
        public void onResults(Bundle data) {
            //Log.d(TAG, "onResults " + data);
            ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] value = data.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

            Log.d("DEV", matches.get(0).toString());

//        mVoiceRecognition.mText.setText("Results: " + String.valueOf(matches.size()));
//
//        if(value != null) { // CONFIDENCE_SCORES wasn't added until API level 14
//            String[] combined = new String[matches.size()];
//            for(int i = 0; i < matches.size(); i++) // The size of the data and value is the same
//                combined[i] = matches.get(i).toString() + "\nScore: " + Float.toString(value[i]);
//            mVoiceRecognition.mList.setAdapter(new ArrayAdapter<String>(mVoiceRecognition, android.R.layout.simple_list_item_1,combined));
//        } else
//            mVoiceRecognition.mList.setAdapter(new ArrayAdapter<String>(mVoiceRecognition, android.R.layout.simple_list_item_1,matches));
        }

        public void onBeginningOfSpeech() {
            //Log.d(TAG, "onBeginningOfSpeech");
//        mVoiceRecognition.mText.setText("Sounding good!");

            Log.d("DEV", "onBeginningOfSpeech()");
        }
        public void onBufferReceived(byte[] buffer) {
            //Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech() {
            //Log.d(TAG, "onEndofSpeech");
//        mVoiceRecognition.mText.setText("Waiting for result...");

            Log.d("DEV", "onEndOfSpeech()");
        }
        public void onError(int error) {
            //Log.d(TAG, "error " + error);
//        mVoiceRecognition.mText.setText("error " + error);

//        if(error == 8) {
//            mVoiceRecognition.sr.cancel();
//            mVoiceRecognition.startListeningInBackground();
//        }

            Log.d("DEV", "onError() " + error);
        }
        public void onEvent(int eventType, Bundle params) {
            //Log.d(TAG, "onEvent " + eventType);
        }
        public void onPartialResults(Bundle partialResults) {
            //Log.d(TAG, "onPartialResults");
        }
        public void onReadyForSpeech(Bundle params) {
            //Log.d(TAG, "onReadyForSpeech");
        }
        public void onRmsChanged(float rmsdB) {
            //Log.d(TAG, "onRmsChanged");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }

        mSpeechRecognizer.stopListening();
        mSpeechRecognizer.destroy();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_practice_dialer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
