package com.demotxt.myapp.recyclerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.adapter.RecyclerView3;
import com.demotxt.myapp.recyclerview.adapter.RecyclerViewAdapter;
import com.demotxt.myapp.recyclerview.adapter.RecyclerViewProdAdapter;
import com.demotxt.myapp.recyclerview.ownmodels.r3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<Book> lstBook2;
    List<Prod> Book22;
    List<r3> mTrends;
     View view;
    View v2;
    ViewFlipper viewFlipper;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // View view2=inflater.inflate(R.layout.homefragment,null);
       // viewFlipper=(ViewFlipper)view2.findViewById(R.id.flipper);





        lstBook2 = new ArrayList<>();
        Book22 = new ArrayList<>();
        mTrends = new ArrayList<>();
        getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getrecommendedpro/",1);


        //Data for view 1


     /*   lstBook2.add(new Book("Toddler suit","Kids","Description",R.drawable.baby));
        lstBook2.add(new Book("Black shoes","Shoes","Description",R.drawable.shoes));
        lstBook2.add(new Book("Dress","Women","Description",R.drawable.female));
        lstBook2.add(new Book("Designer Jacket"," Men","Description",R.drawable.jacket));
        lstBook2.add(new Book("Toddler suit","Kids","Description",R.drawable.baby));
        lstBook2.add(new Book("Black shoes","Shoes","Description",R.drawable.shoes));
        lstBook2.add(new Book("Dress","Women","Description",R.drawable.female));
        lstBook2.add(new Book("Designer Jacket","Men","Description",R.drawable.jacket));
        lstBook2.add(new Book("Toddler suit","Kids","Description",R.drawable.baby));
        lstBook2.add(new Book("Black shoes","Shoes","Description",R.drawable.shoes));
        lstBook2.add(new Book("Dress","Women","Description",R.drawable.female));
        lstBook2.add(new Book("Designer Jacket","Men","Description",R.drawable.jacket));*/

        //Data for view 2

        getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getNewpro/",2);

        /*
        Book22.add(new Prod("Toddler suit","Kids","Description",R.drawable.baby));
        Book22.add(new Prod("Black shoes","Shoes","Description",R.drawable.shoes));
        Book22.add(new Prod("Dress","Women","Description",R.drawable.female));
        Book22.add(new Prod("Designer Jacket"," Men","Description",R.drawable.jacket));
        Book22.add(new Prod("Toddler suit","Kids","Description",R.drawable.baby));
        Book22.add(new Prod("Black shoes","Shoes","Description",R.drawable.shoes));
        Book22.add(new Prod("Dress","Women","Description",R.drawable.female));
        Book22.add(new Prod("Designer Jacket","Men","Description",R.drawable.jacket));
        Book22.add(new Prod("Toddler suit","Kids","Description",R.drawable.baby));
        Book22.add(new Prod("Black shoes","Shoes","Description",R.drawable.shoes));
        Book22.add(new Prod("Dress","Women","Description",R.drawable.female));
        Book22.add(new Prod("Designer Jacket","Men","Description",R.drawable.jacket));
        */


        //Data for view 3


        getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/gettrendingpro/",3);

        /*
        mTrends.add(new r3("Toddler suit","Kids","Description",R.drawable.baby));
        mTrends.add(new r3("Black shoes","Shoes","Description",R.drawable.shoes));
        mTrends.add(new r3("Dress","Women","Description",R.drawable.female));
        mTrends.add(new r3("Designer Jacket"," Men","Description",R.drawable.jacket));
        mTrends.add(new r3("Toddler suit","Kids","Description",R.drawable.baby));
        mTrends.add(new r3("Black shoes","Shoes","Description",R.drawable.shoes));
        mTrends.add(new r3("Dress","Women","Description",R.drawable.female));
        mTrends.add(new r3("Designer Jacket","Men","Description",R.drawable.jacket));
        mTrends.add(new r3("Toddler suit","Kids","Description",R.drawable.baby));
        mTrends.add(new r3("Black shoes","Shoes","Description",R.drawable.shoes));
        mTrends.add(new r3("Dress","Women","Description",R.drawable.female));
        mTrends.add(new r3("Designer Jacket","Men","Description",R.drawable.jacket));

*/
//Recycler View 1
         view= inflater.inflate(R.layout.homefragment,container,false);

         v2=inflater.inflate(R.layout.cardveiw_item_prod,null);
       setrecycleone();


