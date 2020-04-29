package com.demotxt.myapp.recyclerview.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.ownmodels.r3;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBaseAdapter;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment extends Fragment {
    private ListView listview;
    private Set<String> ids;
    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;


    Typeface fonts1, fonts2;
    private List<CartListBeanlist> Bean;
    private CartListBaseAdapter baseAdapter;

    //private int[] IMAGE = {R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo,
         //   R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo};

  //  private String[] TITLE = {"Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set"};


   // private String[] PRICE = {"$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00"};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.favoritefragment, container, false);
        listview = (ListView) view.findViewById(R.id.listview);
        ids= new HashSet<String>();
        Bean=new ArrayList<>();


        cartpreferrence =   getContext().getSharedPreferences("favpref", MODE_PRIVATE);
 //
       ids=cartpreferrence.getStringSet("ids",ids);


       getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getproductswithproId");
        //
      /*  Bean = new ArrayList<CartListBeanlist>();

        for (int i = 0; i < TITLE.length; i++) {

            CartListBeanlist bean = new CartListBeanlist(IMAGE[i], TITLE[i], PRICE[i]);
            Bean.add(bean);

        }



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

                                baseAdapter = new CartListBaseAdapter(getActivity(), Bean,2) {
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
//
//                       Toast.makeText(getContext(),  Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
                )  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                JSONArray jsonArray= new JSONArray();
                for (String  i:ids) {
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
