package com.bitspilani.socialcops.video;

import android.app.Activity;
import android.os.Bundle;

import com.bitspilani.socialcops.R;

/**
 * Created by Admin on 9/18/2016.
 */
public class VideoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }
}
