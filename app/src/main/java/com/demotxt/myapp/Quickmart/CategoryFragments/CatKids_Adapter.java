package com.demotxt.myapp.Quickmart.CategoryFragments;

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

import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.activity.Prod_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class CatKids_Adapter extends RecyclerView.Adapter<CatKids_Adapter.CatKidsViewHolder> implements Filterable {

    private final Context mContext;
    private List<Catkids> mData;
    private final List<Catkids> mDataFull;
    //
    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
    //  private List<Integer> Ids;
    public Set<String> ids;

    public CatKids_Adapter(Context mContext,List<Catkids> mdata){
        this.mContext=mContext;
        this.mData = mdata;
        mDataFull = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public CatKidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartpreferrence = mContext.getSharedPreferences("favpref", MODE_PRIVATE);
        cartprefEditor = cartpreferrence.edit();
        ids = cartpreferrence.getStringSet("ids", ids);
        //
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_cat_kids, parent, false);
        return new CatKids_Adapter.CatKidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatKidsViewHolder holder, final int position) {
        int ID = mData.get(position).getId();
        String strID = String.valueOf(ID);
        isblack = cartpreferrence.getBoolean(strID, false);
        if (isblack == true) {
            holder.heart.setImageResource(R.drawable.ic_favorite_fill_24dp);
        }

        holder.tv_kids_title.setText(mData.get(position).getTitle());
        holder.tv_price.setText(String.valueOf(mData.get(position).getPrice()));
        //holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_kids_thumbnail);
        //For Rating Purposes
        holder.mRatingBar.setRating(mData.get(position).getRating());
        //
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Prod_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                intent.putExtra("price",mData.get(position).getPrice());
                intent.putExtra("proid",mData.get(position).getId());
                intent.putExtra("sellerid",mData.get(position).getUserId());
                //Transition Test
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                        holder.img_kids_thumbnail, Objects.requireNonNull(ViewCompat.getTransitionName(holder.img_kids_thumbnail)));

                // start the activity
                mContext.startActivity(intent,optionsCompat.toBundle());




            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {

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
                        holder.heart.setImageResource(R.drawable.ic_favorite_fill_24dp);
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
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CatKidsViewHolder extends RecyclerView.ViewHolder   {

        final TextView tv_kids_title;
        final TextView tv_price;
        final ImageView img_kids_thumbnail;
        final ImageView heart;
        final CardView cardView;
        final AppCompatRatingBar mRatingBar;


        public CatKidsViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_kids_title = itemView.findViewById(R.id.kids_title_id);
            tv_price = itemView.findViewById(R.id.kids_prod_price);
            img_kids_thumbnail = itemView.findViewById(R.id.kids_img_id);
            cardView = itemView.findViewById(R.id.cardview_kids);
            heart = itemView.findViewById(R.id.heart);
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
            List<Catkids> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Catkids item : mDataFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if (item.getCategory().toLowerCase().contains(filterPattern)){
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

            mData = new ArrayList<>();
            mData.addAll( (List) results.values);
            notifyDataSetChanged();

        }
    };



}
