package com.example.coviam.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread d= new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(5000);
                    Intent i =new Intent(splashScreen.this,DisplayByCategory.class);
                    startActivity(i);
                    finish();
                }
                catch( Exception e)
                {

                }
            }
        };
        d.start();
    }
}
