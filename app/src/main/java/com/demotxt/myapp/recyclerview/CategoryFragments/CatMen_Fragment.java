package com.demotxt.myapp.recyclerview.CategoryFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.TabsBasic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatMen_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<CatMen> ProdMen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_cat_men_, container, false);
        mRecyclerView = rootview.findViewById(R.id.Rv_CatMen);
        ProdMen = new ArrayList<>();


        //TODO Add Data in The Recycler Views;

        TabsBasic activity=(TabsBasic) getActivity();  // get activity data
        int sid= activity.getuserid();
        Log.i("Seller id",  String.valueOf(sid));
        String userid=String.valueOf(sid);
        String url="http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getprowithsellerid";
        getconnection(url,userid);





        return rootview;
    }
    public  void   getconnection(String url, final String seller_id) {
        final RequestQueue request = Volley.newRequestQueue(getContext());


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //
                        try {
                          //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                          //  Log.i("responce", "onResponse: "+response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProdMen = Arrays.asList(gson.fromJson(response, CatMen[].class));
                            setimageurl();
                            setadapterRecyclerView();

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("sellerid",seller_id);
                params.put("category","Men");

                // JSONArray jsonArray= new JSONArray();
                // jsonArray.put(sid);
                // params.put("sellerid",jsonArray.toString());
              /*  JSONArray jsonArray= new JSONArray();
                for (String  i:) {
                    jsonArray.put(i);
                }
                params.put("idsarray",jsonArray.toString());
                *?
               */

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
    private  void  setadapterRecyclerView()
    {
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mAdapter = new CatMen_Adapter(getActivity(),ProdMen);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private  void setimageurl(){
        int n = 0;
        for (CatMen i : ProdMen) {
            i.setThumbnail("http://ahmedishtiaq9778-001-site1.ftempurl.com" + i.getThumbnail());
            // list.remove(n);
            ProdMen.set(n,i);
            n++;


        }
    }
}
