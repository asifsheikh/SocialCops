package com.bitspilani.socialcops;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    /* Called when the second activity's finished */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.equals("cancel")){
                    Toast.makeText(CameraActivity.this, "Picture not saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CameraActivity.this, "Saving Picture", Toast.LENGTH_SHORT).show();
                    savePicture();
                }
            }
        }
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

    private void savePicture(){
        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Log.d(MainActivity.DEBUG_TAG, "Can't create directory to save image.");
            Toast.makeText(CameraActivity.this, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(pictureData);
            fos.close();
            Toast.makeText(CameraActivity.this, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Log.d(MainActivity.DEBUG_TAG, "File" + filename + "not saved: "
                    + error.getMessage());
            Toast.makeText(CameraActivity.this, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }


    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "SocialCops");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_save){
            savePicture();
            finish();
        }
        else{
            finish();
        }
    }
}
