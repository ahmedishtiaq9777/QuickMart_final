package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.productlist.ShoppyProductListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Prod_Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_prod_);
        cartids = new HashSet<String>();

        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);// get login preferences which contains information like "userid" and login status
        cartlistpref = getSharedPreferences("cartprefs", MODE_PRIVATE);//get cartpreferences that contains cartitemlist
        cartlistprefeditor = cartlistpref.edit();// this is to add stuff in preferences

        cartids = cartlistpref.getStringSet("cartids", cartids);//get current product ids in cartprefferences
        //Toast.makeText(getApplicationContext(), "length:" + cartids.size(), Toast.LENGTH_SHORT).show();
        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        // tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);
        price = (TextView) findViewById(R.id.price);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


        // Recieve data
        final Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail");
        float pRise = intent.getExtras().getFloat("price");
        String strprice = String.valueOf(pRise);

        // Setting values


        tvtitle.setText(Title);
        tvdescription.setText(Description);
        price.setText(strprice);
        //  img.setImageResource(image);
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
                    Intent login = new Intent(Prod_Activity.this, Login.class);
                    login.putExtra("proid", proid);
                    startActivity(login);
                }
            }
        });
    }
    /* public void  getconnection(String url, final int uid, final int pid)
     {

 RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
 StringRequest request=new StringRequest(Request.Method.POST, url,
         new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {

             }
         }, new Response.ErrorListener() {
     @Override
     public void onErrorResponse(VolleyError error) {

     }
 }

 ){

     @Override
     protected Map<String, String> getParams() throws AuthFailureError {
         String.valueOf(uid);
         String.valueOf(pid);
         Map<String,String> parems =new HashMap<String, String>();
         parems.put("userId",String.valueOf(uid));
         parems.put("productId", String.valueOf(pid));

         return  parems;
     }

     @Override
     public Map<String, String> getHeaders() throws AuthFailureError {
         Map<String, String> params = new HashMap<String, String>();
         params.put("Content-Type", "application/x-www-form-urlencoded");
         return params;
     }
 };

    }*/
}
