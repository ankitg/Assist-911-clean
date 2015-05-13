package com.stmichaelshospital.assist911.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.R;


public class ReviewRegistrationActivity extends Activity {

    TextView mUsername;
    TextView mAddress;
    ImageView mNextButton;
    ImageView mEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_registration);

        initializeViews();
        loadPreferences();
    }

    private void initializeViews() {
        mUsername = (TextView)findViewById(R.id.tv_address_review_registration);
        mAddress = (TextView)findViewById(R.id.tv_username_review_registration);
        mNextButton = (ImageView)findViewById(R.id.btn_next_review_registration);
        mEditButton = (ImageView)findViewById(R.id.btn_edit_review_registration);

        mNextButton.setOnClickListener(onNextPressed);
        mEditButton.setOnClickListener(onEditPressed);
    }

    View.OnClickListener onNextPressed = new View.OnClickListener() {
        @Override
        public void onClick(View button) {
            Intent mInstructionalVideoIntent = new Intent(ReviewRegistrationActivity.this, InstructionalVideoActivity.class);
            startActivity(mInstructionalVideoIntent);
        }
    };

    View.OnClickListener onEditPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent registrationIntent = new Intent(ReviewRegistrationActivity.this, RegistrationActivity.class);
            startActivity(registrationIntent);
            finish();
        }
    };

    private void loadPreferences() {
        mUsername.setText(Assist911Application._SharedPreferences.getString("username", ""));
        mAddress.setText(Assist911Application._SharedPreferences.getString("address", ""));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_review_registration, menu);
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
