package com.bitspilani.socialcops;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.kinvey.android.callback.KinveyPingCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainDialogFragment.MainDialogListener{

    public final static String DEBUG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionsMenu menuSelfieActions = (FloatingActionsMenu) findViewById(R.id.selfie_actions);

        findViewById(R.id.action_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
                menuSelfieActions.collapse();
            }
        });

        findViewById(R.id.action_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VideoActivityNew.class));
                menuSelfieActions.collapse();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        ((SocialCopsApplication)getApplication()).getmKinveyClient().ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(),"Kinvey ping failed",
                        Toast.LENGTH_LONG).show();
            }
            public void onSuccess(Boolean b) {
                Toast.makeText(getBaseContext(),"Kinvey ping Sucess",
                        Toast.LENGTH_LONG).show();
            }
        });
        fab.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //findViewById(R.id.selfie_actions).
    }

    @Override
    public void onClick(View v) {
        DialogFragment fragment;
        fragment = new MainDialogFragment();
        fragment.show(getSupportFragmentManager(), "MainDialogFragment");

    }

    @Override
    public void takePhoto() {
        startActivity(new Intent(this,CameraActivity.class));
    }

    @Override
    public void takeVideo() {
        Toast.makeText(MainActivity.this, "Wait for taking Video", Toast.LENGTH_SHORT).show();

    }


}
