package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demotxt.myapp.Quickmart.CategoryFragments.CatKids_Fragment;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatMen_Fragment;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatWomen_Fragment;
import com.demotxt.myapp.Quickmart.ownmodels.CustomInternetDialog;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.utils.Tools;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

//import com.demotxt.myapp.recyclerview.ownmodels.CheckConnection;

public class TabsBasic extends AppCompatActivity {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    private int userid;
    CustomInternetDialog dialog;
    // CheckConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_basic);
        //initialize both
      /*  dialog=new CustomInternetDialog(TabsBasic.this);
        connection=new CheckConnection(TabsBasic.this);
        //check connection
        boolean is_connected=connection.CheckConnection();
        if(!is_connected)
        {
            dialog.showCustomDialog();
        }*/

        Intent i = getIntent();
        userid = i.getExtras().getInt("sellerid");
        // userid=i.getIntExtra("sellerid",0); this
        // userid=7;
        //  Log.i("UserId", "Seller id: "+userid);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(view_pager);

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PlaceholderFragment.newInstance(0), "Men");
        adapter.addFragment(PlaceholderFragment.newInstance(1), "Women");
        adapter.addFragment(PlaceholderFragment.newInstance(2), "Kids");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public int getuserid() {
        return userid;
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);


            return fragment;
        }

        //For changing Fragments
        View rootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            inflater.inflate(R.layout.activity_tabs_basic, container, false);
            return rootView;
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;


            switch (position) {
                case 0:

                    frag = new CatMen_Fragment();

                    break;
                case 1:

                    frag = new CatWomen_Fragment();

                    break;
                case 2:

                    frag = new CatKids_Fragment();

                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}