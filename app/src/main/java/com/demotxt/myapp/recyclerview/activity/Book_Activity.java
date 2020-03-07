package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.demotxt.myapp.recyclerview.R;
import com.squareup.picasso.Picasso;

public class Book_Activity extends AppCompatActivity {

    private TextView tvtitle,tvdescription,tvcategory,Price;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        tvtitle = (TextView) findViewById(R.id.booktitle);
        tvdescription = (TextView) findViewById(R.id.bookDesc);
       // tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);
        Price=(TextView)findViewById(R.id.bookPrice);


        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail");
        float pRise=intent.getExtras().getFloat("price");
        String p=String.valueOf(pRise);

        // Setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        Price.setText(p);
        //img.setImageResource(image);
        Picasso.get().load(image).into(img);


    }
}
