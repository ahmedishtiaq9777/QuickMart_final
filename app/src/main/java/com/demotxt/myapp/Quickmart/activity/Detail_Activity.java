package com.demotxt.myapp.Quickmart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.DetailModel;
import com.google.android.material.snackbar.Snackbar;


public class Detail_Activity extends PreferenceActivity {
    private DetailModel model;
    private View parent_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.xml.usersettings);

        addPreferencesFromResource(R.xml.usersettings);
        parent_view = findViewById(android.R.id.content);
        model = new DetailModel(this);


        final EditTextPreference prefName = (EditTextPreference) findPreference(getString(R.string.pref_title_name));
        final EditTextPreference prefEmail = (EditTextPreference) findPreference(getString(R.string.pref_title_email));
        final EditTextPreference prefPhone = (EditTextPreference) findPreference(getString(R.string.pref_title_phone));
        final EditTextPreference prefAddress = (EditTextPreference) findPreference(getString(R.string.pref_title_address));

        prefName.setSummary(model.getYourName());
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

                    return false;
                }


            }
        });

//for email
 /*       prefEmail.setSummary(model.getYourEmail());
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
        });*/


        prefPhone.setSummary(model.getYourPhone());
        prefPhone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if (!s.trim().isEmpty()) {
                    prefPhone.setSummary(s);
                    return true;
                } else {
                    Snackbar snackbar = Snackbar.make(parent_view, R.string.pref_msg_invalid_phone, Snackbar.LENGTH_LONG);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    return false;
                }
            }
        });


        prefAddress.setSummary(model.getYourAddress());
        prefAddress.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if (!s.trim().isEmpty()) {
                    prefAddress.setSummary(s);
                    return true;
                } else {
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