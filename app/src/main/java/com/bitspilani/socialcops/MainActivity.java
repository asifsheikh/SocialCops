package com.bitspilani.socialcops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitspilani.socialcops.camera.CameraActivity;
import com.bitspilani.socialcops.video.VideoActivity;
import com.bitspilani.socialcops.socialcopsapplication.SocialCopsApplication;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.kinvey.android.callback.KinveyPingCallback;

public class MainActivity extends AppCompatActivity{

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
                startActivity(new Intent(MainActivity.this, com.bitspilani.socialcops.cameranew.CameraActivity.class));
                menuSelfieActions.collapse();
            }
        });

        findViewById(R.id.action_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VideoActivity.class));
                menuSelfieActions.collapse();
            }
        });


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    }


}
