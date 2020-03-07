package com.demotxt.myapp.recyclerview.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.sharepref.SharedPref;
import com.google.android.material.snackbar.Snackbar;

public class UsersettingFragment extends PreferenceActivity {
    private SharedPref sharedPref;
    private View parent_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.xml.usersettings);

        addPreferencesFromResource(R.xml.usersettings);
        parent_view = findViewById(android.R.id.content);
        sharedPref = new SharedPref(this);


        final EditTextPreference prefName = (EditTextPreference) findPreference(getString(R.string.pref_title_name));
        final EditTextPreference prefEmail = (EditTextPreference) findPreference(getString(R.string.pref_title_email));
        final EditTextPreference prefPhone = (EditTextPreference) findPreference(getString(R.string.pref_title_phone));
        final EditTextPreference prefAddress = (EditTextPreference) findPreference(getString(R.string.pref_title_address));

        prefName.setSummary(sharedPref.getYourName());
        prefName.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if (!s.trim().isEmpty()) {
                    prefName.setSummary(s);
                    return true;
                } else {

                    Snackbar snackbar = Snackbar.make(parent_view, R.string.pref_msg_invalid_name, Snackbar.LENGTH_LONG);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                    return  false;
                }


            }
        });




        prefEmail.setSummary(sharedPref.getYourEmail());
        prefEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if (!s.trim().isEmpty()) {
                    prefEmail.setSummary(s);
                    return true;
                } else {
                    Snackbar snackbar = Snackbar.make(parent_view, R.string.pref_msg_invalid_email, Snackbar.LENGTH_LONG);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    return false;
                }
            }
        });


        prefPhone.setSummary(sharedPref.getYourPhone());
        prefPhone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if(!s.trim().isEmpty()){
                    prefPhone.setSummary(s);
                    return true;
                }else{
                    Snackbar snackbar = Snackbar.make(parent_view, R.string.pref_msg_invalid_phone, Snackbar.LENGTH_LONG);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    return false;
                }
            }
        });



        prefAddress.setSummary(sharedPref.getYourAddress());
        prefAddress.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if(!s.trim().isEmpty()){
                    prefAddress.setSummary(s);
                    return true;
                }else{
                    Snackbar snackbar = Snackbar.make(parent_view, R.string.pref_msg_invalid_address, Snackbar.LENGTH_LONG);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    return false;
                }
            }
        });














    }
}
