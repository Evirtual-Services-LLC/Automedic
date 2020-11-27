package com.evs.automedic.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.evs.automedic.R;
import com.evs.automedic.model.UserModel;
import com.evs.automedic.model.UserResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.ImageFilePath;
import com.evs.automedic.utils.SessionManager;
import com.evs.automedic.utils.Utills;
import com.evs.automedic.viewModels.AuthListener;
import com.evs.automedic.viewModels.UserViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;

public class RegisterScreen extends AppCompatActivity implements AuthListener<UserResponse> {
    @BindView(R.id.edttxt_name)
    EditText edttxt_name;
    @BindView(R.id.edttxt_email)
    EditText edttxt_email;
    @BindView(R.id.edttxt_phone)
    EditText edttxt_phone;
    @BindView(R.id.edttxt_password)
    EditText edttxt_password;
    String name_string, email_string, password_string, phone_string;
    ACProgressFlower progressDialog;
    UserViewModel userViewModel;
    SharedPreferences prefs;
    int PERMISSION_ALL = 1;
    String getLat, getlongs;
    private FusedLocationProviderClient fusedLocationClient;
    //caputre image
    RoundedImageView uploadSelfiImageFirst;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    private String selectedImagePath = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        intialize();
    }

    private void intialize() {
        Utills.StatusBarColour(RegisterScreen.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.text_toolbar);
        mTitle.setText("REGISTER".toUpperCase());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(RegisterScreen.this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        progressDialog = Global.getProgress(this, "Sign Up...");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getcheckLocation();
    }

    private void selectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    openCamera();
                } else if (items[item].equals("Gallery")) {
                    openGalery();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void openGalery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void getcheckLocation() {
        if (checkLocation()) {
            if (!Global.GpsEnable(this)) {
                Global.showGPSDisabledAlertToUser(this);
            } else {
                lastLocation();
            }
        } else Global.showSettingsDialog(this);
    }

    private void getAddress(double lat, Double longs) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, longs, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            getLat = String.valueOf(lat);
            getlongs = String.valueOf(longs);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    private void lastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            getAddress(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    private boolean checkLocation() {
        int courcelocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (courcelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_ALL);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                lastLocation();
                return;
            }
            default:
                this.finish();
        }
    }

    @OnClick({R.id.btn_sign_up, R.id.tv_sign_in})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_up) {
            SessionManager.save_check_login(prefs, true);
             startActivity(new Intent(this, CompleteProfile.class));
            if (Global.isOnline(this)) {
                if (validation()) {
                  //  userViewModel.hitVendorRegistrationApi(name_string,email_string, phone_string, password_string,selectedImagePath,SessionManager.get_device_token(prefs),getLat,getlongs,this);
                }
            }
        } else if (view.getId() == R.id.tv_sign_in) {
            startActivity(new Intent(this, LoginScreen.class));

        }
    }

    private boolean validation() {
        name_string = edttxt_name.getText().toString().trim();
        email_string = edttxt_email.getText().toString().trim();
        phone_string = edttxt_phone.getText().toString().trim();
        password_string = edttxt_password.getText().toString().trim();

        if (!Global.validatename(name_string)) {
            edttxt_name.setError(Html.fromHtml("<font color='red'>Enter correct Name</font>"));
            return false;
        } else if (!Global.ValidEmail(email_string)) {
            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter correct emailid</font>"));
            return false;
        }else if (!Global.validatename(phone_string)) {
            edttxt_phone.setError(Html.fromHtml("<font color='red'>Enter Phone Number</font>"));
            return false;
        }else if (phone_string.length() < 10) {
            edttxt_phone.setError(Html.fromHtml("<font color='red'>Enter correct Phone</font>"));
            return false;
        }
        else if (!Global.password(password_string)) {
            edttxt_password.setError(Html.fromHtml("<font color='red'>Enter correct password</font>"));
            return false;
        }
        return true;
    }

    @Override
    public void onStarted() {
        progressDialog.show();

    }

    @Override
    public void onSuccess(LiveData<UserResponse> data) {
        progressDialog.dismiss();
        if (data.getValue().getStatus().equalsIgnoreCase("success")) {
            SessionManager.save_check_login(prefs, true);
            System.out.println("User Id === " + data.getValue().getUser().getUserId());
            System.out.println("User email === " + data.getValue().getUser().getEmail());
            saveUserData(data.getValue().getUser());
          //  Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
          //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         //   startActivity(intent);
        }

    }


    private void saveUserData(UserModel user) {
        SessionManager.save_user_id(prefs, user.getUserId());
        SessionManager.save_name(prefs, user.getFullName());
        SessionManager.save_emailid(prefs, user.getEmail());
        SessionManager.save_mobile(prefs, user.getContactNumber());
        SessionManager.save_image(prefs, user.getImage());
        SessionManager.save_device_token(prefs, user.getDeviceToken());
    }

    @Override
    public void onFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
