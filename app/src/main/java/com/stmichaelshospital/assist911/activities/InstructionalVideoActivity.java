package com.stmichaelshospital.assist911.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.R;


public class InstructionalVideoActivity  extends Activity implements MediaPlayer.OnCompletionListener {

    private VideoView mVideoView;
    private ImageView mReplayButton;
    private ImageView mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructional_video);

        initializeViews();
        playInstructionalVideo();
        devModeSetup(Assist911Application.isDevMode);
    }

    private void initializeViews() {
        mVideoView = (VideoView) findViewById(R.id.video_instructions);
        mReplayButton = (ImageView) findViewById(R.id.btn_replay_instructions);
        mNextButton = (ImageView) findViewById(R.id.btn_next_instructions);

        mReplayButton.setOnClickListener(onReplay);
        mNextButton.setOnClickListener(onNext);
    }

    View.OnClickListener onReplay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mVideoView.seekTo(0);
            mVideoView.start();
        }
    };

    View.OnClickListener onNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent practiceVideoIntent = new Intent(InstructionalVideoActivity.this, PracticeVideoActivity.class);
            startActivity(practiceVideoIntent);
        }
    };

    private void playInstructionalVideo() {
        int mVideoResource = getResources().getIdentifier("instructional", "raw", getPackageName());
        String mUrl = "android.resource://" + getPackageName() + "/" + mVideoResource;

        if (mUrl != null) {
            mVideoView.setOnCompletionListener(this);
            mVideoView.setVideoURI(Uri.parse(mUrl));
            mVideoView.start();
        }
    }

    private void devModeSetup(Boolean isDevMode) {
        if(isDevMode) {
            // Enable buttons.
            mReplayButton.setEnabled(true);
            mNextButton.setEnabled(true);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // Enable buttons on complete.
        mReplayButton.setEnabled(true);
        mNextButton.setEnabled(true);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_instructional_video, menu);
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
