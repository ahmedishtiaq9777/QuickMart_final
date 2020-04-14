package com.demotxt.myapp.recyclerview.CategoryFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.TabsBasic;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class CatWomen_Adapter extends RecyclerView.Adapter<CatWomen_Adapter.CatWomenViewHolder> {

    private Context mContext;
    private List<CatWomen> mData;
    //
    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
    //  private List<Integer> Ids;
    public Set<String> ids;

    public CatWomen_Adapter(List<CatWomen> mdata){
        mData = mdata;
    }

    @NonNull
    @Override
    public CatWomenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartpreferrence = mContext.getSharedPreferences("favpref", MODE_PRIVATE);
        cartprefEditor = cartpreferrence.edit();
        ids = cartpreferrence.getStringSet("ids", ids);
        //
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_cat_women, parent, false);
        return new CatWomen_Adapter.CatWomenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatWomenViewHolder holder, int position) {
        int ID = mData.get(position).getId();
        String strID = String.valueOf(ID);
        isblack = cartpreferrence.getBoolean(strID, false);
        if (isblack == true) {
            holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp);
        }


        holder.tv_women_title.setText(mData.get(position).getTitle());
        holder.tv_price.setText(mData.get(position).getPrice());
        //holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_women_thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Starting Activity To show Category Activity
                Intent intent = new Intent(mContext, TabsBasic.class);
                mContext.startActivity(intent);

            }
        });
      /*  holder.heart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    int ID = mData.get(position).getId();// get selected product Id
                    String strID = String.valueOf(ID);// convert to String
                    isblack = cartpreferrence.getBoolean(strID, false);// Check whether selected product is black or not
                    if (isblack == true) {
                        holder.heart.setImageResource(R.drawable.ic_favorite_border_24dp);
                        ids.remove(strID);

                        cartprefEditor.putBoolean(strID, false);


                    } else {
                        holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp);
                        cartprefEditor.putBoolean(strID, true);
                        ids.add(strID);
                        //  Log.i("message", "length: " +ids.size());
                        //  Toast.makeText(, "error:", Toast.LENGTH_SHORT).show();

                    }
                    cartprefEditor.remove("favpref");
                    cartprefEditor.commit();
                    cartprefEditor.putStringSet("ids", ids);
                    cartprefEditor.commit();

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


              *//*  if (iscjd=false)
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
                }*//*


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CatWomenViewHolder extends RecyclerView.ViewHolder   {

        TextView tv_women_title,tv_price;
        ImageView img_women_thumbnail, heart;
        CardView cardView;


        public CatWomenViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_women_title = itemView.findViewById(R.id.women_title_id);
            tv_price = itemView.findViewById(R.id.women_prod_price);
            img_women_thumbnail = itemView.findViewById(R.id.women_img_id);
            cardView = itemView.findViewById(R.id.cardview_women);
            heart = itemView.findViewById(R.id.heart);
        }
    }
}
