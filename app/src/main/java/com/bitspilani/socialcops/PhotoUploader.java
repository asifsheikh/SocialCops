package com.bitspilani.socialcops;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kinvey.java.core.MediaHttpUploader;
import com.kinvey.java.core.UploaderProgressListener;
import com.kinvey.java.model.FileMetaData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 9/18/2016.
 */
public class PhotoUploader extends AsyncTask<Void,String,Void> implements UploaderProgressListener {
    private static final String DEBUG_TAG="PHOTOUPLOADER";
    private byte[] picturedata;
    private Context context;
    private File pictureFile;
    private ProgressDialog progressDialog;


    public PhotoUploader(byte[] picturedata, Context context) {
        this.picturedata = picturedata;
        this.context = context;
    }

    protected void onPreExecute() {
        File pictureFileDir = getDir();
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Log.d(MainActivity.DEBUG_TAG, "Can't create directory to save image.");
            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(picturedata);
            fos.close();
            Toast.makeText(context, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Log.d(MainActivity.DEBUG_TAG, "File" + filename + "not saved: "
                    + error.getMessage());
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading Please wait");
        progressDialog.show();
        Log.d(DEBUG_TAG,"Now showing the progress dialog");
    }

    @Override
    protected Void doInBackground(Void... params) {
        ((SocialCopsApplication)context.getApplicationContext()).login();
        ((SocialCopsApplication)context.getApplicationContext()).getmKinveyClient().file().upload(pictureFile,this);
        ((SocialCopsApplication)context.getApplicationContext()).logout();
        return null;
    }

    protected void onProgressUpdate(String... values) {
       progressDialog.setMessage(values[0]);
    }

    protected void onPostExecute(Void result) {
       if(progressDialog.isShowing()) progressDialog.dismiss();
        Log.d(DEBUG_TAG,"Now removing the progress dialog");
        Toast.makeText(context, "Upload Complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progressChanged(MediaHttpUploader uploader) throws IOException {
        //progressDialog.setMessage(" " + uploader.getUploadState());
        onProgressUpdate(""+uploader.getUploadState());
        Log.i(DEBUG_TAG, "upload progress: " + uploader.getUploadState());
        // all updates to UI widgets need to be done on the UI thread
    }

    @Override
    public void onSuccess(FileMetaData fileMetaData) {
        Log.i(DEBUG_TAG, "successfully upload file");
        //progressDialog.dismiss();

    }

    @Override
    public void onFailure(Throwable error) {
        Log.e(DEBUG_TAG, "failed to upload file.", error);
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "SocialCops");
    }

}
