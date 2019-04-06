package com.example.ammarhasan.budgeter;
import android.app.Application;
import com.firebase.client.Firebase;

/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This is an Application class required for Firebase
 */
public class FireBaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
