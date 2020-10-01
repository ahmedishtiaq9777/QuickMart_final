package com.demotxt.myapp.Quickmart.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.CategoryFragments.Catkids;
import com.demotxt.myapp.Quickmart.ownmodels.IOHelper;
import com.demotxt.myapp.Quickmart.ownmodels.Prod;
import com.demotxt.myapp.Quickmart.ownmodels.ProductSpecification;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.ownmodels.UserViewLog;
import com.demotxt.myapp.Quickmart.utils.Tools;
import com.demotxt.myapp.Quickmart.utils.ViewAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class Prod_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, price;
    private ImageView img;
    private FloatingActionButton floatingActionButton;
    private SharedPreferences loginpref;
    private int proid;
    private int sellerid;
    public int User_id;
    StringResponceFromWeb result;
    CoordinatorLayout lyt_parent;
    public Set<String> cartids;
    private ImageButton bt_toggle_reviews, bt_toggle_description;
    private View lyt_expand_reviews, lyt_expand_description;
    private NestedScrollView nested_scroll_view;
    //For Review Layout
    private EditText Feedback_TXT;
    private Button Submit_Btn, BuyNow;
    private AppCompatRatingBar mRatingBar;
    public String rate, Feedback;
    public String Title, Description, image, strsellerid, strpid,category;
    private View layout;
    public List<ProductSpecification> sizecolorlist;
    public List<String> SizeList, colorList;
    public ArrayAdapter<String> Colorspinner;
    public ArrayAdapter<String> Sizespinner;
    public Spinner spinner1, spinner2;
    public String selectedcolor, selectedsize ,selectedItemText;
    public IOHelper ioHelper;
    List<UserViewLog> userViewLogList,userViewLogList2;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_);
        ioHelper=new IOHelper(getApplicationContext());
        lyt_parent = findViewById(R.id.parent_view);

        sizecolorlist = new ArrayList<>();
        SizeList = new ArrayList<>();
        colorList = new ArrayList<>();
        userViewLogList=new ArrayList<UserViewLog>();
        userViewLogList2=new ArrayList<UserViewLog>();
        // For custom toast
        final LayoutInflater inflater = getLayoutInflater();
        try {
            layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));//for product added :to make custom toast with tick mark

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Prodactivity", "error" + e.getMessage());

        }
        //
        try {
            proid = getIntent().getExtras().getInt("proid");
        }catch (Exception e){
            e.printStackTrace();
        }

        //UI THREAD

                getsizecolor(hostinglink + "/Home/getsizecolors");



        // Size Spinner
        spinner1 = (Spinner) findViewById(R.id.sizeSpinner);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                selectedsize = selectedItemText;
                if (position >= 0) {
                    // Notify the selected item text
                    Snackbar.make(lyt_parent,"Selected : " + selectedItemText,Snackbar.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Color Spinner
        spinner2 = (Spinner) findViewById(R.id.colorSpinner);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);

                selectedcolor = selectedItemText;

                SizeList.clear();



                        getsizeswithcolor(selectedItemText);


                Sizespinner = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, SizeList) {
                    @Override
                    public boolean isEnabled(int position) {
                      return  true;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        tv.setTextColor(Color.BLACK);

                                 /*   if(position == 0){
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY);
                                    }
                                    else {
                                        tv.setTextColor(Color.BLACK);
                                    }*/
                        return view;
                    }
                };


                Sizespinner.setDropDownViewResource(R.layout.item_spinner);
                spinner1.setAdapter(Sizespinner);

                if (position >= 0) {
                    // Notify the selected item text
                    Snackbar.make(lyt_parent,"Selected : " + selectedItemText,Snackbar.LENGTH_SHORT).show();
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
        category=intent.getExtras().getString("category");
        User_id=loginpref.getInt("userid", 0);
        final String strprice = String.valueOf(pRise);
        Update_if_category_exists(category);

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
                                    String url = hostinglink + "/Home/AddtoCart";

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
                                            params.put("color", selectedcolor);
                                            params.put("size", selectedsize);
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

                    } else {
                        proid = intent.getExtras().getInt("proid");
                        Intent login = new Intent(Prod_Activity.this, Login.class);
                        login.putExtra("proid", proid);
                        startActivity(login);

                        finish();

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
                                        Snackbar.make(lyt_parent,"Success",Snackbar.LENGTH_SHORT).show();
                                        //clear rating and text
                                        mRatingBar.setRating(0);
                                        Feedback_TXT.setText("");
                                        //
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

                    finish();
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
                        buyNow.putExtra("color",selectedcolor);
                        buyNow.putExtra("size",selectedsize);
                        buyNow.putExtra("proId", strpid);
                        buyNow.putExtra("size",selectedsize);
                        buyNow.putExtra("color",selectedcolor);
                        startActivity(buyNow);
                        //
                        finish();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    public void getsizecolor(String url) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            sizecolorlist = Arrays.asList(gson.fromJson(response, ProductSpecification[].class));

                            for (ProductSpecification p : sizecolorlist
                            ) {
                                colorList.add(p.getProductColor());
                                // SizeList.add(p.getProductSize());
                            }

                            Sizespinner = new ArrayAdapter<String>(
                                    getApplicationContext(), R.layout.item_spinner, SizeList) {
                                @Override
                                public boolean isEnabled(int position) {
                                 /*   if (position == 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }*/
                                 return  true;
                                }

                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    TextView tv = (TextView) view;
                                    tv.setTextColor(Color.BLACK);

                                 /*   if(position == 0){
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY);
                                    }
                                    else {
                                        tv.setTextColor(Color.BLACK);
                                    }*/
                                    return view;
                                }
                            };
                            Sizespinner.setDropDownViewResource(R.layout.item_spinner);
                            spinner1.setAdapter(Sizespinner);

                            Colorspinner = new ArrayAdapter<String>(
                                    getApplicationContext(), R.layout.item_spinner, colorList) {
                                @Override
                                public boolean isEnabled(int position) {
                                    return true;
                                    /* if(position == 0)
                                    {
                                        return false;
                                    }
                                    else
                                    {
                                        return true;
                                    }*/
                                }

                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    TextView tv = (TextView) view;
                               /*     if(position == 0){
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY);
                                    }
                                    else {
                                        tv.setTextColor(Color.BLACK);
                                    }*/
                                    tv.setTextColor(Color.BLACK);
                                    return view;
                                }
                            };
                            Colorspinner.setDropDownViewResource(R.layout.item_spinner);
                            spinner2.setAdapter(Colorspinner);


                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(getContext(),  Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("proid", String.valueOf(proid));


                return params;
            }

            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };


        request.add(rRequest);


    }
    public void  Updatefile(String category){


    }
    public String  Readfile(String  _category)
    {try {
        String filename2 = "UserLog.json";
        InputStream inputStream = getApplicationContext().openFileInput(filename2);
       String strdata= ioHelper.stringFromStream(inputStream);
return strdata;




    }catch (FileNotFoundException e)
    {

        List<UserViewLog> TEMPLIST=new ArrayList<UserViewLog>();
        UserViewLog userViewLog=new UserViewLog();
        userViewLog.setUserid(String.valueOf(User_id));
        userViewLog.setCategory(_category);
        userViewLog.setNOV("1");
        TEMPLIST.add(userViewLog);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String strlist=gson.toJson(TEMPLIST);
        ioHelper.writeToFile(strlist);

        Toast.makeText(getApplicationContext(),"file is created and data is saved",Toast.LENGTH_SHORT).show();


        String strdata = ioHelper.stringFromFile();


        e.printStackTrace();

        return  strdata;




    }
    }
    public  boolean Update_if_category_exists(String _category)
    {
     //   Readfile(_category);
//userViewLogList2=new ArrayList<>();
//userViewLogList=new ArrayList<>();
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson=gsonBuilder.create();
String strdata=ioHelper.stringFromFile();
if(strdata!=""&&strdata!=null) {
    userViewLogList.clear();
    userViewLogList = Arrays.asList(gson.fromJson(strdata, UserViewLog[].class));
    userViewLogList2.clear();
    userViewLogList2.addAll(userViewLogList);


    int index = 0;
try {
    for (UserViewLog u : userViewLogList2) {

        if (u.getCategory().equals(category)) {
            int nov = Integer.parseInt(u.getNOV());
            nov = nov + 1;
            u.setNOV(String.valueOf(nov));
            userViewLogList2.set(index, u);

            String updatedlist = gson.toJson(userViewLogList2);
            ioHelper.writeToFile(updatedlist);
           /* for (UserViewLog userViewLog : userViewLogList2) {
                Log.i("Prod_Activity", "category: " + userViewLog.getCategory());
                Log.i("Prod_Activity", "NOV: " + userViewLog.getNOV());
*/
            showdatainlog();
            return true;

        }
        index++;

    }
}catch (NullPointerException e)
{

    e.printStackTrace();
}

userViewLogList= Arrays.asList(gson.fromJson(strdata,UserViewLog[].class));
    userViewLogList2.clear();
    userViewLogList2.addAll(userViewLogList);
    for (UserViewLog u :userViewLogList2) {
        Log.i("test","category"+u.getCategory());
        Log.i("test","Nov"+u.getNOV());

    }
    UserViewLog userViewLog=new UserViewLog();
    userViewLog.setUserid(String.valueOf(User_id));
    userViewLog.setCategory(category);
    userViewLog.setNOV("1");

    userViewLogList2.add(userViewLog);
   String strlog= gson.toJson(userViewLogList2);
   ioHelper.writeToFile(strlog);

    for (UserViewLog u :userViewLogList2) {
        Log.i("afteradd","category"+u.getCategory());
        Log.i("afteradd","Nov"+u.getNOV());

    }
showdatainlog();
   // String currentdata=ioHelper.stringFromFile();
   // Log.i("after:",currentdata);

/*    showdatainlog();

    String currentdata=ioHelper.stringFromFile();
userViewLogList=new ArrayList<>();
userViewLogList2=new ArrayList<>();
     userViewLogList  =Arrays.asList(gson.fromJson(currentdata,UserViewLog[].class));
     userViewLogList2.addAll(userViewLogList);
/// creatingobject
     UserViewLog userViewLog=new UserViewLog();
     userViewLog.setCategory(category);
     userViewLog.setNOV("1");
     userViewLog.setUserid(String.valueOf(User_id));
     ///////////////// append data
     userViewLogList2.add(userViewLog);
     //converting to json string
    String updatedlist = gson.toJson(userViewLogList2);
    //jsut for showing

/////////////
    for (UserViewLog userLog:userViewLogList2) {
        Log.i("Prod_Activity","category: "+userLog.getCategory());
        Log.i("Prod_Activity","NOV: "+userLog.getNOV());


    }
    ioHelper.writeToFile(updatedlist);/// updating to file

    showdatainlog();
    return true;*/
    return true;
}else {
    UserViewLog n=new UserViewLog();
    n.setCategory(category);
    n.setNOV("1");
    n.setUserid(String.valueOf(User_id));

    userViewLogList2.add(n);


    ioHelper.createbydefault(userViewLogList2);
    showdatainlog();
    return true;
}

    }
public  void showdatainlog(){
GsonBuilder gsonBuilder=new GsonBuilder();
Gson gson=gsonBuilder.create();
       String strdata= ioHelper.stringFromFile();
userViewLogList2= new ArrayList<>();
userViewLogList= new ArrayList<>();
      userViewLogList= Arrays.asList(gson.fromJson(strdata,UserViewLog[].class));
      userViewLogList2.addAll(userViewLogList);
    for (UserViewLog userLog:userViewLogList2) {
        Log.i("Prod_Activity(sh)","category: "+userLog.getCategory());
        Log.i("Prod_Activity(sh)","NOV: "+userLog.getNOV());


    }

}
    public void getsizeswithcolor(String color) {
        SizeList = null;
        SizeList = new ArrayList<>();
        for (ProductSpecification specification : sizecolorlist
        ) {


            if (specification.getProductColor().equals(color)) {

                SizeList.add(specification.getProductSize());
            }

        }
        for (String size : SizeList) {
            Log.i("proid:" + proid, "Size:" + size);

        }


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
        if (selectedView != null && selectedView instanceof TextView) {
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
