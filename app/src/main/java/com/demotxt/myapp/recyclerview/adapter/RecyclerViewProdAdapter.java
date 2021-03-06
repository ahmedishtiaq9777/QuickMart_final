package com.demotxt.myapp.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.demotxt.myapp.recyclerview.R;
import com.squareup.picasso.Picasso;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewProdAdapter extends RecyclerView.Adapter<RecyclerViewProdAdapter.MyViewHolder> {

    private Context mContext;
    private List<Prod> Data1;

    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
  //  private List<Integer> Ids;
    public Set<String> ids;


    public RecyclerViewProdAdapter(Context mContext, List<Prod> data1) {
        this.mContext = mContext;
        this.Data1 = data1;
        ids=new HashSet<String>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cartpreferrence =   mContext.getSharedPreferences("favpref", MODE_PRIVATE);
        cartprefEditor = cartpreferrence.edit();
        ids=cartpreferrence.getStringSet("ids",ids);
       // cartprefEditor.putBoolean("ischecked", false);


        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_prod,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        int ID=Data1.get(position).getId();
        String strID=String.valueOf(ID);
        isblack=cartpreferrence.getBoolean(strID,false);
        if(isblack==true)
        {
            holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        holder.tv_book_title.setText(Data1.get(position).getTitle());
       // holder.img_book_thumbnail.setImageResource(Data1.get(position).getThumbnail());
        Picasso.get().load(Data1.get(position).getThumbnail()).into(holder.img_book_thumbnail);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Prod_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",Data1.get(position).getTitle());
                intent.putExtra("Description",Data1.get(position).getDescription());
                intent.putExtra("Thumbnail",Data1.get(position).getThumbnail());
                intent.putExtra("price",Data1.get(position).getPrice());
                intent.putExtra("proid",Data1.get(position).getId());
                // start the activity
                mContext.startActivity(intent);

            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    int ID=Data1.get(position).getId();// get selected product Id
                    String strID=String.valueOf(ID);// convert to String
                  isblack=cartpreferrence.getBoolean(strID,false);// Check whether selected product is black or not
                    if(isblack==true)
                    {
                        holder.heart.setImageResource(R.drawable.ic_favorite_border_24dp);
                        ids.remove(strID);
                        cartprefEditor.putBoolean(strID,false);


                    }else {
                        holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp);
                        cartprefEditor.putBoolean(strID,true);
                        ids.add(strID);

                    }
                    cartprefEditor.putStringSet("ids",ids);
                    cartprefEditor.commit();

                }catch (Exception e){
                    Toast.makeText(mContext,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }


              /*  if (iscjd=false)
                {
                    heart black
                }
               // int res = getResources().getIdentifier(, "drawable", this.getPackageName());

              //  cartprefEditor.putBoolean("ischecked", false);
               /* ischecked = cartpreferrence.getBoolean("heart", false);
                if(ischecked==true)
                {
                    int ID=Data1.get(position).getId();

                    ids.add(String.valueOf(ID));
                    cartprefEditor


                }else {
                    cartprefEditor=
                }*/


            }
        } );




    }

    @Override
    public int getItemCount() {
        return Data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail,heart;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            cardView = itemView.findViewById(R.id.cardview2);
            heart=itemView.findViewById(R.id.heart);


        }
    }

}
