package com.stmichaelshospital.assist911.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.stmichaelshospital.assist911.R;


public class PracticeDialerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_dialer);
        
        initializeViews();
    }

    private void initializeViews() {
    }

    private void onDial() {
        // TODO: Play dialtone
        // TODO: say "911, what is your name?"
        // TODO: listen for answer
        // TODO: check answer

        // TODO: say "911, what is your address?"
        // TODO: listen for answer
        // TODO: say "911, what is the nature of your emergency?"
        // TODO: if level 1, show image depending on the emergency.
        // TODO: listen for answer
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice_dialer, menu);
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
