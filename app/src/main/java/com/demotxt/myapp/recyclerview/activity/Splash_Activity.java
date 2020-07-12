package com.demotxt.myapp.recyclerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.demotxt.myapp.recyclerview.MainActivity;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.fragment.FavoriteFragment;
import com.demotxt.myapp.recyclerview.fragment.HomeFragment;
import com.demotxt.myapp.recyclerview.ownmodels.CheckConnection;

public class Splash_Activity extends AppCompatActivity {

    LazyLoader mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);


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
        }, 4000);

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
