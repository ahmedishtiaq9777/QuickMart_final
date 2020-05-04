package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.utils.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class r3_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvcategory, price;
    private ImageView img;
    private FloatingActionButton floatingActionButton;
    private SharedPreferences loginpref, cartlistpref;
    private SharedPreferences.Editor loginprefeditor, cartlistprefeditor;
    private int proid;
    public Set<String> cartids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r3);


        //for toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);


        cartids = new HashSet<String>();

        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);// get login preferences which contains information like "userid" and login status
        cartlistpref = getSharedPreferences("cartprefs", MODE_PRIVATE);//get cartpreferences that contains cartitemlist
        cartlistprefeditor = cartlistpref.edit();// this is to add stuff in preferences

        cartids = cartlistpref.getStringSet("cartids", cartids);//get current product ids in cartprefferences


        tvtitle = (TextView) findViewById(R.id.r3title);
        tvdescription = (TextView) findViewById(R.id.r3Desc);
        //tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.r3thumbnail);
        price = (TextView) findViewById(R.id.r3price);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        // Recieve data
        final Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail");
        float pRise = intent.getExtras().getFloat("price");
        String strprise = String.valueOf(pRise);

        // Setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        price.setText(strprise);
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
                    Intent login = new Intent(r3_Activity.this, Login.class);
                    login.putExtra("proid", proid);
                    startActivity(login);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
