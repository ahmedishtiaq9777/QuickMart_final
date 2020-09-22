package com.demotxt.myapp.Quickmart.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.Quickmart.activity.Prod_Activity;
import com.demotxt.myapp.Quickmart.ownmodels.Prod;
import com.demotxt.myapp.Quickmart.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewProdAdapter extends RecyclerView.Adapter<RecyclerViewProdAdapter.MyViewHolder> implements Filterable {

    private final Context mContext;
    private List<Prod> Data1;
    @NonNull
    private final List<Prod> Data1Full;
    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
  //  private List<Integer> Ids;
    public Set<String> ids;


    public RecyclerViewProdAdapter(Context mContext, List<Prod> data1) {
        this.mContext = mContext;
        this.Data1 = data1;
        Data1Full = new ArrayList<>(Data1);
        ids= new HashSet<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        int ID=Data1.get(position).getId();
        String strID=String.valueOf(ID);
        isblack=cartpreferrence.getBoolean(strID,false);
        if(isblack==true)
        {
            holder.heart.setImageResource(R.drawable.ic_favorite_fill_24dp);
        }

        holder.tv_book_title.setText(Data1.get(position).getTitle());
        holder.price.setText(String.valueOf(Data1.get(position).getPrice()));
       // holder.img_book_thumbnail.setImageResource(Data1.get(position).getThumbnail());
        Picasso.get().load(Data1.get(position).getThumbnail()).into(holder.img_book_thumbnail);
        //For Rating Purposes
        holder.mRatingBar.setRating(Data1.get(position).getRating());
        //
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
                intent.putExtra("sellerid",Data1.get(position).getSid());
                //Transition Test
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                        holder.img_book_thumbnail, Objects.requireNonNull(ViewCompat.getTransitionName(holder.img_book_thumbnail)));

                // start the activity
                mContext.startActivity(intent,optionsCompat.toBundle());
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
                        holder.heart.setImageResource(R.drawable.ic_favorite_fill_24dp);
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

        final TextView tv_book_title;
        final TextView price;
        final ImageView img_book_thumbnail;
        final ImageView heart;
        final CardView cardView ;
        final AppCompatRatingBar mRatingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id);
            price = itemView.findViewById(R.id.book_prod_price);
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            cardView = itemView.findViewById(R.id.cardview2);
            heart=itemView.findViewById(R.id.heart);
            mRatingBar = itemView.findViewById(R.id.card_RatingBar);
        }
    }

    //For Search Purposes
    @Override
    public Filter getFilter() {
        return mDataFilter;
    }

    private final Filter mDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Prod> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(Data1Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Prod item : Data1Full) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            Data1 = new ArrayList<>();
            Data1.addAll( (List) results.values);
            notifyDataSetChanged();

        }
    };



}
