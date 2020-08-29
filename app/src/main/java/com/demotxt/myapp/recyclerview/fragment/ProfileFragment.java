package com.demotxt.myapp.recyclerview.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.Order.Order_Activity;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.Login;
import com.demotxt.myapp.recyclerview.activity.Notification_Activity;
import com.demotxt.myapp.recyclerview.activity.Splash_Activity;
import com.demotxt.myapp.recyclerview.activity.Web_Activity;
import com.demotxt.myapp.recyclerview.ownmodels.CustomDialoag;
import com.demotxt.myapp.recyclerview.ownmodels.ImageFilePath;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;


public class ProfileFragment extends Fragment {

    // private SharedPref sharedPref;

    TextView username;
    ImageView phtotimage;
    TextView login, signup;
    ImageView logout;
    Dialog popup;
    CardView btn_order_history,notification, btn_privacy, dark, language, setting, fav, cart, shop,contact,exit;
    LinearLayout lyt_root;
    LinearLayout linearLayoutfornotlogin;
    ConstraintLayout linearLayoutforloggenin;
    boolean isAnimated;


    private SharedPreferences loginpref;
    SharedPreferences.Editor loginprefeditor;
    LottieAnimationView o, s, l, d, f, c, p, e, sh, con;
    private String userid;
    private boolean islogin;
    StringResponceFromWeb result;
    StringResponceFromWeb result2;
    String filename;
    public static boolean file_islarge;
    public File file;

    public static final int PICK_PHOTO_FOR_AVATAR = 2;
    public static final int PIC_CROP = 1;


