package com.demotxt.myapp.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.productlist.ShoppyProductListActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test extends AppCompatActivity {



    private ViewFlipper flipper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

flipper=(ViewFlipper)findViewById(R.id.viewflipper1);
int images[] = {R.drawable.ac_banner,R.drawable.cloth_banner,R.drawable.sale1,R.drawable.mobile_banner};
        for (int image:images) {
            flipperimages(image);
        }


    }
    public  void flipperimages(int image)
    {
        try {
            ImageView imageView=new ImageView(getApplicationContext());
            imageView.setImageResource(image);
            flipper.addView(imageView);
            flipper.setFlipInterval(3000);
            flipper.setAutoStart(true);
            flipper.setInAnimation(this,android.R.anim.slide_in_left);
            flipper.setOutAnimation(this,android.R.anim.slide_out_right);
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }




    }

    }

