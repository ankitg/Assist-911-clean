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
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.dialer.dialpad.DigitsEditText;
import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.CallStates;
import com.stmichaelshospital.assist911.R;
import com.stmichaelshospital.assist911.VideoItem;
import com.stmichaelshospital.assist911.dialpad.DialpadImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class PracticeDialerActivity extends Activity implements TextToSpeech.OnInitListener, View.OnClickListener {

    private MediaPlayer mMediaPlayer; // For the phone ringing.

    private TextToSpeech mTextToSpeech; // For the audio prompts.
    private Boolean isTTSAvailable = false;

    private SpeechRecognizer mSpeechRecognizer;

    private VideoItem mVideo = null;
    private CallStates mProgress;

    private DialpadImageButton btnOne;
    private DialpadImageButton btnTwo;
    private DialpadImageButton btnThree;
    private DialpadImageButton btnFour;
    private DialpadImageButton btnFive;
    private DialpadImageButton btnSix;
    private DialpadImageButton btnSeven;
    private DialpadImageButton btnEight;
    private DialpadImageButton btnNine;
    private DialpadImageButton btnZero;
    private DialpadImageButton btnStar;
    private DialpadImageButton btnPound;
    private ImageButton btnBackspace;
    private ImageButton btnCall;

    private DigitsEditText etDigits;


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
            etDigits.setText("911");
        }
    }

    private void initializeViews() {
        btnOne = (DialpadImageButton)findViewById(R.id.one);
        btnOne.setOnClickListener(this);
        btnTwo = (DialpadImageButton)findViewById(R.id.two);
        btnTwo.setOnClickListener(this);
        btnThree = (DialpadImageButton)findViewById(R.id.three);
        btnThree.setOnClickListener(this);
        btnFour = (DialpadImageButton)findViewById(R.id.four);
        btnFour.setOnClickListener(this);
        btnFive = (DialpadImageButton)findViewById(R.id.five);
        btnFive.setOnClickListener(this);
        btnSix = (DialpadImageButton)findViewById(R.id.six);
        btnSix.setOnClickListener(this);
        btnSeven = (DialpadImageButton)findViewById(R.id.seven);
        btnSeven.setOnClickListener(this);
        btnEight = (DialpadImageButton)findViewById(R.id.eight);
        btnEight.setOnClickListener(this);
        btnNine = (DialpadImageButton)findViewById(R.id.nine);
        btnNine.setOnClickListener(this);
        btnZero = (DialpadImageButton)findViewById(R.id.zero);
        btnZero.setOnClickListener(this);

        btnStar = (DialpadImageButton)findViewById(R.id.star);
        btnStar.setOnClickListener(this);
        btnPound = (DialpadImageButton)findViewById(R.id.pound);
        btnPound.setOnClickListener(this);
        btnBackspace = (ImageButton)findViewById(R.id.deleteButton);
        btnBackspace.setOnClickListener(this);
        btnCall = (ImageButton)findViewById(R.id.dialButton);
        btnCall.setOnClickListener(this);

        etDigits = (DigitsEditText)findViewById(R.id.digits);
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
            mProgress = CallStates.STARTED;
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // say "911, do you need fire, ambulance or police?"
                    mProgress = CallStates.EMERGENCY_TYPE;
                    say("Nine one one, do you need fire, ambulance or police?", CallStates.EMERGENCY_TYPE.name());
                }
            });


        }
    }

    public void startListeningInBackground() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
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
            if (Build.VERSION.SDK_INT >= 21) {
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
            if (utteranceId.equalsIgnoreCase(CallStates.EMERGENCY_TYPE.name())) {
                // TODO: if level 1, show image prompt depending on the emergency.
//                switch (mVideo.type) {
//                    case FIRE:
//                        break;
//                    case AMBULANCE:
//                        break;
//                    case POLICE:
//                        break;
//                }
                // listen for answer
                PracticeDialerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startListeningInBackground();
                    }
                });
            }
