package com.stmichaelshospital.assist911.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.R;
import com.stmichaelshospital.assist911.VideoItem;

import java.util.Random;


public class PracticeVideoActivity extends Activity implements MediaPlayer.OnCompletionListener {

    private VideoView mVideoView;
    private Button mPositiveButton;
    private Button mNegativeButton;
    private VideoItem mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_video);

        initializeViews();
        setAndPlayPracticeVideo();
    }

    private void initializeViews() {
        mVideoView = (VideoView) findViewById(R.id.video_practice);
        mPositiveButton = (Button) findViewById(R.id.btn_positive_practice);
        mNegativeButton = (Button) findViewById(R.id.btn_negative_practice);

        mPositiveButton.setOnClickListener(onPositive);
        mNegativeButton.setOnClickListener(onNegative);
    }

    View.OnClickListener onPositive = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mVideo.isEmergency) {
                Intent mPracticeLockScreenIntent = new Intent(PracticeVideoActivity.this, PracticeLockScreenActivity.class);
                mPracticeLockScreenIntent.putExtra("VIDEO", mVideo);
                startActivity(mPracticeLockScreenIntent);
            } else {
                // TODO:They made a mistake, let them know.
            }
        }
    };

    View.OnClickListener onNegative = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mVideo.isEmergency) {
                // TODO:They made a mistake, let them know.
            }
            setAndPlayPracticeVideo();
        }
    };

    private void setAndPlayPracticeVideo() {
        mVideo = Assist911Application.videosArray.get(new Random().nextInt(Assist911Application.videosArray.size()));

        int mVideoResource = getResources().getIdentifier(mVideo.videoName, "raw", getPackageName());
        String mUrl = "android.resource://" + getPackageName() + "/" + mVideoResource;

        if (mUrl != null) {
            mVideoView.setOnCompletionListener(this);
            mVideoView.setVideoURI(Uri.parse(mUrl));
            mVideoView.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_practice_video, menu);
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
