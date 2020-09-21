package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.demotxt.myapp.Quickmart.ownmodels.CheckConnection;
import com.demotxt.myapp.Quickmart.R;

import pl.droidsonroids.gif.GifImageView;

public class Splash_Activity extends AppCompatActivity {

    LazyLoader mLoader;
    GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        gif = findViewById(R.id.logo);


        mLoader =findViewById(R.id.Progressloader);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                CheckConnection connection=new CheckConnection(getApplicationContext());
                Boolean  is_connected=connection.CheckConnection();





                if(is_connected){

                    Intent start = new Intent(Splash_Activity.this, MainActivity2.class);
                    startActivity(start);
                    finish();

                }else {

                    Intent intent = new Intent(Splash_Activity.this,Error_Screen_Activity.class);
                    startActivity(intent);


                }


            }
        }, 2000);

        //for loading animation
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());

        mLoader.addView(loader);

    }
}