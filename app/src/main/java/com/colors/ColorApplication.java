package com.colors;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class ColorApplication extends Application {
    private static Context sContext;
    private static String TAG = "ColorApplication";

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();

        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}