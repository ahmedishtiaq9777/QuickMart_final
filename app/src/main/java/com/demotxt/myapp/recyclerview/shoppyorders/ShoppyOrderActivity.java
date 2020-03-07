package com.demotxt.myapp.recyclerview.shoppyorders;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.recyclerview.R;

import java.util.ArrayList;

public class ShoppyOrderActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayList<BeanclassOrder> Bean;
   private ListAdapter baseAdapter;
    //private ExpandableListAdapter baseAdapter;
    private ProgressBar progressBar1;

    private int[] IMAGE = {R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo,R.drawable.shoppy_logo};
    private String[] ORDERDAY = {"2 day ago", "5 day ago", "7 day ago","8 day ago"};
    private String[] ORDERNAME = {"Vanvera Black High Heels", "Vanvera Black High Heels", "Vanvera Black High Heels","Vanvera Black High Heels"};
    private String[] QT = {"1 Pair", "1 Pair", "1 Pair","1 Pair"};
    private String[] DATE = {"June 20, 2016", "June 23, 2016", "June 28, 2016","June 30, 2016"};
    private String[] IDNUMBER = {"987876543", "3244876543", "233216543","9654876543"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_order);


//     ********Listview**************

            listview = (ListView) findViewById(R.id.listview);

            Bean = new ArrayList<BeanclassOrder>();

            for (int i = 0; i < IMAGE.length; i++) {

                BeanclassOrder bean = new BeanclassOrder(IMAGE[i], ORDERDAY[i], ORDERNAME[i], QT[i], DATE[i], IDNUMBER[i]);
                Bean.add(bean);

            }

            baseAdapter = new ListBaseAdapterOrder(ShoppyOrderActivity.this, Bean) {
            };

            listview.setAdapter(baseAdapter);
        }catch (Exception E)
        {
            Toast.makeText(getApplicationContext(),"error: "+E.getMessage(),Toast.LENGTH_SHORT).show();
            Log.e("error", E.getMessage());
        }
    }
}
