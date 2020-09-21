package com.demotxt.myapp.Quickmart.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.widget.NestedScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.utils.Tools;
import com.demotxt.myapp.Quickmart.utils.ViewAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Prod_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, price;
    private ImageView img;
    private FloatingActionButton floatingActionButton;
    private SharedPreferences loginpref;
    private int proid;
    private int sellerid;
    StringResponceFromWeb result;
    public Set<String> cartids;
    private ImageButton bt_toggle_reviews, bt_toggle_description;
    private View lyt_expand_reviews, lyt_expand_description;
    private NestedScrollView nested_scroll_view;
    //For Review Layout
    private EditText Feedback_TXT;
    private Button Submit_Btn, BuyNow;
    private AppCompatRatingBar mRatingBar;
    public String rate, Feedback;
    private String Title, Description, image;
    String struserid, strpid, strsellerid;
    private View layout;
    private Spinner spinner1, spinner2;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_);
        // For custom toast
        final LayoutInflater inflater = getLayoutInflater();
        try {
            layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));//for product added :to make custom toast with tick mark

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Prodactivity", "error" + e.getMessage());

        }
        // Size Spinner
        spinner1 = (Spinner) findViewById(R.id.sizeSpinner);
        // Initializing a String Array
        String[] size = new String[]{
                "Size",
                "S",
                "M",
                "L",
                "XL"
        };
        final List<String> SizeList = new ArrayList<>(Arrays.asList(size));
        final ArrayAdapter<String> Sizespinner = new ArrayAdapter<String>(
                this, R.layout.item_spinner, SizeList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        Sizespinner.setDropDownViewResource(R.layout.item_spinner);
        spinner1.setAdapter(Sizespinner);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Color Spinner
        spinner2 = (Spinner) findViewById(R.id.colorSpinner);
        // Initializing a String Array
        String[] color = new String[]{
                "Color",
                "Red",
                "Yellow",
                "White",
                "Black"
        };
        final List<String> colorList = new ArrayList<>(Arrays.asList(color));
        final ArrayAdapter<String> Colorspinner = new ArrayAdapter<String>(
                this, R.layout.item_spinner, colorList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        Colorspinner.setDropDownViewResource(R.layout.item_spinner);
        spinner2.setAdapter(Colorspinner);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //result=new StringResponseFromWeb();
        cartids = new HashSet<>();

        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);// get login preferences which contains information like "userid" and login status

        tvtitle = (TextView) findViewById(R.id.txttitle);
        //    tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvdescription = (TextView) findViewById(R.id.textdesciption);
        // tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);
        price = (TextView) findViewById(R.id.price);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        //
        Feedback_TXT = findViewById(R.id.txt_feedback);
        Submit_Btn = findViewById(R.id.Submit_Btn);
        mRatingBar = findViewById(R.id.AppCompact_RatingBar);
        BuyNow = findViewById(R.id.BuyNow_btn);

        //Recieve data from prod
        final Intent intent = getIntent();
        Title = Objects.requireNonNull(intent.getExtras()).getString("Title");
        Description = intent.getExtras().getString("Description");
        image = intent.getExtras().getString("Thumbnail");
        Float pRise = intent.getExtras().getFloat("price");
        sellerid = intent.getExtras().getInt("sellerid");
        strsellerid = String.valueOf(sellerid);
        proid = intent.getExtras().getInt("proid");
        strpid = String.valueOf(proid);

        final String strprice = String.valueOf(pRise);

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
                    Boolean is_logedin = loginpref.getBoolean("loggedin", false);
                    if (is_logedin.equals(true)) {
                        int userid = loginpref.getInt("userid", 0);
                        final String struserid = String.valueOf(userid);


                        try {
                            if (validateSpinner(spinner2, "Choose Color", "Color") == false) {
                                AlertDialog.Builder build = new AlertDialog.Builder(Prod_Activity.this);
                                build.setTitle("Choose Color");
                                build.setMessage("Please Select a Color");
                                build.setIcon(R.drawable.exclamationmarkresize);
                                // builder1.show();
                                AlertDialog alert = build.create();
                                alert.show();
                            } else {
                                if (validateSpinner(spinner1, "Choose Size", "Size") == false) {
                                    AlertDialog.Builder build = new AlertDialog.Builder(Prod_Activity.this);
                                    build.setTitle("Choose Size");
                                    build.setMessage("Please Select a Size");
                                    build.setIcon(R.drawable.exclamationmarkresize);
                                    // builder1.show();
                                    AlertDialog alert = build.create();
                                    alert.show();
                                } else {

                                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    // String url = "http:// 192.168.10.13:64077/api/login";
                                    //String url="https://api.myjson.com/bins/kp9wz";
                                    String url = MainActivity2.hostinglink + "/Home/AddtoCart";

                                    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                                    GsonBuilder builder = new GsonBuilder();
                                                    Gson gson = builder.create();
                                                    result = gson.fromJson(response, StringResponceFromWeb.class);
                                                    if (result.getresult().equals("Added")) {


                                                        Toast toast = new Toast(getApplicationContext());
                                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        toast.setDuration(Toast.LENGTH_SHORT);
                                                        toast.setView(layout);
                                                        toast.show();


                                                        // Toast.makeText(getApplicationContext(), "Product Added to Cart" , Toast.LENGTH_SHORT).show();


                                                    } else if (result.getresult().equals("AllreadyAdded")) {
                                                        // Toast.makeText(getApplicationContext(), "Product is Already Added", Toast.LENGTH_LONG).show();
                                                        // response
                                                        try {
                                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Prod_Activity.this);
                                                            builder1.setTitle("Already Added");
                                                            builder1.setMessage("Your product is Already Added to Cart!");

                                                            builder1.setIcon(R.drawable.exclamationmarkresize);
                                                            // builder1.show();
                                                            AlertDialog alert11 = builder1.create();

                                                            alert11.show();


                                                        } catch (Exception e) {
                                                            Log.i("error:", Objects.requireNonNull(e.getMessage()));
                                                            Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                                        }


                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error
                                                    Log.i("APIERROR", Objects.requireNonNull(error.getMessage()));
                                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<>();

                                            params.put("productId", strpid);
                                            params.put("userId", struserid);
                                            params.put("sellerid", strsellerid);
                                            return params;
                                        }

                                        public Map<String, String> getHeaders() {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("Content-Type", "application/x-www-form-urlencoded");
                                            return params;
                                        }
                                    };

                                    requestQueue.add(rRequest);


                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    } else {
                        proid = intent.getExtras().getInt("proid");
                        Intent login = new Intent(Prod_Activity.this, Login.class);
                        login.putExtra("proid", proid);
                        startActivity(login);

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Click listener on Submit btn
        Submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking if user is logged in
                Boolean Check_login = loginpref.getBoolean("loggedin", false);
                if (Check_login.equals(true)) {
                    //Values
                    rate = String.valueOf(mRatingBar.getRating());
                    Feedback = Feedback_TXT.getText().toString();
                    proid = intent.getExtras().getInt("proid");
                    final String strpid = String.valueOf(proid);
                    int userid = loginpref.getInt("userid", 0);
                    final String struserid = String.valueOf(userid);
                    String url = MainActivity2.hostinglink + "/home/SaveFeedback";
                    //
                    try {
                        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

                        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), "ON-Response", Toast.LENGTH_LONG).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("userid", struserid);
                                params.put("proid", strpid);
                                params.put("rating", rate);
                                params.put("feedback", Feedback);
                                return params;
                            }

                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                        request.add(rRequest);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            }
        });

        //Buy Now Button
        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean Check_login = loginpref.getBoolean("loggedin", false);
                if (Check_login.equals(true)) {

                    if (validateSpinner(spinner2, "Choose Color", "Color") == false) {
                        AlertDialog.Builder build = new AlertDialog.Builder(Prod_Activity.this);
                        build.setTitle("Choose Color");
                        build.setMessage("Please Select a Color");
                        build.setIcon(R.drawable.exclamationmarkresize);
                        // builder1.show();
                        AlertDialog alert = build.create();
                        alert.show();
                    } else if (validateSpinner(spinner1, "Choose Size", "Size") == false) {
                        AlertDialog.Builder build = new AlertDialog.Builder(Prod_Activity.this);
                        build.setTitle("Choose Size");
                        build.setMessage("Please Select a Size");
                        build.setIcon(R.drawable.exclamationmarkresize);
                        // builder1.show();
                        AlertDialog alert = build.create();
                        alert.show();
                    } else {
                        Intent buyNow = new Intent(Prod_Activity.this, DirectCheckout_Activity.class);
                        buyNow.putExtra("img", image);
                        buyNow.putExtra("name", Title);
                        buyNow.putExtra("price", strprice);
                        buyNow.putExtra("sellerId", strsellerid);
                        buyNow.putExtra("proId", strpid);
                        startActivity(buyNow);
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            }
        });


    }

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

    boolean validateSpinner(Spinner spinner, String error, String msg) {

        View selectedView = spinner.getSelectedView();
        if (selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals(msg)) {
                selectedTextView.setError(error);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }


}
