package com.demotxt.myapp.recyclerview.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.CategoryFragments.CatKids_Fragment;
import com.demotxt.myapp.recyclerview.CategoryFragments.Catkids;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.activity.Payment;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListActivity;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBaseAdapter;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class CartFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView listview;

    Typeface fonts1, fonts2;

  //  private int[] IMAGE = {R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo,
       //     R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo};

    //private String[] TITLE = {"Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set"};


  //  private String[] PRICE = {"$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00"};
    private List<CartListBeanlist> Bean;
    private CartListBaseAdapter baseAdapter;
    public Set<String> cartids;
    private SharedPreferences cartprefs;
    private SharedPreferences.Editor cartprefeditor;
    private Button pay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_cart, container, false);
        listview = (ListView) view.findViewById(R.id.listview);
        pay=(Button)view.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Payment.class);
                startActivity(i);
            }
        });

        cartids=new HashSet<String>();
        cartprefs=getContext().getSharedPreferences("cartprefs",MODE_PRIVATE);
        cartprefeditor=cartprefs.edit();
cartids=cartprefs.getStringSet("cartids",cartids);
        Bean = new ArrayList<>();
getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getproductswithproId");
       /* for (int i = 0; i < TITLE.length; i++) {

            CartListBeanlist bean = new CartListBeanlist(IMAGE[i], TITLE[i], PRICE[i]);
            Bean.add(bean);

        }


        baseAdapter = new CartListBaseAdapter(getActivity(), Bean) {
        };

        listview.setAdapter(baseAdapter);
*/

        // Inflate the layout for this fragment
        return view;
    }
    public  void   getconnection(String url) {
        final RequestQueue request = Volley.newRequestQueue(getContext());


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                             Bean= Arrays.asList(gson.fromJson(response, CartListBeanlist[].class));
                             setimageurl();

                          /*  JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                String img = product.getString("productImage");
                                String title = product.getString("title");
                                //String description = product.getString("description");
                                //String category = product.getString("category");
                                double price = product.getDouble("price");
                                //  int id = product.getInt("productId");
                                img="http://ahmedishtiaq9778-001-site1.ftempurl.com"+img;
                                Bean.add(new CartListBeanlist(img,title,price));



                            }*/

                            baseAdapter = new CartListBaseAdapter(getActivity(), Bean) {
                            };

                            listview.setAdapter(baseAdapter);


                        } catch (Exception e) {
                            // Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        //  Toast.makeText(ShoppyProductListActivity.this, response, Toast.LENGTH_SHORT).show();


                        // response
                        //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

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
        )  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }
                params.put("idsarray",jsonArray.toString());

                //  params.p

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };





        request.add(rRequest);


    }
    private  void setimageurl(){
        int n = 0;
        for (CartListBeanlist i : Bean) {
            i.setImage("http://ahmedishtiaq9778-001-site1.ftempurl.com" + i.getImage());
            // list.remove(n);
            Bean.set(n,i);
            n++;


        }
    }

}
