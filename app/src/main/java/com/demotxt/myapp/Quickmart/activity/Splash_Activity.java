package com.demotxt.myapp.Quickmart.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.demotxt.myapp.Quickmart.ownmodels.CheckConnection;
import com.demotxt.myapp.Quickmart.R;

import pl.droidsonroids.gif.GifImageView;

public class Splash_Activity extends AppCompatActivity {

    LazyLoader mLoader;
    GifImageView gif;
    boolean gps_enabled=false;
    boolean network_enabled=false;
    LocationManager lm;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        gif = findViewById(R.id.logo);


        mLoader = findViewById(R.id.Progressloader);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                CheckConnection connection = new CheckConnection(getApplicationContext());
                Boolean is_connected = connection.CheckConnection();


                if (is_connected) {
                    //
                    Intent start = new Intent(Splash_Activity.this, MainActivity2.class);
                    startActivity(start);
                    finish();

                } else {

                    Intent intent = new Intent(Splash_Activity.this, Error_Screen_Activity.class);
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
        //

        Location net_loc=null, gps_loc=null;

        if(gps_enabled)
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                },1);
            }else {
                gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

        //permission for Network
        if(network_enabled)
            if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                },2);
            }else {
                net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }


   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error Permission", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error Permission", Toast.LENGTH_SHORT).show();
        }
    }

}