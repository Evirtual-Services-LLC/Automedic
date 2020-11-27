package com.evs.automedic.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.evs.automedic.R;
import com.evs.automedic.model.UserModel;
import com.evs.automedic.model.UserResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.SessionManager;
import com.evs.automedic.utils.Utills;
import com.evs.automedic.viewModels.UserAuthListener;
import com.evs.automedic.viewModels.UserViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;

import static com.evs.automedic.R.layout.activity_login;

public class LoginScreen extends AppCompatActivity {
    @BindView(R.id.edttxt_email)
    EditText edttxt_email;
    @BindView(R.id.edttxt_password)
    EditText edttxt_password;
    String username, password_string;
    ACProgressFlower progressDialog;
    UserViewModel userViewModel;
    SharedPreferences prefs;
    int PERMISSION_ALL = 1;
    String getLat, getlongs;
    private FusedLocationProviderClient fusedLocationClient;
//    String getLat, getlongs;
//    int PERMISSION_ALL = 1;
//    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        ButterKnife.bind(this);

        intialize();
    }

    private void intialize() {
        Utills.StatusBarColour(LoginScreen.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.text_toolbar);
        mTitle.setText("Sign in".toUpperCase());
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

        prefs = PreferenceManager.getDefaultSharedPreferences(LoginScreen.this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        progressDialog = Global.getProgress(this, "Login...");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getcheckLocation();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                SessionManager.save_device_token(prefs, newToken);
                Log.e("newToken", newToken);

            }
        });
    }

    @OnClick({R.id.btn_sign_in, R.id.tv_sign_up, R.id.tv_forgot_password})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_in) {
            if (Global.isOnline(this)) {
                SessionManager.save_check_login(prefs, true);
                startActivity(new Intent(this, MainActivity.class));
                if (validation()) {
                  //  userViewModel.hitLoginApi(username, password_string, getLat, getlongs, LoginScreen.this);

                }
            }

        } else if (view.getId() == R.id.tv_sign_up) {
            startActivity(new Intent(this, RegisterScreen.class));

        } else if (view.getId() == R.id.tv_forgot_password) {
            startActivity(new Intent(LoginScreen.this, ForgotPassword.class));
        }
    }

    private boolean validation() {
        username = edttxt_email.getText().toString().trim();
        password_string = edttxt_password.getText().toString().trim();
        if (!Global.ValidEmail(username)) {
            edttxt_email.setError(Html.fromHtml("<font color='blue'>Enter correct emailid</font>"));
            return false;
        }
        if (!Global.password(password_string)) {
            //    edttxt_email.setError(null);
            edttxt_password.setError(Html.fromHtml("<font color='blue'>Enter correct password</font>"));
            return false;
        }
        return true;
    }

//    @Override
//    public void onStarted() {
//        progressDialog.show();
//    }

//    @Override
//    public void onSuccess(LiveData<UserResponse> user) {
//        progressDialog.dismiss();
//        user.observe(this, new Observer<UserResponse>() {
//            @Override
//            public void onChanged(UserResponse userResponse) {
//                if (userResponse.getStatus().equalsIgnoreCase("Success")) {
//                    SessionManager.save_check_login(prefs, true);
//                    saveUserData(user.getValue().getUser());
//                    Global.showToast(LoginScreen.this, user.getValue().getMsg());
//                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                }
//            }
//        });
//    }

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

    private void saveUserData(UserModel user) {
        SessionManager.save_user_id(prefs, user.getUserId());
        SessionManager.save_name(prefs, user.getFullName());
        SessionManager.save_emailid(prefs, user.getEmail());
        SessionManager.save_address(prefs, user.getAddress());
        SessionManager.save_mobile(prefs, user.getContactNumber());
        SessionManager.save_image(prefs, user.getImage());
        SessionManager.save_device(prefs, user.getDevice());
        SessionManager.save_socialId(prefs, user.getSocialId());
    }

//    @Override
//    public void onFailure(String message) {
//        progressDialog.dismiss();
//        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
//    }
}
