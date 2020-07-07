package com.demotxt.myapp.recyclerview.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.recyclerview.productlist.ShoppyProductListActivity;
import com.demotxt.myapp.recyclerview.utils.Tools;
import com.demotxt.myapp.recyclerview.utils.ViewAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Prod_Activity extends AppCompatActivity {

    private TextView tvtitle,tvdescription,tvcategory,price;
    private ImageView img;
    private FloatingActionButton floatingActionButton;
    private SharedPreferences loginpref;

    private int proid;
    StringResponceFromWeb result;
    public Set<String> cartids;
    private ImageButton bt_toggle_reviews, bt_toggle_warranty, bt_toggle_description;
    private View lyt_expand_reviews, lyt_expand_warranty, lyt_expand_description;
    private NestedScrollView nested_scroll_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_);
        // For custom toast
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));//for product added :to make custom toast with tick mark

       // ImageView tickicon = (ImageView) layout.findViewById(R.id.image);
       // tickicon.setImageResource(R.drawable.tick3resize);
        TextView text = (TextView) layout.findViewById(R.id.text);
        //text.setText("Product added to the cart!");





        //for toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);







//result=new StringResponceFromWeb();
        cartids=new HashSet<String>();

       loginpref=getSharedPreferences("loginpref",MODE_PRIVATE);// get login preferences which contains information like "userid" and login status
//cartlistpref=getSharedPreferences("cartprefs",MODE_PRIVATE);//get cartpreferences that contains cartitemlist
     //  cartlistprefeditor=cartlistpref.edit();// this is to add stuff in preferences

    //    cartids=cartlistpref.getStringSet("cartids",cartids);//get current product ids in cartprefferences
        //Toast.makeText(getApplicationContext(), "length:" + cartids.size(), Toast.LENGTH_SHORT).show();
        tvtitle = (TextView) findViewById(R.id.txttitle);
    //    tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvdescription = (TextView) findViewById(R.id.textdesciption);
       // tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);
        price= (TextView)findViewById(R.id.price);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);






        // Recieve data
        final Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail") ;
        float pRise=intent.getExtras().getFloat("price");

        String strprice=String.valueOf(pRise);

        // Setting values
        // section reviews
        bt_toggle_reviews = (ImageButton) findViewById(R.id.bt_toggle_reviews);
        lyt_expand_reviews = (View) findViewById(R.id.lyt_expand_reviews);
        bt_toggle_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_reviews);
            }
        });

        // section warranty
        bt_toggle_warranty = (ImageButton) findViewById(R.id.bt_toggle_warranty);
        lyt_expand_warranty = (View) findViewById(R.id.lyt_expand_warranty);
        bt_toggle_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_warranty);
            }
        });

//section description
        bt_toggle_description = (ImageButton) findViewById(R.id.bt_toggle_description);
        lyt_expand_description = (View) findViewById(R.id.lyt_expand_description);
        bt_toggle_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_description);
            }
        });


        toggleArrow(bt_toggle_description);
        lyt_expand_description.setVisibility(View.VISIBLE);

//setting values
        tvtitle.setText(Title);
        tvdescription.setText(Description);
        price.setText(strprice);
      //  img.setImageResource(image);
        Picasso.get().load(image).into(img);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {
    Boolean is_logedin=loginpref.getBoolean("loggedin",false);
    if(is_logedin.equals(true))
    {
        proid= intent.getExtras().getInt("proid");
        final String strpid=String.valueOf(proid);//
          int userid =loginpref.getInt("userid",0);
          final String struserid=String.valueOf(userid);//






try{

    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    // String url = "http:// 192.168.10.13:64077/api/login";
    //String url="https://api.myjson.com/bins/kp9wz";
    String url = "http://ahmedishtiaq1997-001-site1.ftempurl.com/Home/AddtoCart";

    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    result=gson.fromJson(response, StringResponceFromWeb.class);
                    if(result.getresult().equals("Added"))
                    {


                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();


                       // Toast.makeText(getApplicationContext(), "Product Added to Cart" , Toast.LENGTH_SHORT).show();






                    }else if(result.getresult().equals("AllreadyAdded")) {
                       // Toast.makeText(getApplicationContext(), "Product is Already Added", Toast.LENGTH_LONG).show();
                        // response
                        try {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Prod_Activity.this);
                            builder1.setTitle("Already Added");
                            builder1.setMessage("Your product is Already Added to Cart!");

                            builder1 .setIcon(R.drawable.exclamationmarkresize);
                            // builder1.show();
                            AlertDialog alert11 = builder1.create();

                            alert11.show();


                        }catch (Exception e)
                        {
                            Log.i("error:", e.getMessage());
                            Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_SHORT).show();

                        }




                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.i("APIERROR", error.getMessage());
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            params.put("productId", strpid);
            params.put("userId", struserid);

            return params;
        }

        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/x-www-form-urlencoded");
            return params;
        }
    };

    requestQueue.add(rRequest);


}catch (Exception e){
    Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
}



























        /// cartids.add(strpid);
        ///cartlistprefeditor.remove("cartids");
        ///cartlistprefeditor.commit();
        //cartlistprefeditor.putStringSet("cartids",cartids);
        //cartlistprefeditor.commit();
       // Toast.makeText(getApplicationContext(), "Product Added to Cart" , Toast.LENGTH_SHORT).show();

        //  int userid=loginpref.getInt("userid",-1);
        //

        //  getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/AddToCart",userid,proid);
    }else {
        proid= intent.getExtras().getInt("proid");
        Intent login=new Intent(Prod_Activity.this,Login.class);
        login.putExtra("proid",proid);
        startActivity(login);

    }
}
catch (Exception e)
{
    Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }
    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
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
