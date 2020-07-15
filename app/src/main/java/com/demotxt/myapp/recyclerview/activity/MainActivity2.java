package com.demotxt.myapp.recyclerview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.fragment.CartFragment;
import com.demotxt.myapp.recyclerview.fragment.FavoriteFragment;
import com.demotxt.myapp.recyclerview.fragment.HomeFragment;
import com.demotxt.myapp.recyclerview.fragment.ProfileFragment;
import com.demotxt.myapp.recyclerview.utils.Tools;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private int Check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CheckConnection();

        //To Check Internet Connection
        if (Check == 1){
        setContentView(R.layout.activitymain2);
        }
        else {
            finish();
           // Intent intent = new Intent(getApplicationContext(),Error_Screen_Activity.class);
            Intent intent = new Intent(MainActivity2.this,Error_Screen_Activity.class);
            startActivity(intent);
        }

        try {
            initToolbar();
        }catch (Exception e)
        {

        }




           // Toast.makeText(getApplicationContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();




        try {
            Intent i = getIntent();
            int code = i.getExtras().getInt("code");
            if (code == 5) {
                Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("ExceptionMainActivity2",e.getMessage());
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);

try {
    navView.setOnNavigationItemSelectedListener(this);

     loadFragment(new HomeFragment(),"homestack");
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
}catch (Exception e)
{
    Toast.makeText(getApplicationContext(),"Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
}
    }



    public boolean loadFragment(Fragment fragment,String stackname) {
        if (fragment != null && !stackname.equals("homestack")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, fragment).addToBackStack(null)
                    .commit();
            return true;
        }else if(stackname.equals("homestack"))
        {  getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentcontainer, fragment)
                .commit();
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int op = 0;
String backstackname=null;
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                backstackname="homestack";
                break;
            case R.id.nav_favourite:
                fragment = new FavoriteFragment();
                backstackname="favstack";
                break;
            case R.id.nav_cart:
                fragment = new CartFragment();
                backstackname="cartstack";
                break;

            case R.id.nav_acc:
                if (getloginprefference() == true) {
                    fragment = new ProfileFragment();
                    backstackname="profilestack";
                    break;
                } else {

                    Intent login = new Intent(MainActivity2.this, Login.class);
                    startActivity(login);
                }
        }

        if (op != 1)
            return loadFragment(fragment,backstackname);
        return false;
    }

    private boolean getloginprefference() {

        loginPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);

        loginPrefsEditor = loginPreferences.edit();
        String username = loginPreferences.getString("username", "");
        String pass = loginPreferences.getString("password", "");
        if (username != null || pass != null) {
            return true;
        } else {
            return false;
        }

    }

    //Internet Connection Check
    public void CheckConnection(){
try {
    ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

    if (null != activeNetwork) {
        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            Toast.makeText(this, "Wifi On", Toast.LENGTH_SHORT).show();
            Check = 1;
        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            Toast.makeText(this, "Mobile Data On", Toast.LENGTH_SHORT).show();
            Check = 1;
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            Check = 0;
        }

    }
}catch (Exception e)
{
    Toast.makeText(getApplicationContext(), "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
}
    }

    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

}

