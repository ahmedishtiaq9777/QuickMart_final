package com.demotxt.myapp.recyclerview.productlist;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppyProductListActivity extends AppCompatActivity {

    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;


    private ExpandableHeightListView listview;
    private ArrayList<BeanclassList> Bean;
    private ListviewAdapter baseAdapter;
    private ArrayList<String> images;

    ImageView gridviewicon,listviewicon;


   // private int[] IMAGE = {R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo};
  //  private String[] TITLE = {"Casual Sleeveless", "Short Sleeve ", "Sleeve Graphic Print", "Roundneck Sleeve", "Casual Sleeveless", "Short Sleeve "};
   // private String[] PRICE = {"Rs. 1,499", "Rs. 1,899", "Rs. 1,299", "Rs. 1,199", "Rs. 1,499", "Rs. 1,899"};
   // private String[] CUTPRICE = {"Rs. 1,699", "Rs. 1,999", "Rs. 1,599", "Rs. 1,399", "Rs. 1,699", "Rs. 1,999"};
   // private String[] DISCOUNT = {"10%", "14%", "20%", "25%","10%", "14%"};
   // private String[] RATING = {"(1231)", "(2331)", "(2321)", "(3213)","(4322)", "(2432)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);



        gridview = (ExpandableHeightGridView)findViewById(R.id.gridview);
        beanclassArrayList= new ArrayList<Beanclass>();
        getconnection("http://ahmedishtiaqbutt-001-site1.atempurl.com/Home/getproducts/");

//              ********GRIDVIEW***********






//        /        ********LISTVIEW***********





//        ***********Grid - list view **********

        gridviewicon = (ImageView)findViewById(R.id.gridviewicon);
        listviewicon = (ImageView)findViewById(R.id.listviewicon);


        gridviewicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listviewicon.setVisibility(View.VISIBLE);
                gridviewicon.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                gridview.setVisibility(View.GONE);

            }
        });

        listviewicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listviewicon.setVisibility(View.GONE);
                gridviewicon.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                gridview.setVisibility(View.VISIBLE);



            }
        });



    }

    public  void   getconnection(String url){



        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";



            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                          //  Toast.makeText(ShoppyProductListActivity.this, response, Toast.LENGTH_SHORT).show();
                            try {
                                 images=new ArrayList<String>();
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject product = array.getJSONObject(i);
                                    String img=product.getString("productImage");
                                    img="http://ahmedishtiaqbutt-001-site1.atempurl.com"+img;
                                    if(img!=null) {
                                        images.add(img);
                                    }

                                }
                                setGridview();
                                setListview();


                            } catch (JSONException e) {
                                e.printStackTrace();

                                Toast.makeText(getApplicationContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            // response
                            //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    Intent i =getIntent();
                    String email=i.getStringExtra("email");
                    String password=i.getStringExtra("password");
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(rRequest);


        }catch (Exception E)
        {
            Toast.makeText(getApplicationContext(),"Error: "+E.getMessage(),Toast.LENGTH_SHORT).show();
        }















    }
    void setGridview()
    {
        for(int i=0;i<images.size();i++)
        {
            Beanclass beanclass=new Beanclass(images.get(i),"Casual Sleeveless","Rs. 1,499","Rs. 1,699","10%","1231)");
            beanclassArrayList.add(beanclass);

        }

       /* for (int i= 0; i< IMAGE.length; i++) {

            Beanclass beanclass = new Beanclass(IMAGE[i], TITLE[i], PRICE[i], CUTPRICE[i], DISCOUNT[i], RATING[i]);
            beanclassArrayList.add(beanclass);

        }*/
        gridviewAdapter = new GridviewAdapter(this,ShoppyProductListActivity.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);



    }
    void setListview()
    {
        listview = (ExpandableHeightListView)findViewById(R.id.listview);


        Bean = new ArrayList<BeanclassList>();

        for (int i= 0; i< images.size(); i++){


            BeanclassList beanclassList=new BeanclassList(images.get(i),"Casual Sleeveless","Rs. 1,499","Rs. 1,699","10%","1231)");
            Bean.add(beanclassList);




        }

        baseAdapter = new ListviewAdapter(this,ShoppyProductListActivity.this, Bean) {
        };

        listview.setAdapter(baseAdapter);



    }
}