//            else if(utteranceId.equalsIgnoreCase(CallStates.ADDRESS.name())) {
            else {
                PracticeDialerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startListeningInBackground();
                    }
                });
            }
        }

        @Override
        public void onError(String s) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.one:
                etDigits.append("1");
                break;
            case R.id.two:
                etDigits.append("2");
                break;
            case R.id.three:
                etDigits.append("3");
                break;
            case R.id.four:
                etDigits.append("4");
                break;
            case R.id.five:
                etDigits.append("5");
                break;
            case R.id.six:
                etDigits.append("6");
                break;
            case R.id.seven:
                etDigits.append("7");
                break;
            case R.id.eight:
                etDigits.append("8");
                break;
            case R.id.nine:
                etDigits.append("9");
                break;
            case R.id.zero:
                etDigits.append("0");
                break;
            case R.id.deleteButton:
                etDigits.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                break;
            case R.id.star:
                etDigits.append("*");
                break;
            case R.id.pound:
                etDigits.append("#");
                break;
            case R.id.dialButton:
                if(etDigits.getText().toString().equalsIgnoreCase("911")) {
                    onDial();
                } else {
                    // TODO: Reset, you failed.
                }
                break;
        }
    }


    class VoiceRecognitionListener implements RecognitionListener {
        PracticeDialerActivity mVoiceRecognition;

        public VoiceRecognitionListener(PracticeDialerActivity instance) {
            mVoiceRecognition = instance;
        }
        public void onResults(Bundle data) {
            ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String mSpeechResult;
            if(matches.size() > 0) {
                mSpeechResult = matches.get(0).toString();
                if(Assist911Application.isDevMode) {
                    Toast.makeText(PracticeDialerActivity.this, mSpeechResult, Toast.LENGTH_LONG).show();
                }
            }
            else {
                mSpeechResult = "";
                if(Assist911Application.isDevMode) {
                    Toast.makeText(PracticeDialerActivity.this, "Couldn't catch what you said :(", Toast.LENGTH_LONG).show();
                }
            }

            if(mProgress == CallStates.EMERGENCY_TYPE) {
                // check answer for emergency type
                if(mVideo.type == VideoItem.EMERGENCYTYPE.FIRE && mSpeechResult.contains("fire")
                        || mVideo.type == VideoItem.EMERGENCYTYPE.AMBULANCE && mSpeechResult.contains("ambulance")
                        || mVideo.type == VideoItem.EMERGENCYTYPE.POLICE && mSpeechResult.contains("police"))
                {
                    // say "what is your address?"
                    mProgress = CallStates.ADDRESS;
                    say("What is your address?", CallStates.ADDRESS.name());
                }
            }
            else if (mProgress == CallStates.ADDRESS) {
//                String mAddress = Assist911Application._SharedPreferences.getString("address","");

                // say "what is name?"
                mProgress = CallStates.NAME;
                say("What is your name?", CallStates.NAME.name());
            }
            else if (mProgress == CallStates.NAME) {
//                String mUsername = Assist911Application._SharedPreferences.getString("username","");

                // say "what's the problem?"
                mProgress = CallStates.DETAILS;
                say("What's the problem?", CallStates.DETAILS.name());
            }
            else if (mProgress == CallStates.DETAILS) {
            }
        }

        public void onBeginningOfSpeech() {
        }

        public void onBufferReceived(byte[] buffer) {
        }

        public void onEndOfSpeech() {
        }

        public void onError(int error) {
//            Log.d(TAG, "error " + error);

        if(Assist911Application.isDevMode) {
            Toast.makeText(PracticeDialerActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
        }

//        if(error == 8) {
//            mVoiceRecognition.sr.cancel();
//            mVoiceRecognition.startListeningInBackground();
//        }
        }

        public void onEvent(int eventType, Bundle params) {
        }

        public void onPartialResults(Bundle partialResults) {
        }

        public void onReadyForSpeech(Bundle params) {
        }

        public void onRmsChanged(float rmsdB) {
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
