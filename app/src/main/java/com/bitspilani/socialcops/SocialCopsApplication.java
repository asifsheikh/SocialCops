package com.bitspilani.socialcops;

import android.app.Application;
import com.kinvey.android.Client;


/**
 * Created by asifsheikh on 3/9/16.
 */
public class SocialCopsApplication extends Application {
    private Client mKinveyClient;

    @Override
    public void onCreate(){
        super.onCreate();
        mKinveyClient = new Client.Builder("kid_SypuXaFc", "5c1983e775d343c3862d4f460c1ad7a3",this.getApplicationContext()).build();
    }

    public synchronized SocialCopsApplication getAppIntance(){
        return (SocialCopsApplication)getApplicationContext();
    }

    public Client getmKinveyClient(){
        return mKinveyClient;
    }

}
