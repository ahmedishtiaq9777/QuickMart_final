package com.demotxt.myapp.Quickmart.Cart_Fav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.activity.Prod_Activity;
import com.demotxt.myapp.Quickmart.fragment.CartFragment;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;


public class CartListBaseAdapter extends BaseAdapter {


    Context context;

    List<CartListBeanlist> Bean;
    List<CartListBeanlist> BeanTemp;
    private SharedPreferences favouritepref, loginpref;

    private SharedPreferences.Editor favouriteprefeditor;

    public Set<String> favids;
    private String proid, userid, finalQuantity;
    private int selectionid;
    private StringResponceFromWeb result;
    private String specId;
    List<String> quantities, proids;
    //List<String> seller_quantities;

    StringResponceFromWeb ResultForQuantitySave;


    private int number = 01;

    Typeface fonts1, fonts2;


    public CartListBaseAdapter(Context context, List<CartListBeanlist> bean, int number) {


        //  proids= Arrays.asList(new String[bean.size()]);
        // quantities=Arrays.asList(new String[bean.size()]);
        //  seller_quantities=Arrays.asList(new String[bean.size()]);

      /*  for(int index=0;index<bean.size();index++)
        {
            quantities.set(index,"1");
        }*/


        // for (String q:quantities) {
        //   q
        //}


        this.context = context;

        selectionid = number;
        this.BeanTemp = bean;
        initializearray();
        //   this.Bean = bean;
        int index = 0;
        for (CartListBeanlist i : Bean) {
            Log.i("proid " + index, ":" + i.getQuantity());
            index++;

        }


        //  cartlistpref=context.getSharedPreferences("cartprefs",MODE_PRIVATE);//get cartpreferences that contains cartitemlist
        favouritepref = context.getSharedPreferences("favpref", MODE_PRIVATE);
        loginpref = context.getSharedPreferences("loginpref", MODE_PRIVATE);

        userid = String.valueOf(loginpref.getInt("userid", 0));

        //  cartlistprefeditor=cartlistpref.edit();// this is to add stuff in preferences
        favouriteprefeditor = favouritepref.edit();
        //  cartids=cartlistpref.getStringSet("cartids",cartids);//get current product ids in cartprefferences
        favids = favouritepref.getStringSet("ids", favids);


    }

    public void initializearray() {
        Bean = new ArrayList<>();
        for (CartListBeanlist item : BeanTemp) {
            Bean.add(item);

        }
    }


    @Override
    public int getCount() {
        return Bean.size();
    }

    @Override
    public Object getItem(int position) {
        return Bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_list, null);

            viewHolder = new ViewHolder();


            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.cross = (ImageView) convertView.findViewById(R.id.cross);
            viewHolder.plus = (ImageView) convertView.findViewById(R.id.plus);
            viewHolder.minus = (ImageView) convertView.findViewById(R.id.minus);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.Quantity = (TextView) convertView.findViewById(R.id.prodQuantity);
            viewHolder.mCardView = convertView.findViewById(R.id.Cardview_Cart);

            viewHolder.title.setTypeface(fonts2);
            viewHolder.Quantity.setTypeface(fonts1);
            viewHolder.price.setTypeface(fonts2);

            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        final CartListBeanlist bean = (CartListBeanlist) getItem(position);

        // viewHolder.image.setImageResource(bean.getImage());
        Picasso.get().load(bean.getImage()).into(viewHolder.image);
        viewHolder.title.setText(bean.getTitle());

