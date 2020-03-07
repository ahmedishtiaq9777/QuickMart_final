package com.demotxt.myapp.recyclerview.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import  com.balysv.materialripple.MaterialRippleLayout;
import com.demotxt.myapp.recyclerview.Config;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.sharepref.SharedPref;
import com.demotxt.myapp.recyclerview.shoppyorders.ShoppyOrderActivity;


public class ProfileFragment extends Fragment{

    private SharedPref sharedPref;
    TextView txt_user_name;
    TextView txt_user_email;
    TextView txt_user_phone;
    TextView txt_user_address;
    TextView lang;
    MaterialRippleLayout btn_edit_user;
    MaterialRippleLayout btn_order_history, btn_rate, btn_share, btn_privacy,language;
    LinearLayout lyt_root;


    private static final String[] Languages = new String[]{
            "English", "Urdu"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

sharedPref =new SharedPref(getActivity());

        final View view = inflater.inflate(R.layout.profilefragment, container, false);
        final View v2=inflater.inflate(R.layout.language,container,false);


        sharedPref = new SharedPref(getActivity());

        lyt_root = view.findViewById(R.id.lyt_root);
        if (Config.ENABLE_RTL_MODE) {
            lyt_root.setRotationY(180);
        }

        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_email = view.findViewById(R.id.txt_user_email);
        txt_user_phone = view.findViewById(R.id.txt_user_phone);
        txt_user_address = view.findViewById(R.id.txt_user_address);
        lang=     view.findViewById(R.id.languagetext);

        txt_user_name.setText(sharedPref.getYourName());
        txt_user_email.setText(sharedPref.getYourEmail());
        txt_user_address.setText(sharedPref.getYourAddress());
        txt_user_phone.setText(sharedPref.getYourPhone());

        btn_edit_user = view.findViewById(R.id.btn_edit_user);
        btn_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getActivity(), UsersettingFragment.class);
                startActivity(intent);

                ///FragmentTransaction ft=getChildFragmentManager().beginTransaction();


                // UsersettingFragment user=new UsersettingFragment();
                // ft.replace(R.id.lyt_root,user);
            }
        });

        btn_order_history = view.findViewById(R.id.btn_order_history);
        btn_order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getActivity(), ShoppyOrderActivity.class);
                startActivity(intent);
            }
        });

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

        btn_privacy = view.findViewById(R.id.btn_privacy);
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url))));
            }
        });


        language=view.findViewById(R.id.lang);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("LANGUAGE").setView(view);

                dialog=builder.create();
                dialog.show();
*/

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Phone Ringtone");
                builder.setSingleChoiceItems(Languages, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lang.setText(Languages[i]);
                        //single_choice_selected = Languages[i];
                       // language.
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // Snackbar.make(parent_view, "selected : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();



                    }
                });
                builder.setNegativeButton("cancel", null);
                builder.show();

            }
        });

        return view;








    }
}

