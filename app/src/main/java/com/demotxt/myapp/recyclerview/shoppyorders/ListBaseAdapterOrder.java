package com.demotxt.myapp.recyclerview.shoppyorders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demotxt.myapp.recyclerview.R;

import java.util.ArrayList;

/**
 * Created by apple on 01/04/16.
 */
public class ListBaseAdapterOrder extends BaseAdapter {


    Context context;

    ArrayList<BeanclassOrder> bean;
    Typeface fonts1;


    private ProgressBar progressBar1;


    public ListBaseAdapterOrder(Context context, ArrayList<BeanclassOrder> bean) {


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



        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_order,null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.orderday = (TextView)convertView.findViewById(R.id.orderday);
            viewHolder.ordername = (TextView)convertView.findViewById(R.id.ordername);
            viewHolder.qt = (TextView)convertView.findViewById(R.id.qt);
            viewHolder.date = (TextView)convertView.findViewById(R.id.date);
            viewHolder.idnumber = (TextView)convertView.findViewById(R.id.idnumber);





            viewHolder.orderday.setTypeface(fonts1);
            viewHolder.ordername.setTypeface(fonts1);
            viewHolder.qt.setTypeface(fonts1);
            viewHolder.date.setTypeface(fonts1);
            viewHolder.idnumber.setTypeface(fonts1);

            progressBar1 = (ProgressBar)convertView.findViewById(R.id.progressBar1);

        progressBar1.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);



            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }







        BeanclassOrder bean = (BeanclassOrder)getItem(position);


        viewHolder.image.setImageResource(bean.getImage());
        viewHolder.orderday.setText(bean.getDate());
        viewHolder.ordername.setText(bean.getOrdername());
        viewHolder.qt.setText(bean.getQt());
        viewHolder.date.setText(bean.getDate());
        viewHolder.idnumber.setText(bean.getIdnumber());









        return convertView;
    }

    private class ViewHolder{

        ImageView image;
        TextView orderday;
        TextView ordername;
        TextView qt;
        TextView date;
        TextView idnumber;


    }
}