    private static final String[] Languages = new String[]{
            "English", "Urdu"
    };

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
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
//selectphoto();
        file_islarge = false;
        loadLocale(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        final View view = inflater.inflate(R.layout.profilefragment, container, false);


        loginpref = getContext().getSharedPreferences("loginpref", MODE_PRIVATE);
        loginprefeditor = loginpref.edit();
        userid = String.valueOf(loginpref.getInt("userid", 0));
        //txt_user_name = view.findViewById(R.id.txt_user_name);
        //txt_user_email = view.findViewById(R.id.txt_user_email);
        //txt_user_phone = view.findViewById(R.id.txt_user_phone);
        //txt_user_address = view.findViewById(R.id.txt_user_address);
        username = view.findViewById(R.id.username);

        o = view.findViewById(R.id.ORDER);
        s = view.findViewById(R.id.NOTIY);
        l = view.findViewById(R.id.LANGUAGE);
        d = view.findViewById(R.id.DARKMODE);
        f = view.findViewById(R.id.FAVOURITE);
        c = view.findViewById(R.id.CART);
        sh = view.findViewById(R.id.SHOP);
        p = view.findViewById(R.id.PRIVACY);
        con = view.findViewById(R.id.CONTACT);
        e = view.findViewById(R.id.EXIT);
        notification= view.findViewById(R.id.NotificationCard);
        btn_order_history = view.findViewById(R.id.OrderHistoryCard);
        phtotimage = view.findViewById(R.id.selectimage);
        signup = view.findViewById(R.id.signup);
        language = view.findViewById(R.id.LanguageCard);
        linearLayoutfornotlogin = (LinearLayout) view.findViewById(R.id.fornotloggedin);
        linearLayoutforloggenin = (ConstraintLayout) view.findViewById(R.id.forloggedin);

        islogin = loginpref.getBoolean("loggedin", false);
        if (islogin) {
            linearLayoutfornotlogin.setVisibility(View.GONE);
            linearLayoutforloggenin.setVisibility(View.VISIBLE);
            btn_order_history.setVisibility(View.VISIBLE);
            notification.setVisibility(View.VISIBLE);
            GetProfile(hostinglink + "/Home/GetProfile");

        } else {
            linearLayoutforloggenin.setVisibility(View.GONE);
            linearLayoutfornotlogin.setVisibility(View.VISIBLE);
            btn_order_history.setVisibility(View.GONE);
            notification.setVisibility(View.GONE);
        }


       signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                intent.putExtra("loginfromprofile", true);
                startActivity(intent);
            }
        });
        phtotimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectphoto();
            }
        });


        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginprefeditor.putBoolean("loggedin", false);
                // loginprefeditor.putInt("userid", uid);
                loginprefeditor.remove("userid");
                loginprefeditor.commit();
                linearLayoutforloggenin.setVisibility(View.GONE);
                linearLayoutfornotlogin.setVisibility(View.VISIBLE);
                btn_order_history.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);

            }
        });

        //For Order history
        btn_order_history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isAnimated) {
                    p.playAnimation();
                    p.setSpeed(5);
                    isAnimated = true;
                } else {
                    p.cancelAnimation();
                    isAnimated = false;
                }
                Intent intent = new Intent(getActivity(), Order_Activity.class);
                startActivity(intent);
            }


        });


        //For Privacy Policy
        btn_privacy = view.findViewById(R.id.PrivacyCard);
        btn_privacy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isAnimated) {
                    p.playAnimation();
                    p.setSpeed(6);
                    isAnimated = true;
                } else {
                    p.cancelAnimation();
                    isAnimated = false;
                }
                String share_text = Html.fromHtml(getResources().getString(R.string.Privacy_Policy)).toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, share_text + "\n\n" + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


        //For  settings
        setting = view.findViewById(R.id.NotificationCard);
        setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAnimated) {
                    s.playAnimation();
                    isAnimated = true;
                } else {
                    s.cancelAnimation();
                    isAnimated = false;
                }

            }
        });

        //For Language settings
        language = view.findViewById(R.id.LanguageCard);
        language.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAnimated) {
                    l.playAnimation();
                    l.setSpeed(9);
                    isAnimated = true;
                } else {
                    l.cancelAnimation();
                    isAnimated = false;
                }

                showChangeLanguageDialog();
            }
        });

        //For Favourite
        fav = view.findViewById(R.id.FavouriteCard);
        fav.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (!isAnimated) {
                    f.playAnimation();
                    isAnimated = true;
                } else {
                    f.cancelAnimation();
                    isAnimated = false;
                }

                Fragment fragment = null;
                fragment = new FavoriteFragment();
                loadFragment(fragment);

            }
        });

        //For Cart
        cart = view.findViewById(R.id.CartCard);
        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAnimated) {
                    c.playAnimation();
                    isAnimated = true;
                } else {
                    c.cancelAnimation();
                    isAnimated = false;
                }
                Fragment fragment = null;
                fragment = new CartFragment();
                loadFragment(fragment);

            }
        });


        //For Darkmode
        dark = view.findViewById(R.id.DarkModeCard);
        dark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onclick", Toast.LENGTH_SHORT).show();

                try {

                    CustomDialoag dialoag = new CustomDialoag(getContext());
                    dialoag.showCustomDialog();
                } catch (Exception e) {

                    Toast.makeText(getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("error in profile", "error:" + e.getMessage());
                }
            }
        });


        //For Shop Registeration
        shop = view.findViewById(R.id.ShopCard);
        shop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAnimated) {
                    sh.playAnimation();
                    sh.setSpeed(4f);
                    isAnimated = true;
                } else {
                    sh.cancelAnimation();
                    isAnimated = false;
                }

                Intent intent = new Intent(getContext(), Web_Activity.class);
                startActivity(intent);


            }
        });

        //For Contact us
        contact = view.findViewById(R.id.ContactCard);
        contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAnimated) {
                    con.playAnimation();
                    con.setSpeed(4f);
                    isAnimated = true;
                } else {
                    con.cancelAnimation();
                    isAnimated = false;
                }


            }
        });

        //For Exiting the App
        exit = view.findViewById(R.id.ExitCard);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAnimated) {
                    e.playAnimation();
                    e.setSpeed(22);
                    isAnimated = true;
                } else {
                    e.cancelAnimation();
                    isAnimated = false;
                }

                System.exit(0);
            }
        });

        //For Notification Card
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifyInt = new Intent(getContext(), Notification_Activity.class);
                startActivity(notifyInt);
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
                    setLocale("en", getContext());
                    Intent intent = new Intent(getContext(), Splash_Activity.class);
                    startActivity(intent);
                    getActivity().recreate();

                } else if (i == 1) {
                    setLocale("ur", getContext());
                    Intent intent = new Intent(getContext(), Splash_Activity.class);
                    startActivity(intent);
                    getActivity().recreate();
                }
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private  static void setLocale(String lang,Context context) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        //saving data in shared preference

        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public static void loadLocale(Context cX) {

        SharedPreferences pref = cX.getSharedPreferences("Settings", MODE_PRIVATE);
        String lan = pref.getString("My_Lang", "");
        setLocale(lan, cX);
    }


    public String calculateFileSize(String pth) {
        //String filepathstr=filepath.toString();

        File file = new File(pth);

        float fileSizeInKB = file.length() / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        //  float fileSizeInMB = fileSizeInKB / 1024;

        String calString = Float.toString(fileSizeInKB);
        return calString;
    }


    public static Bitmap scaleImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > 600 || rotatedHeight > 600) {
            float widthRatio = ((float) rotatedWidth) / ((float) 600);
            float heightRatio = ((float) rotatedHeight) / ((float) 600);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        String type = context.getContentResolver().getType(photoUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (type.equals("image/png")) {
            srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        } else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        byte[] bMapArray = baos.toByteArray();
        baos.close();
        return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void selectphoto() {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                Uri uri = data.getData();
                Bitmap bitmap3 = scaleImage(getContext(), uri);
                //  File dir = Environment.getExternalStorageDirectory();
                //String path = dir.getAbsolutePath();
                String realPath = ImageFilePath.getPath(getActivity(), data.getData());
                // Toast.makeText(getContext(),"Path:"+realPath,Toast.LENGTH_SHORT).show();

                String size = calculateFileSize(realPath);
//Toast.makeText(getContext(),"size:"+size,Toast.LENGTH_SHORT).show();
                Log.i("Profile fragment", "size:" + size);
                double sizeinkb = Double.parseDouble(size);
                if (sizeinkb < 600)
                    file_islarge = false;
                else
                    file_islarge = true;
                //  String PATH=getImagePath(uri);
                /* file = new File(realPath);
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());*/   // way to get bitmap  from file
                // Uri u=file.toURI(uri.toString());
                // Toast.makeText(getActivity(),"sizeee: "+file.length(),Toast.LENGTH_SHORT).show();

               /*Long flength = file.length();
                if (flength < 1000000) {
                    file_islarge = true;
                } else
                    file_islarge = false;
*/
                filename = getFileName(uri);
                if (!file_islarge) {


                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    phtotimage.setImageBitmap(bitmap);

                    phtotimage.setVisibility(View.VISIBLE);
                    try {
                        String bitMapToString = BitMapToString(bitmap);
                        UploadProfile(hostinglink + "/Home/UploadProfile", bitMapToString, filename);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    file = new File(realPath);//


                    phtotimage.setImageBitmap(bitmap3);
                    // bitmap2.recycle();

                    //  Bitmap resized = Bitmap.createScaledBitmap(bitmap, 600, 600, true);

                    String bitMapToString = BitMapToString(bitmap3);
                    UploadProfile(hostinglink + "/Home/UploadProfile", bitMapToString, filename);




                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }

    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void UploadProfile(String url, final String bitmapstr, final String filename) {
        final RequestQueue request = Volley.newRequestQueue(getContext());


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result2 = gson.fromJson(response, StringResponceFromWeb.class);
                            Toast.makeText(getContext(), "Responce:" + result2.getresult(), Toast.LENGTH_SHORT).show();
                            if (result2.getresult().equals("updated")) {

                                String url = result2.getLogo();
                                url = hostinglink + url;
                                Picasso.get().load(url).into(phtotimage);

                                /*  try{
                                      GetProfile(hostinglink +"/Home/GetProfile");
                                  }catch (Exception e)


                                  {
                                      Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                                  }*/


                            }


                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(getContext(),  Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                /*JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }*/
                params.put("userid", userid);
                params.put("bitmapstr", bitmapstr);
                params.put("filename", filename);

                //  params.p

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };


        request.add(rRequest);

    }


    public void GetProfile(String url) {
        final RequestQueue request = Volley.newRequestQueue(getContext());


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {

                            // Toast.makeText(getContext(),"Responce:"+response,Toast.LENGTH_SHORT).show();
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result = gson.fromJson(response, StringResponceFromWeb.class);

                            if (result.getresult().equals("error")) {
                                Toast.makeText(getContext(), "error:" + result.getErrorResult(), Toast.LENGTH_SHORT).show();
                            } else if (!result.getUsername().equals(null)) {
                                try {

                                    username.setText(result.getUsername());
                                    String url = result.getresult();
                                    url = hostinglink + url;
                                    Picasso.get().load(url).into(phtotimage);
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }


                        } catch (Exception e) {
                            Toast.makeText(getContext(), "excaption:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        //  Toast.makeText(ShoppyProductListActivity.this, response, Toast.LENGTH_SHORT).show();


                        // response
                        //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(getContext(),  Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                /*JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }*/
                params.put("userid", userid);


                //  params.p

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };


        request.add(rRequest);


    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}