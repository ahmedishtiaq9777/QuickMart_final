package com.demotxt.myapp.recyclerview.shoppycartlist;

import android.app.Activity;
import android.content.Context;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.fragment.CartFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class CartListBaseAdapter extends BaseAdapter {

    Context context;

    List<CartListBeanlist> Bean;
    List<CartListBeanlist> BeanTemp;
    private SharedPreferences favouritepref,loginpref;

    private SharedPreferences.Editor favouriteprefeditor;
    public Set<String> cartids;
    public Set<String> favids;
    private String  proid,userid;
    private int selectionid;



    private int number = 01;

    Typeface fonts1,fonts2;





    public CartListBaseAdapter(Context context, List<CartListBeanlist> bean,int number) {


        this.context = context;

        selectionid=number;
        this.BeanTemp=bean;
        initializearray();
       // this.Bean = bean;
        cartids=new HashSet<String>();

      //  cartlistpref=context.getSharedPreferences("cartprefs",MODE_PRIVATE);//get cartpreferences that contains cartitemlist
        favouritepref=context.getSharedPreferences("favpref",MODE_PRIVATE);
        loginpref = context.getSharedPreferences("loginpref", MODE_PRIVATE);

        userid=  String.valueOf(loginpref.getInt("userid", 0));

        //  cartlistprefeditor=cartlistpref.edit();// this is to add stuff in preferences
        favouriteprefeditor=favouritepref.edit();
      //  cartids=cartlistpref.getStringSet("cartids",cartids);//get current product ids in cartprefferences
        favids=favouritepref.getStringSet("ids",favids);




    }
    public void initializearray(){
        Bean = new ArrayList<>();
        for (CartListBeanlist item: BeanTemp) {
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

        //fonts1 =  Typeface.createFromAsset(context.getAssets(),"fonts/MavenPro-Regular.ttf");

      //  fonts2 = Typeface.createFromAsset(context.getAssets(), "fonts/MavenPro-Regular.ttf");

        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_list,null);

            viewHolder = new ViewHolder();


            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.cross = (ImageView)convertView.findViewById(R.id.cross);

            viewHolder.title = (TextView)convertView.findViewById(R.id.title);

            viewHolder.price = (TextView)convertView.findViewById(R.id.price);

            viewHolder.text = (TextView)convertView.findViewById(R.id.text);



            viewHolder.title.setTypeface(fonts2);

            viewHolder.text.setTypeface(fonts1);
            viewHolder.price.setTypeface(fonts2);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }







        final CartListBeanlist bean = (CartListBeanlist)getItem(position);

       // viewHolder.image.setImageResource(bean.getImage());
        Picasso.get().load(bean.getImage()).into(viewHolder.image);
        viewHolder.title.setText(bean.getTitle());

      String pricestr=String.valueOf(bean.getPrice());
        viewHolder.price.setText(pricestr);

        // delete element from cartlist
        viewHolder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strid;
                    proid =     String.valueOf(bean.getId());
                   if(selectionid==1) {

                       getconnection("http://ahmedishtiaq1997-001-site1.ftempurl.com/home/DeleteProductFromCart");






















                       //cartlistprefeditor.remove("cartids");
                       //cartlistprefeditor.commit();
                      //  strid = String.valueOf(proid);
                      // cartids.remove(strid);
                      // cartlistprefeditor.putStringSet("cartids", cartids);
                       //cartlistprefeditor.commit();
                       Bean.remove(position);
                       notifyDataSetChanged();

                   }else if(selectionid==2){
                       favouriteprefeditor.remove("ids");
                       favouriteprefeditor.commit();
                        strid=String.valueOf(proid);
                        favids.remove(strid);
                        favouriteprefeditor.putStringSet("ids",favids);
                        favouriteprefeditor.putBoolean(strid,false);
                       favouriteprefeditor.commit();
                        Bean.remove(position);
                        notifyDataSetChanged();
                   }

               /* for (CartListBeanlist obj:Bean) {
                    Log.i("Item "+obj.getId(), "Title "+obj.getTitle());
                }*/
             // CartListBeanlist obj= Bean.get(position);
               // Toast.makeText(context.getApplicationContext()," Title:"+obj.getTitle(),Toast.LENGTH_SHORT).show();


             //   Toast.makeText(context.getApplicationContext()," position:"+position,Toast.LENGTH_SHORT).show();
                 //   CartListBaseAdapter.this.notifyAll();



                }catch (Exception e){
                   Toast.makeText(context.getApplicationContext()," Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }




            }
        });


//        number = 01;
//        viewHolder.text.setText(""+number);
//
//        final ViewHolder finalViewHolder = viewHolder;
//        viewHolder.min.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (number == 1){
//                    finalViewHolder.text.setText("" + number);
//            }
//
//                if (number > 1){
//
//                    number = number -1;
//                    finalViewHolder.text.setText(""+number);
//                }
//
//            }
//        });
//
//        final ViewHolder finalViewHolder1 = viewHolder;
//        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (number == 10) {
//                    finalViewHolder1.text.setText("" + number);
//                }
//
//                if (number < 10) {
//
//                    number = number + 1;
//                    finalViewHolder1.text.setText("" + number);
//
//                }
//
//
//
//
//            }
//        });




        return convertView;
    }




    public  void   getconnection(String url) {
        final RequestQueue request = Volley.newRequestQueue(context);


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Responce on clickCross", "onResponse: "+response );

                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();



                        //  Toast.makeText(ShoppyProductListActivity.this, response, Toast.LENGTH_SHORT).show();


                        // response
                        //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                       Toast.makeText(context,"Error:"+error.getMessage(),  Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }
        )  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                /*JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }*/
                params.put("userid",userid);
                params.put("proid",proid);

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




    private class ViewHolder{
        ImageView image;
        ImageView cross;
        TextView title;

        TextView price;

        TextView text;















    }
}




