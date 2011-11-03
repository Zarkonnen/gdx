package com.badlogic.helloworld;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.zarkonnen.atactics.HelloWorld;

public class HelloWorldAndroid extends AndroidApplication {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main); 
        initialize(new HelloWorld(), false);
    }
}