//Recycler View 2

      //  View view2= inflater.inflate(R.layout.homefragment,  null);
        /*RecyclerView myrv2=(RecyclerView) view.findViewById(R.id.recyclerview2);
        RecyclerViewProdAdapter myAdapter1 = new RecyclerViewProdAdapter(getActivity(),Book22);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv2.setLayoutManager(layoutManager1);
        myrv2.setAdapter(myAdapter1);*/

//Recycler View 3

        //  View view2= inflater.inflate(R.layout.homefragment,  null);
        /*RecyclerView myrv3=(RecyclerView) view.findViewById(R.id.recyclerview3);
        RecyclerView3 myAdapter2 = new RecyclerView3(getActivity(),mTrends);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv3.setLayoutManager(layoutManager2);
        myrv3.setAdapter(myAdapter2);*/



        ImageView prod =(ImageView)v2.findViewById(R.id.book_img_id);


        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetail=new Intent(getActivity(), Prod_Activity.class);
                startActivity(productdetail);
            }
        });

        //flipper
        viewFlipper=(ViewFlipper)view.findViewById(R.id.flipper);
        int images[] = {R.drawable.ac_banner,R.drawable.cloth_banner,R.drawable.sale1,R.drawable.mobile_banner};

        for (int image:images) {
            flipperimages(image);
        }

return  view;





        //return inflater.inflate(R.layout.homefragment,  null);

    }
    public  void   getconnection(String url,final int val){



        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";



            StringRequest rRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(ShoppyProductListActivity.this, response, Toast.LENGTH_SHORT).show();
                            try {
                                //m=new ArrayList<String>();
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject product = array.getJSONObject(i);
                                    String img=product.getString("productImage");
                                    String title=product.getString("title");
                                    String description=product.getString("description");
                                    String category=product.getString("category");
                                    double price= product.getDouble("price");
                                    int id=product.getInt("productId");
                                    img="http://ahmedishtiaq9778-001-site1.ftempurl.com"+img;
                                    if(img!=null) {
                                        if(val==1) {
                                            lstBook2.add(new Book(title, category, description, img,price,id));
                                        }else if(val==2)
                                        {
                                            Book22.add(new Prod(title, category, description, img,price,id));
                                        }else if(val==3){
                                            mTrends.add(new r3(title, category, description, img,price,id));
                                        }
                                    }

                                }
                                if(val==1){
                                setrecycleone();
                                }else if(val==2){
                                    setrecycletwo();
                                }else if(val==3){
                                    setrecyclethree();
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();

                                Toast.makeText(getContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
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
                    });



            requestQueue.add(rRequest);


        }catch (Exception E)
        {
            Toast.makeText(getContext(),"Error: "+E.getMessage(),Toast.LENGTH_SHORT).show();
        }















    }
    public  void flipperimages(int image)
    {
        try {
            ImageView imageView=new ImageView(getContext());
            imageView.setImageResource(image);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);
        }catch(Exception e)
        {
            Toast.makeText(getContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }




    }

  public void setrecycleone()
     {


         RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
         RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(),lstBook2);
         LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
         //  myrv.setLayoutManager(new LinearLayoutManager(getActivity()));

         myrv.setLayoutManager(layoutManager);
         myrv.setAdapter(myAdapter);

     }
    public void setrecycletwo()
    {
        RecyclerView myrv2=(RecyclerView) view.findViewById(R.id.recyclerview2);
        RecyclerViewProdAdapter myAdapter1 = new RecyclerViewProdAdapter(getActivity(),Book22);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv2.setLayoutManager(layoutManager1);
        myrv2.setAdapter(myAdapter1);
    }
    public void setrecyclethree()
    {
        RecyclerView myrv3=(RecyclerView) view.findViewById(R.id.recyclerview3);
        RecyclerView3 myAdapter2 = new RecyclerView3(getActivity(),mTrends);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv3.setLayoutManager(layoutManager2);
        myrv3.setAdapter(myAdapter2);
    }
}
