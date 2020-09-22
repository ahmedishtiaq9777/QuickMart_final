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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.activity.Prod_Activity;
import com.demotxt.myapp.Quickmart.ownmodels.Prod;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerView3 extends RecyclerView.Adapter<RecyclerView3.MyViewHolder> implements Filterable {

    private final Context mContext;
    private List<Prod> Data2;
    private final List<Prod> Data2Full;

    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
    //  private List<Integer> Ids;
    @Nullable
    public Set<String> ids;


    public RecyclerView3(Context mContext, List<Prod> data) {
        this.mContext = mContext;
        this.Data2 = data;
        Data2Full = new ArrayList<>(Data2);
        ids = new HashSet<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        cartpreferrence = mContext.getSharedPreferences("favpref", MODE_PRIVATE);
        cartprefEditor = cartpreferrence.edit();
        ids = cartpreferrence.getStringSet("ids", ids);

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_r3, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        int ID = Data2.get(position).getId();
        String strID = String.valueOf(ID);
        isblack = cartpreferrence.getBoolean(strID, false);
        if (isblack == true) {
            holder.heart.setImageResource(R.drawable.ic_favorite_fill_24dp);
        }

        holder.tv_r3_title.setText(Data2.get(position).getTitle());
        holder.r3_price.setText(String.valueOf(Data2.get(position).getPrice()));
        // holder.img_book_thumbnail.setImageResource(Data2.get(position).getThumbnail());
        Picasso.get().load(Data2.get(position).getThumbnail()).into(holder.img_r3_thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Prod_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title", Data2.get(position).getTitle());
                intent.putExtra("Description", Data2.get(position).getDescription());
                intent.putExtra("Thumbnail", Data2.get(position).getThumbnail());
                intent.putExtra("price", Data2.get(position).getPrice());
                intent.putExtra("proid", Data2.get(position).getId());
                intent.putExtra("sellerid",Data2.get(position).getSid());

                //Transition Test
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                        holder.img_r3_thumbnail, Objects.requireNonNull(ViewCompat.getTransitionName(holder.img_r3_thumbnail)));

                // start the activity
                mContext.startActivity(intent,optionsCompat.toBundle());

            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    int ID = Data2.get(position).getId();// get selected product Id
                    String strID = String.valueOf(ID);// convert to String
                    isblack = cartpreferrence.getBoolean(strID, false);// Check whether selected product is black or not
                    if (isblack == true) {
                        holder.heart.setImageResource(R.drawable.ic_favorite_border_24dp);
                        Objects.requireNonNull(ids).remove(strID);
                        cartprefEditor.putBoolean(strID, false);


                    } else {
                        holder.heart.setImageResource(R.drawable.ic_favorite_fill_24dp);
                        cartprefEditor.putBoolean(strID, true);
                        Objects.requireNonNull(ids).add(strID);
                        //  Log.i("message", "length: " +ids.size());
                        //  Toast.makeText(, "error:", Toast.LENGTH_SHORT).show();

                    }
                    cartprefEditor.putStringSet("ids", ids);
                    cartprefEditor.commit();

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return Data2.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_r3_title;
        final TextView r3_price;
        final ImageView img_r3_thumbnail;
        final ImageView heart;
        final CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_r3_title = itemView.findViewById(R.id.r3_title_id);
            img_r3_thumbnail = itemView.findViewById(R.id.r3_img_id);
            r3_price = itemView.findViewById(R.id.r3_prod_price);
            cardView = itemView.findViewById(R.id.cardview_id_r3);
            heart = itemView.findViewById(R.id.heart);

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
                filteredList.addAll(Data2Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Prod item : Data2Full) {
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

            Data2 = new ArrayList<>();
            Data2.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


}
