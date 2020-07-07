package com.demotxt.myapp.recyclerview.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.balysv.materialripple.MaterialRippleLayout;
import com.demotxt.myapp.recyclerview.Config;
import com.demotxt.myapp.recyclerview.Order.Order_Activity;
import com.demotxt.myapp.recyclerview.R;

import com.demotxt.myapp.recyclerview.sharepref.SharedPref;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    private SharedPref sharedPref;
    TextView txt_user_name;
    TextView txt_user_email;
    TextView txt_user_phone;
    TextView txt_user_address;
    TextView lang;
    MaterialRippleLayout btn_edit_user;
    MaterialRippleLayout btn_order_history, btn_share, btn_privacy, language, fav, exit;
    LinearLayout lyt_root;
    RelativeLayout relativeLayoutfornotlogin,relativeLayoutforloggenin;
    private SharedPreferences  loginpref;

    private static final String[] Languages = new String[]{
            "English", "Urdu"
    };
    public  boolean loadsubFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Information,fragment).addToBackStack(null)
                    .commit();
            return  true;
        }
        return  false;
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loadLocale();
       // Fragment fragment=null;
           //  fragment=   new ProfileSubFragment();
//loadsubFragment(fragment);
        sharedPref = new SharedPref(getActivity());

        final View view = inflater.inflate(R.layout.profilefragment, container, false);

        sharedPref = new SharedPref(getActivity());

        lyt_root = view.findViewById(R.id.lyt_root);
        if (Config.ENABLE_RTL_MODE) {
            lyt_root.setRotationY(180);
        }
        loginpref = getContext().getSharedPreferences("loginpref", MODE_PRIVATE);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_email = view.findViewById(R.id.txt_user_email);
        txt_user_phone = view.findViewById(R.id.txt_user_phone);
        txt_user_address = view.findViewById(R.id.txt_user_address);
        btn_order_history = view.findViewById(R.id.btn_order_history);

        lang = view.findViewById(R.id.languagetext);
         relativeLayoutfornotlogin=(RelativeLayout) view.findViewById(R.id.fornotlogin);
         relativeLayoutforloggenin=(RelativeLayout)view.findViewById(R.id.Information);
boolean islogin=loginpref.getBoolean("loggedin",false);
if(islogin)
{
    relativeLayoutfornotlogin.setVisibility(View.GONE);
    relativeLayoutforloggenin.setVisibility(View.VISIBLE);
}else {
    relativeLayoutforloggenin.setVisibility(View.GONE);
    relativeLayoutfornotlogin.setVisibility(View.VISIBLE);
    btn_order_history.setVisibility(View.GONE);
}




        txt_user_name.setText(sharedPref.getYourName());
        txt_user_email.setText(sharedPref.getYourEmail());
        txt_user_address.setText(sharedPref.getYourAddress());
        txt_user_phone.setText(sharedPref.getYourPhone());

        btn_edit_user = view.findViewById(R.id.btn_edit_user);
        btn_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), UsersettingFragment.class);
                startActivity(intent);

                ///FragmentTransaction ft=getChildFragmentManager().beginTransaction();


                // UsersettingFragment user=new UsersettingFragment();
                // ft.replace(R.id.lyt_root,user);
            }
        });

        //For Order history

        btn_order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Order_Activity.class);
                startActivity(intent);
            }
        });

        //For Sharing
        btn_share = view.findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String share_text = Html.fromHtml(getResources().getString(R.string.share_app)).toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, share_text + "\n\n" + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                intent.setType("text/plain");
                startActivity(intent);

            }
        });

        //For Privacy Policy
        btn_privacy = view.findViewById(R.id.btn_privacy);
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String share_text = Html.fromHtml(getResources().getString(R.string.Privacy_Policy)).toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, share_text + "\n\n" + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        //For Language settings
        language = view.findViewById(R.id.lang);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showChangeLanguageDialog();
            }
        });

        //For Opening the Favourite Fragment
        fav = view.findViewById(R.id.favourite);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new FavoriteFragment();
                loadFragment(fragment);

            }
        });

        //For Exiting the App
        exit = view.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(0);

            }
        });
        return view;
    }

    private void showChangeLanguageDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Choose Language....");
        mBuilder.setSingleChoiceItems(Languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    setLocale("en");
                    getActivity().recreate();
                } else if (i == 1) {
                    setLocale("ur");
                    getActivity().recreate();
                }
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
        //saving data in shared preference

        SharedPreferences.Editor editor = getContext().getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences pref = getContext().getSharedPreferences("Settings", MODE_PRIVATE);
        String lan = pref.getString("My_Lang", "");
        setLocale(lan);
    }







}





