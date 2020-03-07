package com.demotxt.myapp.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.adapter.RecyclerViewAdapter;
import com.demotxt.myapp.recyclerview.adapter.RecyclerViewProdAdapter;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> lstBook;
    List<Prod> Book2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Data for view 1
        lstBook = new ArrayList<>();
       /* lstBook.add(new Book("Toddler suit","Kids","Description",R.drawable.baby));
        lstBook.add(new Book("Black shoes","Shoes","Description",R.drawable.shoes));
        lstBook.add(new Book("Dress","Women","Description",R.drawable.female));
        lstBook.add(new Book("Designer Jacket"," Men","Description",R.drawable.jacket));
        lstBook.add(new Book("Toddler suit","Kids","Description",R.drawable.baby));
        lstBook.add(new Book("Black shoes","Shoes","Description",R.drawable.shoes));
        lstBook.add(new Book("Dress","Women","Description",R.drawable.female));
        lstBook.add(new Book("Designer Jacket","Men","Description",R.drawable.jacket));
        lstBook.add(new Book("Toddler suit","Kids","Description",R.drawable.baby));
        lstBook.add(new Book("Black shoes","Shoes","Description",R.drawable.shoes));
        lstBook.add(new Book("Dress","Women","Description",R.drawable.female));
        lstBook.add(new Book("Designer Jacket","Men","Description",R.drawable.jacket));

        //Data for view 2
        Book2 = new ArrayList<>();
        Book2.add(new Prod("Toddler suit","Kids","Description",R.drawable.baby));
        Book2.add(new Prod("Black shoes","Shoes","Description",R.drawable.shoes));
        Book2.add(new Prod("Dress","Women","Description",R.drawable.female));
        Book2.add(new Prod("Designer Jacket"," Men","Description",R.drawable.jacket));
        Book2.add(new Prod("Toddler suit","Kids","Description",R.drawable.baby));
        Book2.add(new Prod("Black shoes","Shoes","Description",R.drawable.shoes));
        Book2.add(new Prod("Dress","Women","Description",R.drawable.female));
        Book2.add(new Prod("Designer Jacket","Men","Description",R.drawable.jacket));
        Book2.add(new Prod("Toddler suit","Kids","Description",R.drawable.baby));
        Book2.add(new Prod("Black shoes","Shoes","Description",R.drawable.shoes));
        Book2.add(new Prod("Dress","Women","Description",R.drawable.female));
        Book2.add(new Prod("Designer Jacket","Men","Description",R.drawable.jacket));

*/
//Recycler View 1
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrv.setLayoutManager(layoutManager);
        myrv.setAdapter(myAdapter);
//Recycler View 2

        RecyclerView myrv2 = (RecyclerView) findViewById(R.id.recyclerview2);
        RecyclerViewProdAdapter myAdapter1 = new RecyclerViewProdAdapter(this,Book2);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrv2.setLayoutManager(layoutManager1);
        myrv2.setAdapter(myAdapter1);


    }
}
