package com.bitspilani.socialcops;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Admin on 9/16/2016.
 */
public class CameraPreviewAcivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        byte[] data = getIntent().getExtras().getByteArray("Imagedata");
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        ImageView imageView = (ImageView) findViewById(R.id.image_preview);
        imageView.setImageBitmap(bmp);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
