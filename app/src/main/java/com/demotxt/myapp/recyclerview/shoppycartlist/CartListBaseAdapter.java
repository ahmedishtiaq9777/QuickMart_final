package com.demotxt.myapp.recyclerview.shoppycartlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demotxt.myapp.recyclerview.R;

import java.util.ArrayList;


public class CartListBaseAdapter extends BaseAdapter {

    Context context;

    ArrayList<CartListBeanlist> bean;




    private int number = 01;

    Typeface fonts1,fonts2;





    public CartListBaseAdapter(Context context, ArrayList<CartListBeanlist> bean) {


        this.context = context;
        this.bean = bean;
    }


    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_list,null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
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







        CartListBeanlist bean = (CartListBeanlist)getItem(position);

        viewHolder.image.setImageResource(bean.getImage());
        viewHolder.title.setText(bean.getTitle());

        viewHolder.price.setText(bean.getPrice());


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









    private class ViewHolder{
        ImageView image;
        TextView title;

        TextView price;

        TextView text;














    }
}




