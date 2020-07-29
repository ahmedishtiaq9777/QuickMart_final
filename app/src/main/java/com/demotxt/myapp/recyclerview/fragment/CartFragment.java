package com.demotxt.myapp.recyclerview.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.Login;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.demotxt.myapp.recyclerview.activity.Shipping;
import com.demotxt.myapp.recyclerview.ownmodels.CheckConnection;
import com.demotxt.myapp.recyclerview.ownmodels.CustomInternetDialog;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBaseAdapter;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.Serializable;


import java.lang.reflect.Type;
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

  public    List<CartListBeanlist> Bean;
    private CartListBaseAdapter baseAdapter;
   // public Set<String> cartids;
   // private SharedPreferences cartprefs;
    //private SharedPreferences.Editor cartprefeditor;
    private Button pay;
    private CheckConnection connection;
    private CustomInternetDialog dialog;
    private SharedPreferences  loginpref;
    //private SharedPreferences.Editor  loginprefeditor;
    private String  userid;
    public static  ArrayList<CartListBeanlist> list;

   // private List<String> quntities;
   /* public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {           // this hits when ever user click on plus button
            // Get extra data included in the Intent

          //  quantity = intent.getIntExtra("quantity",0);
           // position = intent.getIntExtra("position",0);
            Bundle args = intent.getBundleExtra("allquantitiesbundle");
try{
    quntities = (List<String>) args.getSerializable("quantitylist");    // getting  quantities list from bundle in broadcast

}catch (Exception e)
{
    Log.i("ERROR",e.getMessage());
Toast.makeText(getContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
}

           int index=0;
            for (CartListBeanlist item: list) {                                            // assignning quantities fro that list to real cartlistbaselist
                int tquant=Integer.parseInt(quntities.get(index));
                item.setQuantity(tquant);

                list.set(index,item);
                index++;
            }
          //  Log.i("On Receive", "UpdatedQuantity: "+quantity);
            //Log.i("On Receive", "Position: "+position);
            int i=0;
            for (String q:quntities) {
                Log.i("On Receive", "Quantity "+i+": "+q);
                i++;
            }



           // CartListBeanlist cartListBeanlist=list.get(position);
           // cartListBeanlist.setQuantity(quantity);
            //list.set(position,cartListBeanlist);
          //  Toast.makeText(getContext(),"position:"+position+"..quantity:"+quantity,Toast.LENGTH_SHORT).show();
           // Toast.makeText(MainActivity.this,ItemName +" "+qty ,Toast.LENGTH_SHORT).show();
        }
    };*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        listview = (ListView) view.findViewById(R.id.listview);

      //  LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
        //        new IntentFilter("custom-message"));

        connection=new CheckConnection(getActivity());
        dialog=new CustomInternetDialog(getActivity());
list=new ArrayList<CartListBeanlist>();
//quntities=new ArrayList<String>();
        boolean is_connected=connection.CheckConnection();
        if(!is_connected)
        {
            dialog.showCustomDialog();
        }
        loginpref = getActivity().getSharedPreferences("loginpref", MODE_PRIVATE);
       // loginprefeditor = loginpref.edit();

        userid=  String.valueOf(loginpref.getInt("userid", 0));
       /* if(userid.equals(0))
        {
            Intent login=new Intent(getActivity(), Login.class);
            startActivity(login);
        }*/
      //  cartids=new HashSet<String>();
     //   cartprefs=getContext().getSharedPreferences("cartprefs",MODE_PRIVATE);
     //   cartprefeditor=cartprefs.edit();
      //  cartids=cartprefs.getStringSet("cartids",cartids);
        Bean = new ArrayList<CartListBeanlist>();
        getconnection("http://ahmedishtiaq1997-001-site1.ftempurl.com/Home/LoadUserCartProducts");

        pay=(Button)view.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Shipping.class);

               Bundle args = new Bundle();


                args.putSerializable("cartlist",(Serializable) list);
//GsonBuilder builder=new GsonBuilder();
//Gson gson=builder.create();
           //     Type listOfTestObject = new TypeToken<List<CartListBeanlist>>(){}.getType();
             //   String s = gson.toJson(list, listOfTestObject);
                i.putExtra("bundle",args);

    startActivity(i);



  //  Toast.makeText(getContext(),"error: "+e.getMessage(),Toast.LENGTH_SHORT).show();



            }
        });

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
                            StringResponceFromWeb result=null;
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            try {
                                 result = gson.fromJson(response, StringResponceFromWeb.class);

                            }catch (Exception e)
                            {
                               // Log.i(" ","User is not Login or have not carted any product");
                            }
                            try {
                                Bean= Arrays.asList(gson.fromJson(response, CartListBeanlist[].class));
                            }catch (JsonSyntaxException e)
                            {
                                Log.i("User not login","user not login or user have not carted any product");
                            }

if(result==null) {
    list.addAll(Bean);
int index=0;
    for (CartListBeanlist cart:Bean
         ) {  Log.i("quantities","index "+index+":"+cart.getQuantity());
         index++;


    }
    index=0;
    for (CartListBeanlist cart:list
    ) {  Log.i("quantities","index "+index+":"+cart.getQuantity());
        index++;


    }

    Log.i("Been count", String.valueOf(Bean.size()));
    Toast.makeText(getContext(), "Beensize:" + Bean.size(), Toast.LENGTH_LONG).show();
                          /*  for (CartListBeanlist i:  list) {
                                Log.i("oks", " "+i.getTitle());

                            }*/


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
    try{
    baseAdapter = new CartListBaseAdapter(getActivity(), Bean, 1) {
    };


    listview.setAdapter(baseAdapter);
}catch (Exception e)
{
    Toast.makeText(getContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
}

}
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();
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

                params.put("userid",userid);

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
          //  http://ahmedishtiaq1997-001-site1.ftempurl.com/
            i.setImage("http://ahmedishtiaq1997-001-site1.ftempurl.com" + i.getImage());
            // list.remove(n);
            Bean.set(n,i);
            n++;


        }
    }

}