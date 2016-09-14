package com.bitspilani.socialcops;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kinvey.android.callback.KinveyPingCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.hardware.Camera.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainDialogFragment.MainDialogListener{


    public final static String DEBUG_TAG = "MainActivity";
    private Camera camera;
    private CameraPreview mPreview;

    private PictureCallback mPicture  = new PhotoHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an instance of Camera
        camera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        //preview.addView(mPreview);

        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        camera.takePicture(null, null, mPicture);
                    }
                }
        );
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

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }
        catch (Exception e){
            Log.e(DEBUG_TAG, "failed to open Camera");
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    // Here you have handle the onclcik  for the FAB
    @Override
    public void onClick(View v) {
        DialogFragment fragment;
        fragment = new MainDialogFragment();
        fragment.show(getSupportFragmentManager(), "MainDialogFragment");

    }

    @Override
    public void takePhoto() {
        camera.takePicture(null, null,
                new PhotoHandler(getApplicationContext()));
    }

    @Override
    public void takeVideo() {
        Toast.makeText(MainActivity.this, "Wait for taking Video", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }
}
