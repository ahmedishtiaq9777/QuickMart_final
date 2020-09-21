package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.Quickmart.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvcategory, Price;
    private ImageView img;
    private FloatingActionButton floatingActionButton;
    private SharedPreferences loginpref, cartlistpref;
    private SharedPreferences.Editor loginprefeditor, cartlistprefeditor;
    private int proid;
    public Set<String> cartids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        cartids = new HashSet<>();

        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);// get login preferences which contains information like "userid" and login status
        cartlistpref = getSharedPreferences("cartprefs", MODE_PRIVATE);//get cartpreferences that contains cartitemlist
        cartlistprefeditor = cartlistpref.edit();// this is to add stuff in preferences

        cartids = cartlistpref.getStringSet("cartids", cartids);//get current product ids in cartprefferences

        tvtitle = (TextView) findViewById(R.id.booktitle);
        tvdescription = (TextView) findViewById(R.id.bookDesc);
        // tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);
        Price = (TextView) findViewById(R.id.bookPrice);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


        // Recieve data
        final Intent intent = getIntent();
        String Title = Objects.requireNonNull(intent.getExtras()).getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail");
        float pRise = intent.getExtras().getFloat("price");
        String p = String.valueOf(pRise);

        // Setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        Price.setText(p);
        //img.setImageResource(image);
        Picasso.get().load(image).into(img);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean is_logedin = loginpref.getBoolean("loggedin", false);
                if (is_logedin.equals(true)) {
                    proid = intent.getExtras().getInt("proid");
                    String strpid = String.valueOf(proid);
                    cartids.add(strpid);
                    cartlistprefeditor.remove("cartids");
                    cartlistprefeditor.commit();
                    cartlistprefeditor.putStringSet("cartids", cartids);
                    cartlistprefeditor.commit();
                    Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();

                    //  int userid=loginpref.getInt("userid",-1);
                    //

                    //  getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/AddToCart",userid,proid);
                } else {
                    proid = intent.getExtras().getInt("proid");
                    Intent login = new Intent(Book_Activity.this, Login.class);
                    login.putExtra("proid", proid);
                    startActivity(login);

                }

            }
        });


    }
}
