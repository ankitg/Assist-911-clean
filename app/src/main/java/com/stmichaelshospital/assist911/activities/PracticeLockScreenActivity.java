package com.stmichaelshospital.assist911.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stmichaelshospital.assist911.R;

import java.util.Locale;


public class PracticeLockScreenActivity extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech mTextToSpeech; // For the audio prompts.
    private Button mEmergencyCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_lock_screen);

        initializeViews();
        initializeTextToSpeech();
    }

    private void initializeViews() {
        mEmergencyCallButton = (Button)findViewById(R.id.btn_emergency_call_practice_lock_screen);

        mEmergencyCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mDialpadIntent = new Intent(PracticeLockScreenActivity.this, PracticeDialerActivity.class);
                mDialpadIntent.putExtras(getIntent());
                startActivity(mDialpadIntent);
            }
        });
    }

    private void initializeTextToSpeech() {
        mTextToSpeech = new TextToSpeech(this, this);
    }

    private void audioPrompts() {
        // TODO: Check if audio prompts are enabled
        say("Press the green.");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language is not supported. Install the language.
                Toast.makeText(this, "Please install US English TTS pack for verbal prompts", Toast.LENGTH_LONG).show();
            } else {
                audioPrompts();
            }
        }
    }

    @SuppressLint("NewApi")
    private void say(String text) {
        if (Build.VERSION.SDK_INT < 21) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_practice_lock_screen, menu);
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
