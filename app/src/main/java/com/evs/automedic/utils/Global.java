package com.evs.automedic.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.evs.automedic.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static android.content.Context.LOCATION_SERVICE;

public class Global {

    public static String URL = "http://demo2.evirtualservices.co/cuckoo/services/index";
//    public static String PUBLISHABLE_KEY = "pk_live_phy6oQoNq0ZRjTKWpI668OD9004EN4r4tr";
//    public static String SECRET_KEY = "sk_live_AOGf0AXuPxBRiQNEt4Si2YB000YU9lOA2y";

    //    public static String PUBLISHABLE_KEY = "pk_test_P0sek3JBlehtPxunz63NZgoc00Pl3WDYs1";
//    public static String SECRET_KEY = "sk_test_roDXMdY54wIDtVLwtI87klHD000vZKzZvl";
    public static String fbkey = "key=AAAAx2OhDao:APA91bE67IvRR--jm3aVhGviaVdtTs57h_j99YNHnqIMwsgtGSMDq59pTtd4SE6DRjk7JrFlezCwaAYGZQckBnKp467mkWG5rqrdY_gjz3vHh1lPT4Sw6InLQPkhD1zySWc2PnBgdyk2";


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean validatename(String firstrname) {
        if (firstrname.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date;
        date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    public static boolean validateLength(String firstrname, int s) {
        if (firstrname.length() >= s) {
            return true;
        } else {
            return false;
        }

    }

    public final static boolean validateEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean password(String firstrname) {
        if (firstrname.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }

    public final static boolean ValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static ProgressDialog getProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static ACProgressFlower getProgress(Context context, String msg) {


        ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(msg)
                .fadeColor(Color.DKGRAY).build();
        return dialog;
    }

    public static void msgDialog(Activity ac, String msg) {
        try {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ac);
            alertbox.setTitle(ac.getResources().getString(R.string.app_name));
            alertbox.setMessage(msg);
            alertbox.setPositiveButton(ac.getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            //  ac.onBackPressed();
                        }
                    });
            alertbox.show();
        } catch (Exception e) {
        }
    }

    public static void msgDialogBack(final Activity ac, String msg) {
        try {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ac);
            alertbox.setTitle(ac.getResources().getString(R.string.app_name));
            alertbox.setMessage(msg);
            alertbox.setPositiveButton(ac.getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            ac.onBackPressed();
                        }
                    });
            alertbox.show();
        } catch (Exception e) {
        }
    }

    public static void showDialog(final Activity ac) {
        android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(ac);
        alertbox.setTitle(ac.getResources().getString(R.string.app_name));
        alertbox.setMessage("No Internet Connection!");
        alertbox.setPositiveButton(ac.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        ac.finish();
                    }
                });
        alertbox.show();
    }

    public static void showSettingsDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private static void openSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean GpsEnable(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean CheckGpsLocation(final Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }

    public static void showGPSDisabledAlertToUser(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")

                .setCancelable(false)
                .setPositiveButton("Go To Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
