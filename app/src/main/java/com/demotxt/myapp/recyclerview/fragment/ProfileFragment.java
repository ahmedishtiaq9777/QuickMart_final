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
import com.demotxt.myapp.recyclerview.activity.Signup;
import com.demotxt.myapp.recyclerview.ownmodels.CustomDialoag;
import com.demotxt.myapp.recyclerview.ownmodels.CustomInternetDialog;
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
    TextView logout;
    Dialog popup;
    CardView btn_order_history, btn_privacy, dark, language,setting, fav,cart, exit;
    LinearLayout lyt_root;
   LinearLayout linearLayoutfornotlogin,linearLayoutforloggenin;
    private SharedPreferences  loginpref;
    SharedPreferences.Editor  loginprefeditor;
    LottieAnimationView o,s,l,d,f,c,p,e;
    private String userid;
    private boolean islogin;
    StringResponceFromWeb result;
    StringResponceFromWeb result2;
    String filename;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//selectphoto();
        loadLocale(getContext());
       // Fragment fragment=null;
           //  fragment=   new ProfileSubFragment();
//loadsubFragment(fragment);
       // sharedPref = new SharedPref(getActivity());

       ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        final View view = inflater.inflate(R.layout.profilefragment, container, false);

      //  sharedPref = new SharedPref(getActivity());
/*
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();*/

      //  lyt_root = view.findViewById(R.id.lyt_root);
       // if (Config.ENABLE_RTL_MODE) {
       //     lyt_root.setRotationY(180);
       // }
        loginpref = getContext().getSharedPreferences("loginpref", MODE_PRIVATE);
        loginprefeditor=loginpref.edit();
        userid=String.valueOf(loginpref.getInt("userid",0));
        //txt_user_name = view.findViewById(R.id.txt_user_name);
        //txt_user_email = view.findViewById(R.id.txt_user_email);
        //txt_user_phone = view.findViewById(R.id.txt_user_phone);
        //txt_user_address = view.findViewById(R.id.txt_user_address);
        username=view.findViewById(R.id.username);
        o = view.findViewById(R.id.ORDER);
        s = view.findViewById(R.id.SETTING);
        l = view.findViewById(R.id.LANGUAGE);
        d = view.findViewById(R.id.DARKMODE);
        f = view.findViewById(R.id.FAVOURITE);
        c = view.findViewById(R.id.CART);
        p = view.findViewById(R.id.PRIVACY);
        e = view.findViewById(R.id.EXIT);
        btn_order_history = view.findViewById(R.id.OrderHistoryCard);
        phtotimage=view.findViewById(R.id.selectimage);
        signup=view.findViewById(R.id.signup);
        language = view.findViewById(R.id.LanguageCard);
        linearLayoutfornotlogin=(LinearLayout) view.findViewById(R.id.fornotloggedin);
        linearLayoutforloggenin=(LinearLayout) view.findViewById(R.id.forloggedin);

         islogin=loginpref.getBoolean("loggedin",false);
if(islogin)
{
    linearLayoutfornotlogin.setVisibility(View.GONE);
    linearLayoutforloggenin.setVisibility(View.VISIBLE);
    btn_order_history.setVisibility(View.VISIBLE);
    GetProfile(hostinglink +"/Home/GetProfile");

}else {
    linearLayoutforloggenin.setVisibility(View.GONE);
    linearLayoutfornotlogin.setVisibility(View.VISIBLE);
    btn_order_history.setVisibility(View.GONE);
}




      //  txt_user_name.setText(sharedPref.getYourName());
       // txt_user_email.setText(sharedPref.getYourEmail());
        //txt_user_address.setText(sharedPref.getYourAddress());
        //txt_user_phone.setText(sharedPref.getYourPhone());

    /*    login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Login.class);
                intent.putExtra("loginfromprofile",true);
                startActivity(intent);
            }
        });*/
signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), Signup.class);
        startActivity(intent);
    }
});
phtotimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        selectphoto();
    }
});

