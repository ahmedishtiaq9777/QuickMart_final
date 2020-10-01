package com.demotxt.myapp.Quickmart.ownmodels;


import android.content.Context;
import android.content.res.AssetManager;

import com.demotxt.myapp.Quickmart.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOHelper {

    private String filename;
    private Context context;

 public    IOHelper(Context context) {
        filename = context.getString(R.string.UserLog);
        this.context = context;
    }

    public String stringFromStream(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
public void createbydefault(List<UserViewLog> list){
    /*List<UserViewLog> TEMPLIST=new ArrayList<UserViewLog>();
    UserViewLog userViewLog=new UserViewLog();
    userViewLog.setUserid("1");
    userViewLog.setCategory("MenShirts");
    userViewLog.setNOV("1");
    TEMPLIST.add(userViewLog);*/

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String strlist=gson.toJson(list);
    writeToFile(strlist);

}

    public void writeToFile(String str) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(str.getBytes(), 0, str.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String stringFromAsset() {
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(filename);
            String result = stringFromStream(is);
            is.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

  public   String stringFromFile() {
        try {
            FileInputStream fis = context.openFileInput(filename);
            String str = stringFromStream(fis);
            fis.close();
            return str;
        } catch (IOException e) {
            String fromAsset = stringFromAsset();

            writeToFile(fromAsset);
            return fromAsset;
        }
    }

}