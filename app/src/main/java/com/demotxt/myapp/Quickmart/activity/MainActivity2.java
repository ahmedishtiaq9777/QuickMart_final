package com.demotxt.myapp.Quickmart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.demotxt.myapp.Quickmart.Search.Search_Activity;
import com.demotxt.myapp.Quickmart.fragment.CartFragment;
import com.demotxt.myapp.Quickmart.fragment.FavoriteFragment;
import com.demotxt.myapp.Quickmart.fragment.HomeFragment;
import com.demotxt.myapp.Quickmart.fragment.ProfileFragment;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.DetailModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private int Check;
    private FragmentManager fragmentmanager;
    BottomNavigationView navView;
    public static String hostinglink;
    private View layout;
    int proid;
    FloatingActionButton search;
    public RelativeLayout lyt_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentmanager = getSupportFragmentManager();
        hostinglink = getResources().getString(R.string.hosting);
        CheckConnection();
        //
        loadUserDetail();
        //
        proid = 0;       //To Check Internet Connection
        if (Check == 1) {
            setContentView(R.layout.activitymain2);
        } else {
            finish();
            Intent intent = new Intent(MainActivity2.this, Error_Screen_Activity.class);
            startActivity(intent);
        }

        LayoutInflater inflater = getLayoutInflater();
        try {
            layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));//for product added :to make custom toast with tick mark

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Loginactivity", "error" + e.getMessage());

        }

        try {
            Intent i = getIntent();
            proid = Objects.requireNonNull(i.getExtras()).getInt("proid");
            if (proid != 0) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        navView = (BottomNavigationView) findViewById(R.id.nav_view);

        try {
            navView.setOnNavigationItemSelectedListener(this);

            loadFragment(new HomeFragment(), "homestack");
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        fragmentmanager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                StringBuffer messege;
                messege = new StringBuffer("current status of fragment back stack:" + fragmentmanager.getBackStackEntryCount() + "\n");
                for (int index = (fragmentmanager.getBackStackEntryCount() - 1); index >= 0; index--) {

                    FragmentManager.BackStackEntry entry = fragmentmanager.getBackStackEntryAt(index);
                    messege.append(entry.getName()).append("\n");


                }
                Log.i("CALLBACKS", messege.toString());
            }
        });


        //For Search
        search = findViewById(R.id.fab_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, Search_Activity.class);
                startActivity(i);
            }
        });

    }
    //load user detail
    public void loadUserDetail(){
        DetailModel model = new DetailModel(getApplicationContext());
        model.getYourName();
        model.getYourPhone();
        model.getYourAddress();
    }


    public boolean loadFragment(Fragment fragment, String stackname) {
        if (fragment != null && !stackname.equals("homestack")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, fragment).addToBackStack(stackname)
                    .commit();
            return true;
        } else if (stackname.equals("homestack")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, Objects.requireNonNull(fragment))
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentmanager.findFragmentById(R.id.fragmentcontainer);
        if (fragment != null) {

            if (fragment instanceof FavoriteFragment || fragment instanceof CartFragment || fragment instanceof ProfileFragment) {
                navView.getMenu().getItem(0).setChecked(true);
                Fragment Homefragment = new HomeFragment();
                loadFragment(Homefragment, "homestack");
            } else if (fragment instanceof HomeFragment) {
                MainActivity2.this.moveTaskToBack(true);
            }
            //super.onBackPressed();


        }


        // Fragment fragment= FragmentManager.
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int op = 0;
        String backstackname = null;
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                navView.setBackgroundColor(getResources().getColor(R.color.navhome));
                fragment = new HomeFragment();
                backstackname = "homestack";
                break;
            case R.id.nav_favourite:

                navView.setBackgroundColor(getResources().getColor(R.color.navfav));
                fragment = new FavoriteFragment();
                backstackname = "favstack";
                break;
            case R.id.nav_cart:
                navView.setBackgroundColor(getResources().getColor(R.color.navcart));
                fragment = new CartFragment();
                backstackname = "cartstack";
                break;

            case R.id.nav_acc:
                navView.setBackgroundColor(getResources().getColor(R.color.navprof));
                if (getloginprefference() == true) {
                    fragment = new ProfileFragment();
                    backstackname = "profilestack";
                    break;
                } else {

                    Intent login = new Intent(MainActivity2.this, Login.class);
                    startActivity(login);
                }
        }

        if (op != 1)
            return loadFragment(fragment, backstackname);
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
    public void CheckConnection() {
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
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