logout= view.findViewById(R.id.logout);
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

               /* Intent intent = new Intent(getActivity(), UsersettingFragment.class);
                startActivity(intent);*/
                ///FragmentTransaction ft=getChildFragmentManager().beginTransaction();
                // UsersettingFragment user=new UsersettingFragment();
                // ft.replace(R.id.lyt_root,user);
            }
        });

        //For Order history
        btn_order_history.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View view) {
                if (!isAnimated){
                    p.playAnimation();
                    p.setSpeed(5);
                    isAnimated=true;}
                else {
                    p.cancelAnimation();
                    isAnimated=false;
                }
                Intent intent = new Intent(getActivity(), Order_Activity.class);
                startActivity(intent);
            }


        });




        //For Privacy Policy
        btn_privacy = view.findViewById(R.id.PrivacyCard);
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View view) {
                if (!isAnimated){
                    p.playAnimation();
                    p.setSpeed(6);
                    isAnimated=true;}
                else {
                    p.cancelAnimation();
                    isAnimated=false;
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
        setting = view.findViewById(R.id.SettingCard);
        setting.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View v) {
                if (!isAnimated){
                    s.playAnimation();
                    isAnimated=true;}
                else {
                    s.cancelAnimation();
                    isAnimated=false;
                }

            }
        });

        //For Language settings
        language = view.findViewById(R.id.LanguageCard);
        language.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View v) {
                if (!isAnimated){
                    l.playAnimation();
                    l.setSpeed(9);
                    isAnimated=true;}
                else {
                    l.cancelAnimation();
                    isAnimated=false;
                }

                showChangeLanguageDialog();
            }
        });

        //For Favourite
        fav = view.findViewById(R.id.FavouriteCard);
        fav.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override

            public void onClick(View v) {
                if (!isAnimated){
                    f.playAnimation();
                    isAnimated=true;
                }
                else {
                    f.cancelAnimation();
                    isAnimated=false;
                }

                Fragment fragment = null;
                fragment = new FavoriteFragment();
                loadFragment(fragment);

            }
        });

        //For Cart
        cart = view.findViewById(R.id.CartCard);
        cart.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View v) {
                if (!isAnimated){
                    c.playAnimation();
                    isAnimated=true;}
                else {
                    c.cancelAnimation();
                    isAnimated=false;
                }
                Fragment fragment = null;
                fragment = new CartFragment();
                loadFragment(fragment);

            }
        });

        //For Darkmode
        dark= view.findViewById(R.id.DarkModeCard);
        dark.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"onclick",Toast.LENGTH_SHORT).show();

try{

                    CustomDialoag dialoag = new CustomDialoag(getActivity());
                    dialoag.showCustomDialog();
                }catch (Exception e) {

Toast.makeText(getContext(),"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
Log.i("error in profile","error:"+e.getMessage());
}
/*

                if (!isAnimated){
                    d.playAnimation();
                    isAnimated=true;}
                else {
                    d.cancelAnimation();
                    isAnimated=false;
                }

*/

            }
        });

        //For Exiting the App
        exit = view.findViewById(R.id.ExitCard);
        exit.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated;
            @Override
            public void onClick(View v) {
                if (!isAnimated){
                    e.playAnimation();
                    e.setSpeed(22);
                    isAnimated=true;}
                else {
                    e.cancelAnimation();
                    isAnimated=false;
                }

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
                    setLocale("en",getContext());
                    getActivity().recreate();
                } else if (i == 1) {
                    setLocale("ur",getContext());
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
        setLocale(lan,cX);
    }




