package com.demotxt.myapp.recyclerview.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.Shipping;
import com.demotxt.myapp.recyclerview.ownmodels.CheckConnection;
import com.demotxt.myapp.recyclerview.ownmodels.CustomInternetDialog;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBaseAdapter;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;


public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView listview;

  public    List<CartListBeanlist> Bean;
    private CartListBaseAdapter baseAdapter;
    private Button pay;
    private CheckConnection connection;
    private CustomInternetDialog dialog;
    private SharedPreferences loginpref;
    private String userid;
    public static ArrayList<CartListBeanlist> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        listview = (ListView) view.findViewById(R.id.listview);
        connection = new CheckConnection(getActivity());
        dialog = new CustomInternetDialog(getActivity());
        list = new ArrayList<CartListBeanlist>();
        //quntities=new ArrayList<String>();
        boolean is_connected = connection.CheckConnection();
        if (!is_connected) {
            dialog.showCustomDialog();
        }
        loginpref = getActivity().getSharedPreferences("loginpref", MODE_PRIVATE);
        // loginprefeditor = loginpref.edit();

        userid = String.valueOf(loginpref.getInt("userid", 0));

        Bean = new ArrayList<CartListBeanlist>();
        getconnection(hostinglink+"/Home/LoadUserCartProducts");

        pay = (Button) view.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Shipping.class);
                Bundle args = new Bundle();
                args.putSerializable("cartlist", (Serializable) list);
                i.putExtra("bundle", args);
                startActivity(i);
            }
        });

        return view;
    }

    public void getconnection(String url) {
        final RequestQueue request = Volley.newRequestQueue(getContext());

        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            StringResponceFromWeb result = null;
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            try {
                                result = gson.fromJson(response, StringResponceFromWeb.class);

                            } catch (Exception e) {
                                // Log.i(" ","User is not Login or have not carted any product");
                            }
                            try {
                                Bean = Arrays.asList(gson.fromJson(response, CartListBeanlist[].class));
                            } catch (JsonSyntaxException e) {
                                Log.i("User not login", "user not login or user have not carted any product");
                            }

                            if (result == null) {
                                list.addAll(Bean);
                                int index = 0;
                                for (CartListBeanlist cart : Bean
                                ) {
                                    Log.i("quantities", "index " + index + ":" + cart.getQuantity());
                                    index++;


                                }
                                index = 0;
                                for (CartListBeanlist cart : list
                                ) {
                                    Log.i("quantities", "index " + index + ":" + cart.getQuantity());
                                    index++;


                                }
                                try {
                                    Log.i("Been count", String.valueOf(Bean.size()));
                                    Toast.makeText(getContext(), "Beensize:" + Bean.size(), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "errorr:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                                setimageurl();

                                try {
                                    baseAdapter = new CartListBaseAdapter(getActivity(), Bean, 1) {
                                    };

                                    listview.setAdapter(baseAdapter);
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "error" + e.getMessage(), Toast.LENGTH_LONG).show();
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

                params.put("userid", userid);

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

    private void setimageurl() {
        int n = 0;
        for (CartListBeanlist i : Bean) {
            //  http://ahmedishtiaq1997-001-site1.ftempurl.com/
            i.setImage(hostinglink + i.getImage());
            // list.remove(n);
            Bean.set(n, i);
            n++;


        }
    }

}