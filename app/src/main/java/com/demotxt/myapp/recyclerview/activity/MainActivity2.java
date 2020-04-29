package com.demotxt.myapp.recyclerview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain2);

        try {
            Intent i = getIntent();
            int code = i.getExtras().getInt("code");
            if (code == 5) {
                Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);


        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int op = 0;

        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_favourite:
                fragment = new FavoriteFragment();
                break;
            case R.id.nav_cart:
                fragment = new CartFragment();
                break;

            case R.id.nav_acc:
                if (getloginprefference() == true) {
                    fragment = new ProfileFragment();
                    break;
                } else {

                    Intent login = new Intent(MainActivity2.this, Login.class);
                    startActivity(login);
                }
        }

        if (op != 1)
            return loadFragment(fragment);
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
}