public  void selectphoto(){


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
                Uri uri=data.getData();
                filename=getFileName(uri);



   Bitmap bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
   phtotimage.setImageBitmap(bitmap);
   phtotimage.setVisibility(View.VISIBLE);
   try {
       String bitMapToString = BitMapToString(bitmap);
       UploadProfile(hostinglink +"/Home/UploadProfile", bitMapToString, filename);
   }catch (Exception e)
   {
       Toast.makeText(getContext(),"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
   }
//phtotimage.setImageURI(data.getData());


            /*  InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());*/

               // BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inSampleSize = 2; //Scale it down
                //options.inPreferredConfig = Bitmap.Config.RGB_565;
               // Bitmap b = BitmapFactory.decodeStream( inputStream, null, options );

                /* Bitmap binput = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();


                binput.compress(Bitmap.CompressFormat.JPEG,50,stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                String bitMapToString=BitMapToString(compressedBitmap);
               */


              //  getconnection("dsds",bitMapToString);
                //phtotimage.setImageBitmap (compressedBitmap);


                //   Uri uri= getImageUri(getContext(),binput);

            //  Bitmap finalbitmap= handleSamplingAndRotationBitmap(getContext(),uri);
              //  phtotimage.setImageBitmap(finalbitmap);
            // calculateInSampleSize(getContext(),uri);






               // FileOutputStream fos = openFileOutput("desiredFilename.png", Context.MODE_PRIVATE);
               /* File pictureFile = getOutputMediaFile();*/
              //  Bitmap bOutput;
             //   Matrix matrix = new Matrix();
             //   matrix.preScale(-0.5f, 1.0f);
              //  bOutput = Bitmap.createBitmap(binput, 0, 0, binput.getWidth(), binput.getHeight(), matrix, true);*/
              /*  Drawable drawable=new BitmapDrawable(this.getResources(),binput);*/
             //   phtotimage.setImageDrawable(drawable);

              //  phtotimage.setScaleType(ImageView.ScaleType.valueOf("fitXY"));
       /*   phtotimage.setImageBitmap(binput);*/

              //  phtotimage.setImageBitmap((decodeImage(R.drawable.justry)));
               // phtotimage.setImageBitmap (bitmap);

            }catch (Exception e)
            {
              Toast.makeText(getContext(),"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
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

    public  void    UploadProfile(String url, final String bitmapstr, final String filename) {
        final RequestQueue request = Volley.newRequestQueue(getContext());


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                         Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result2=gson.fromJson(response, StringResponceFromWeb.class);
                              Toast.makeText(getContext(),"Responce:"+result2.getresult(),Toast.LENGTH_SHORT).show();
                              if(result2.getresult().equals("updated"))
                              {



                                  try{
                                      GetProfile(hostinglink +"/Home/GetProfile");
                                  }catch (Exception e)
                                  {
                                      Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                                  }



                              }





                        } catch (Exception e) {
                             Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
        )  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                /*JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }*/
                params.put("userid",userid);
                params.put("bitmapstr",bitmapstr);
                params.put("filename",filename);

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




    public  void    GetProfile(String url) {
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
                            result=gson.fromJson(response, StringResponceFromWeb.class);

                            if(result.getresult().equals("error"))
                            {
                                Toast.makeText(getContext(),"error:"+result.getErrorResult(),Toast.LENGTH_SHORT).show();
                            }else if(!result.getUsername().equals(null)){
                                try{

                                    username.setText(result.getUsername());
                                    String url=  result.getresult();
                                    url=hostinglink+url;
                                    Picasso.get().load(url).into(phtotimage);
                                }catch (Exception e)
                                {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                               // Bitmap bitmap=StringToBitMap(strbitmap);

                              //  phtotimage.setImageBitmap(bitmap);
                            }







                        } catch (Exception e) {
                             Toast.makeText(getContext(), "excaption:"+e.getMessage(), Toast.LENGTH_LONG).show();
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
        )  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                /*JSONArray jsonArray= new JSONArray();
                for (String  i:cartids) {
                    jsonArray.put(i);
                }*/
                params.put("userid",userid);



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

    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public Bitmap decodeImage(int resourceId) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), resourceId, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 100; // you are free to modify size as your requirement

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeResource(getResources(), resourceId, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}





