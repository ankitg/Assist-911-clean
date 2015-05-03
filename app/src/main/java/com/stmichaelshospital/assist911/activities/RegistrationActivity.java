package com.stmichaelshospital.assist911.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.R;


public class RegistrationActivity extends Activity {

    private EditText mUsername;
    private EditText mAddress;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeViews();
        loadPreferences();
    }

    private void initializeViews() {
        mUsername = (EditText)findViewById(R.id.et_username_registration);
        mAddress = (EditText)findViewById(R.id.et_address_registration);
        mSaveButton = (Button)findViewById(R.id.btn_save_registration);

        mSaveButton.setOnClickListener(onSaveClicked);
    }

    View.OnClickListener onSaveClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Assist911Application._SharedPreferencesEditor.putString("username", mUsername.getText().toString());
        Assist911Application._SharedPreferencesEditor.putString("address", mAddress.getText().toString());
        Assist911Application._SharedPreferencesEditor.commit();

        Intent mReviewRegistrationIntent = new Intent(RegistrationActivity.this, ReviewRegistrationActivity.class);
        startActivity(mReviewRegistrationIntent);
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
//        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
