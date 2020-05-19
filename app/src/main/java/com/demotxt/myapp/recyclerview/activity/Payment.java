package com.demotxt.myapp.recyclerview.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.fragment.FragmentConfirmation;
import com.demotxt.myapp.recyclerview.fragment.FragmentPayment;
import com.demotxt.myapp.recyclerview.fragment.FragmentShipping;
import com.demotxt.myapp.recyclerview.utils.Tools;
import java.util.ArrayList;
import java.util.List;


public class Payment extends AppCompatActivity {

    private ViewPager view_pager;
/*
    private enum State {
        SHIPPING,
        PAYMENT,
        CONFIRMATION
    }
    State[] array_state = new State[]{State.SHIPPING, State.PAYMENT, State.CONFIRMATION};*/
    private View line_first, line_second;
    private ImageView image_shipping, image_payment, image_confirm;
    private TextView tv_shipping, tv_payment, tv_confirm;

   // private int idx_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        initToolbar();
        initComponent();
        // displayFragment(State.SHIPPING);
    }

    private void initComponent() {
        line_first = (View) findViewById(R.id.line_first);
        line_second = (View) findViewById(R.id.line_second);
        image_shipping = (ImageView) findViewById(R.id.image_shipping);
        image_payment = (ImageView) findViewById(R.id.image_payment);
        image_confirm = (ImageView) findViewById(R.id.image_confirm);

        tv_shipping = (TextView) findViewById(R.id.tv_shipping);
        tv_payment = (TextView) findViewById(R.id.tv_payment);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);

        image_payment.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        image_confirm.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);

        view_pager = (ViewPager) findViewById(R.id.Payment_ViewPager);
        setupViewPager(view_pager);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }
/*
    private void displayFragment(State state) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();

        if (state.name().equalsIgnoreCase(State.SHIPPING.name())) {
            fragment = new FragmentShipping();
            tv_shipping.setTextColor(getResources().getColor(R.color.grey_90));
            image_shipping.clearColorFilter();


        } else if (state.name().equalsIgnoreCase(State.PAYMENT.name())) {
            fragment = new FragmentPayment();
            line_first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_shipping.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_payment.clearColorFilter();
            tv_payment.setTextColor(getResources().getColor(R.color.grey_90));


        } else if (state.name().equalsIgnoreCase(State.CONFIRMATION.name())) {
            fragment = new FragmentConfirmation();
            line_second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_payment.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_confirm.clearColorFilter();
            tv_confirm.setTextColor(getResources().getColor(R.color.grey_90));
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

 */
    private void refreshStepTitle() {
        tv_shipping.setTextColor(getResources().getColor(R.color.grey_20));
        tv_payment.setTextColor(getResources().getColor(R.color.grey_20));
        tv_confirm.setTextColor(getResources().getColor(R.color.grey_20));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
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


    private void setupViewPager(ViewPager viewPager) {
        Payment.SectionsPagerAdapter adapter = new Payment.SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PlaceholderFragment.newInstance(0), "Shipping");
        adapter.addFragment(PlaceholderFragment.newInstance(1), "Payment");
        adapter.addFragment(PlaceholderFragment.newInstance(2), "Confirmation");

        viewPager.setAdapter(adapter);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static Payment.PlaceholderFragment newInstance(int sectionNumber) {
            Payment.PlaceholderFragment fragment = new Payment.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        View rootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            inflater.inflate(R.layout.payment, container, false);
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

            refreshStepTitle();

            switch (position) {
                case 0:
                    frag = new FragmentShipping();

                    tv_shipping.setTextColor(getResources().getColor(R.color.grey_90));
                    image_shipping.clearColorFilter();

                    break;
                case 1:
                    frag = new FragmentPayment();

                    line_first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    image_shipping.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    image_payment.clearColorFilter();
                    tv_payment.setTextColor(getResources().getColor(R.color.grey_90));

                    break;
                case 2:
                    frag = new FragmentConfirmation();

                    line_second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    image_payment.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    image_confirm.clearColorFilter();
                    tv_confirm.setTextColor(getResources().getColor(R.color.grey_90));

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

