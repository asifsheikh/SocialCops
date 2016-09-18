package com.bitspilani.socialcops;

import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by asifsheikh on 14/9/16.
 */
public class CameraActivity extends Activity implements Camera.PictureCallback, View.OnClickListener {

    private static final String DEBUG_TAG="CameraActivity";
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private byte[] pictureData;
    private  Button imgClose;
    private Button save;
    private Button cancel;
    private LinearLayout ll_save_cancel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewlayout);

        try{
            releaseCameraAndPreview();
            mCamera = getCameraInstance();//you can use open(int) to use different cameras
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            //Toast.makeText(CameraActivity.this, "camera is null", Toast.LENGTH_SHORT).show();
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_preview);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }

        //ll to handle the save and cancel button
        ll_save_cancel = (LinearLayout) findViewById(R.id.ll_save_cancel_button);
        //button for saving the imgae
        save = (Button) findViewById(R.id.button_save);
        //button to exit from the activity
        cancel = (Button) findViewById(R.id.button_cancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //btn to close the application
        imgClose = (Button)findViewById(R.id.button_capture);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCamera != null){
                    mCamera.takePicture(null, null, CameraActivity.this);
                }
                else{
                    Toast.makeText(CameraActivity.this, "Cannot open Camera", Toast.LENGTH_SHORT).show();
                }

                //System.exit(0);
            }
        });
        imgClose.setVisibility(View.VISIBLE);
        ll_save_cancel.setVisibility(View.GONE);
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

    private void releaseCameraAndPreview() {
       if(mCameraView != null) mCameraView.setmCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        pictureData = data;
       imgClose.setVisibility(View.GONE);
        ll_save_cancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_save){
            //savePicture();
            MediaActionSound sound = new MediaActionSound();
            sound.play(MediaActionSound.SHUTTER_CLICK);
            new PhotoUploader(pictureData,this).execute();
            ll_save_cancel.setVisibility(View.GONE);
            imgClose.setVisibility(View.VISIBLE);
            mCamera.startPreview();
        }
        else{
            ll_save_cancel.setVisibility(View.GONE);
            imgClose.setVisibility(View.VISIBLE);
            mCamera.startPreview();
        }
    }

}