        String pricestr = String.valueOf(bean.getPrice());
        String userquantity = String.valueOf(bean.getQuantity());
        viewHolder.price.setText(pricestr);
        try {
            viewHolder.Quantity.setText(userquantity);

        } catch (Exception e) {
            Log.i("sellerQuantity not add", "error:" + e.getMessage());
        }
        //card on click listener
        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Prod_Activity.class);
                // passing data to the book activity
                intent.putExtra("Title",bean.getTitle());
                intent.putExtra("Description",bean.getDescription());
                intent.putExtra("Thumbnail",bean.getImage());
                intent.putExtra("price",(float)bean.getPrice());
                intent.putExtra("proid",bean.getId());
                intent.putExtra("sellerid",bean.getSellerid());

                // start the activity
                context.startActivity(intent);

            }
        });


        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proid = String.valueOf(bean.getId());// get product id from object convert it to string
                //  Toast.makeText(context,"proid:"+proid,Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"Position:"+position,Toast.LENGTH_SHORT).show();
                specId=String.valueOf(bean.getSpecificationid());
                int quantity_on_cart = Integer.parseInt(viewHolder.Quantity.getText().toString());/// quantity on view
                if (selectionid == 1) {
                    int Total_pro_quantity = bean.getSellerQuantity();
                    //            int Total_pro_quantity=Integer.parseInt(seller_quantities.get(position));         ///  Product over all quantity from database

                    if (Total_pro_quantity >= quantity_on_cart + 1) {
                        quantity_on_cart = quantity_on_cart + 1;
                        ChangeQuantityInCart(quantity_on_cart, bean, position, "Product Added");
                        /*
                        String str_quntity_on_cart=String.valueOf(quantity_on_cart);
                        bean.setQuantity(quantity_on_cart);
                       CartFragment.list.set(position,bean);
                       notifyDataSetChanged();
                     //   viewHolder.Quantity.setText(str_quntity_on_cart);
                        Log.i("Quantity","Quantity:"+str_quntity_on_cart);

                        SaveQuantityInDb("http://ahmedishtiaq1997-001-site1.ftempurl.com/home/SaveQuantityInCart",str_quntity_on_cart,proid);/// Intent intent = new Intent("custom-message");
                       */


                    } else {
                        Toast.makeText(context, "no more product", Toast.LENGTH_SHORT).show();
                    }
                    //    String fquantity= GetConnectionforquantity("http://ahmedishtiaq1997-001-site1.ftempurl.com/home/GetProductQuantity",proid,quantity);
                    /// viewHolder.Quantity.setText(fquantity);

                }

            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proid = String.valueOf(bean.getId());
                specId=String.valueOf(bean.getSpecificationid());
                //   Toast.makeText(context,"proid:"+proid,Toast.LENGTH_SHORT).show();
                // Toast.makeText(context,"Position:"+position,Toast.LENGTH_SHORT).show();
                int quantity_on_cart = Integer.parseInt(viewHolder.Quantity.getText().toString());
                if (selectionid == 1) {
                    int Total_pro_quantity = bean.getSellerQuantity();
                    if (quantity_on_cart > 1) {
                        quantity_on_cart = quantity_on_cart - 1;
                        ChangeQuantityInCart(quantity_on_cart, bean, position, "Product neglected");

                    } else {
                        //  quantity_on_cart=1;
                    }
                }

            }
        });

        // delete element from cartlist
        viewHolder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strid;
                    proid = String.valueOf(bean.getId());
                    if (selectionid == 1) {

                        getconnection(hostinglink + "/home/DeleteProductFromCart", proid,specId);


                        //CartFragment.list.remove(position);

                        try {
                            Bean.remove(position);
                        } catch (Exception e) {
                            Log.i("error in been remove", ":" + e.getMessage());
                        }

                        notifyDataSetChanged();


                    } else if (selectionid == 2) {
                        favouriteprefeditor.remove("ids");
                        favouriteprefeditor.commit();
                        strid = String.valueOf(proid);
                        favids.remove(strid);
                        favouriteprefeditor.putStringSet("ids", favids);
                        favouriteprefeditor.putBoolean(strid, false);
                        favouriteprefeditor.commit();
                        Bean.remove(position);
                        notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(context.getApplicationContext(), " Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });



        return convertView;
    }


    // for deleting product
    public void getconnection(String url, final String pid, final String spid) {
        final RequestQueue request = Volley.newRequestQueue(context);


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Responce on clickCross", "onResponse: " + response);

                        // Toast.makeText(context, response, Toast.LENGTH_LONG).show();


                        //  Toast.makeText(ShoppyProductListActivity.this, response, Toast.LENGTH_SHORT).show();


                        // response
                        //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                /*JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }*/
                params.put("userid", userid);
                params.put("proid", pid);
                params.put("specificationId",spid);

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

    // for saving user quantity
    public void SaveQuantityInDb(String url, final String Selected_Quantity, final String pid, final String msg, final String specificationid) {


        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";
            //    String url = "http://ahmedishtiaq1997-001-site1.ftempurl.com/Home/AddtoCart";

            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ResultForQuantitySave = gson.fromJson(response, StringResponceFromWeb.class);
                            if (ResultForQuantitySave.getresult().equals("SaveSuccessFully")) {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, ResultForQuantitySave.getresult(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.i("APIERROR", error.getMessage());
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("quantity", Selected_Quantity);
                    params.put("proid", pid);
                    params.put("userid", userid);
                    params.put("specificationId",specificationid);
 /*JSONArray jsonArray1= new JSONArray();
 JSONArray jsonArray2= new JSONArray();

                for (String  q:quantities) {
                    jsonArray1.put(q);
                }
                    for (String p:proids) {
                        jsonArray2.put(p);
                    }
                    params.put("quantities", jsonArray1.toString());
                params.put("proids",jsonArray2.toString());
            params.put("userid",userid);
            */


                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(rRequest);


        } catch (Exception e) {
            Toast.makeText(context, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }


    }


    public void ChangeQuantityInCart(int user_quantity, CartListBeanlist clist, int position, String message) {
        String str_quntity_on_cart = String.valueOf(user_quantity);
        clist.setQuantity(user_quantity);
        CartFragment.list.set(position, clist);
        Bean.set(position, clist);
        notifyDataSetChanged();
        //   viewHolder.Quantity.setText(str_quntity_on_cart);
        Log.i("Quantity", "Quantity:" + str_quntity_on_cart);

        SaveQuantityInDb(hostinglink + "/home/SaveQuantityInCart", str_quntity_on_cart, proid, message,specId);/// Intent intent = new Intent("custom-message");


    }

    private class ViewHolder {
        ImageView image;
        ImageView plus;
        ImageView minus;
        ImageView cross;
        TextView title;
        CardView mCardView;
        TextView price;

        TextView Quantity;


    }
}




