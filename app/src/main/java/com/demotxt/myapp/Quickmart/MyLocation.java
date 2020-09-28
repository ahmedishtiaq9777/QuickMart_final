package com.demotxt.myapp.Quickmart;

import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.muddzdev.styleabletoast.StyleableToast;


public class MyLocation extends AppCompatActivity {
    Timer timer1;
    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context mContext;

    public boolean getLocation(Context context, LocationResult result) {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult = result;
        //
        mContext = context;
        //
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled)
            return false;

        //permission for GPS
        if (gps_enabled)
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
            } else {
                StyleableToast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT, R.style.PermissionToast).show();
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
            }

        //permission for Network
        if (network_enabled)
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                }, 2);
            } else {
                StyleableToast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT, R.style.PermissionToast).show();
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
            }


        timer1 = new Timer();
        timer1.schedule(new GetLastLocation(), 20000);
        return true;
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 || requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else
                StyleableToast.makeText(mContext, "Error Permission", Toast.LENGTH_SHORT, R.style.NotGrantedToast).show();
        }
    }

    final LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(@NonNull Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);

            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(@NonNull String provider) {
        }

        public void onProviderEnabled(@NonNull String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(@NonNull Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);

            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(@NonNull String provider) {
        }

        public void onProviderEnabled(@NonNull String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {

            Location net_loc = null, gps_loc = null;

            //permission for GPS
            if (gps_enabled)
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    }, 1);
                } else {
                    gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

            //permission for Network
            if (network_enabled)
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    }, 2);
                } else {
                    net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

            //if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime())
                    locationResult.gotLocation(gps_loc);
                else
                    locationResult.gotLocation(net_loc);
                return;
            }

            if (gps_loc != null) {
                locationResult.gotLocation(gps_loc);
                lm.removeUpdates(locationListenerGps);
                lm.removeUpdates(locationListenerNetwork);
                return;
            }
            if (net_loc != null) {
                locationResult.gotLocation(net_loc);
                lm.removeUpdates(locationListenerGps);
                lm.removeUpdates(locationListenerNetwork);
                return;
            }
            locationResult.gotLocation(null);
        }
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}


