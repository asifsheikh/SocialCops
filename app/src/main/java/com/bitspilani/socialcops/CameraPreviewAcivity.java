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
        Bundle bundle = getIntent().getExtras();
        byte[] data = bundle.getByteArray("ImageData");
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        ImageView imageView = (ImageView) findViewById(R.id.image_preview);
        imageView.setImageBitmap(bmp);
    }

}
