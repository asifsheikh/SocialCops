package com.bitspilani.socialcops;

import android.app.Application;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;


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

    public  synchronized SocialCopsApplication getAppIntance(){
        return (SocialCopsApplication)getApplicationContext();
    }

    public Boolean login(){
        getmKinveyClient().user().login("asif","1234",new KinveyUserCallback() {
            @Override
            public void onFailure(Throwable t) {
                CharSequence text = "Login error.";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(User u) {
                CharSequence text = "Welcome back!";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    public Boolean logout(){
        getmKinveyClient().user().logout().execute();
        return true;
    }

    public Boolean signup(String username, String password) {
         mKinveyClient.user().create(username,password,new KinveyUserCallback() {
            @Override
            public void onSuccess(User user) {
                CharSequence text = user.getUsername() + ", your account has been created.";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Throwable throwable) {
                CharSequence text = "Could not sign up.";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

            }
        });
        return true;
    }

    public Client getmKinveyClient(){
        return mKinveyClient;
    }

}
