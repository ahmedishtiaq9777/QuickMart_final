package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.Quickmart.R;
import com.squareup.picasso.Picasso;

public class AboutSeller_Activity extends AppCompatActivity {

    ImageView ShopImage;
    TextView ShopName, ShopPhone, ShopAddress, ShopRating;
    String name, add, phone, img, rating;
    int SellerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_seller_);

        ShopImage = findViewById(R.id.ShopImage);
        ShopName = findViewById(R.id.txt_ShopName);
        ShopPhone = findViewById(R.id.txt_contactNo);
        ShopAddress = findViewById(R.id.txt_ShopAddress);
        ShopRating = findViewById(R.id.rating_Txt);


        // Getting Values
        Intent val = getIntent();
        SellerID = val.getExtras().getInt("sellerid");
        name = val.getExtras().getString("ShopName");
        add = val.getExtras().getString("address");
        phone = val.getExtras().getString("Contact");
        img = val.getExtras().getString("ShopImg");
        rating = String.valueOf(val.getExtras().getString("rating"));

        // Setting Values

        ShopName.setText(name);
        ShopAddress.setText(add);
        ShopPhone.setText(phone);
        ShopRating.setText(rating);
        Picasso.get().load(img).into(ShopImage);


    }

}