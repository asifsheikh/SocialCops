package com.bitspilani.socialcops;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Admin on 9/18/2016.
 */
public class VideoActivityNew extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }
}
