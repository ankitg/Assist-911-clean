package com.stmichaelshospital.assist911.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.stmichaelshospital.assist911.Assist911Application;
import com.stmichaelshospital.assist911.R;


public class PracticeLockScreenActivity extends Activity {

    private Button mEmergencyCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_lock_screen);

        initializeViews();
        prompts();
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

    private void prompts() {
        // TODO: Check if audio prompts are enabled
        Assist911Application.say("Press the green.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice_lock_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
