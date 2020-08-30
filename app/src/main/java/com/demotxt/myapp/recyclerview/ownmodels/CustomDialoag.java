package com.demotxt.myapp.recyclerview.ownmodels;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.demotxt.myapp.recyclerview.R;

public class CustomDialoag extends AppCompatActivity {
    private Context context;
    LottieAnimationView lav;
    boolean isChecked = false;
    public static final String MYPREFRENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;

    public CustomDialoag(Context cntx) {
        context = cntx;
        sharedPreferences = context.getSharedPreferences(MYPREFRENCES,context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showCustomDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.customdialoag);
        dialog.setCancelable(true);
        lav = (LottieAnimationView) dialog.findViewById(R.id.DARK);
        //Animation was slow so I increased speed
        lav.setSpeed(6);
        lav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked){
                    lav.setMinAndMaxProgress(0.5f,1.0f);
                    lav.playAnimation();
                    isChecked=false;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                }
                else{
                    lav.setMinAndMaxProgress(0.0f,0.5f);
                    lav.playAnimation();
                    isChecked=true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                }
            }
        });

        buttonCheck();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void saveNightModeState(boolean nightMode){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);
        editor.apply();

    }
    public void buttonCheck(){
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            lav.setMinAndMaxProgress(0.0f,0.5f);
            lav.playAnimation();
            isChecked=true;
        }
    }
}