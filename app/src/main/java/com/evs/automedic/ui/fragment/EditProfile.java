package com.evs.automedic.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import com.evs.automedic.R;
import com.evs.automedic.model.UserModel;
import com.evs.automedic.model.UserResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.GpsTracker;
import com.evs.automedic.utils.ImageFilePath;
import com.evs.automedic.utils.SessionManager;
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

public class EditProfile extends Fragment  {
    @BindView(R.id.iv_location)
    ImageView iv_location;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    EditText et_userName, et_email, et_phone, et_address;
    RoundedImageView imageProfileEdit;
    private String selectedImagePath = "";
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    LocationManager locationManager;
    Geocoder geocoder;
    LocationListener locationListener;
    GpsTracker gps;
    //add location addreee
    String lat = "", longs = "", address = "";
    ProgressDialog progress;
    UserViewModel viewModel;
    private SharedPreferences prefs;
    Button btn_updateProfile;
    int PERMISSION_ALL = 1;
    String getLat, getlongs;
    private FusedLocationProviderClient fusedLocationClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);
        ButterKnife.bind(this, view);
        viewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        progress = Global.getProgressDialog(getContext(), "Loading..");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        imageProfileEdit = view.findViewById(R.id.imageProfileEdit);
        btn_updateProfile = view.findViewById(R.id.btn_updateProfile);
        et_userName = view.findViewById(R.id.et_userName);
        et_email = view.findViewById(R.id.et_email);
        et_phone = view.findViewById(R.id.et_phone);
        et_address = view.findViewById(R.id.et_user_address);

        et_userName.setText(SessionManager.get_name(prefs));
        et_email.setText(SessionManager.get_emailid(prefs));
        et_phone.setText(SessionManager.get_mobile(prefs));
        et_address.setText(SessionManager.get_address(prefs));

        imageProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        Glide.with(getActivity()).load(SessionManager.get_image(prefs)).placeholder(R.drawable.user).into(imageProfileEdit);

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("EDIT PROFILE");
        getcheckLocation();

    }


    private void validation() {
        if (!Global.validatename(et_userName.getText().toString())) {
            et_userName.setError(Html.fromHtml("<font color='red'>Enter correct Name</font>"));
        } else if (et_phone.getText().toString().length() < 10) {
            et_phone.setError(Html.fromHtml("<font color='red'>Enter correct Phone</font>"));
        } else if (et_address.getText().toString().isEmpty()) {
            et_address.setError(Html.fromHtml("<font color='red'>Enter  Address</font>"));
        } else if (Global.isOnline(getContext())) {
//            viewModel.callEditprofile(SessionManager.get_user_id(prefs), et_userName.getText().toString(), et_phone.getText().toString(),
//                    et_address.getText().toString(), selectedImagePath, this);
        } else Global.showDialog(getActivity());

    }

    private void selectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectedImagePath = String.valueOf(destination);
                Glide.with(getActivity()).load(selectedImagePath).error(R.drawable.user).dontAnimate().into(imageProfileEdit);

            } else if (requestCode == SELECT_FILE) {
                Bitmap bm = null;
                if (data != null) {
                    try {
                        Uri selectedImageUri = data.getData();
                        selectedImagePath = ImageFilePath.getPath(getContext(), selectedImageUri);
                        Glide.with(getActivity()).load(selectedImagePath).error(R.drawable.user).dontAnimate().into(imageProfileEdit);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
//
//    @Override
//    public void onStarted() {
//        progress.show();
//    }
//
//    @Override
//    public void onSuccess(LiveData<UserResponse> data) {
//        progress.dismiss();
//        if (data != null) {
//            if (data.getValue().getStatus().equalsIgnoreCase("success")) {
//                savainformation(data.getValue().getUser());
//                Global.showToast(getActivity(), "Profile Updated Successfully");
//            }
//        }
//    }

    private void savainformation(UserModel user) {
        SessionManager.save_name(prefs, user.getFullName());
        SessionManager.save_mobile(prefs, user.getContactNumber());
        SessionManager.save_address(prefs, user.getAddress());
        SessionManager.save_image(prefs, user.getImage());

        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(getActivity().getIntent());
        getActivity().overridePendingTransition(0, 0);

    }

//    @Override
//    public void onFailure(String message) {
//        progress.dismiss();
//        Global.msgDialogBack(getActivity(), message);
//    }

    private void getcheckLocation() {
        if (checkLocation()) {
            if (!Global.GpsEnable(getActivity())) {
                Global.showGPSDisabledAlertToUser(getActivity());
            } else {
                lastLocation();
            }
        } else Global.showSettingsDialog(getActivity());
    }

    private void location() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            updateLocation(lastKnownLocation);

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void updateLocation(Location location) {

        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            lat = String.valueOf(listAddresses.get(0).getLatitude());
            SessionManager.save_BLat(prefs, lat);
            longs = String.valueOf(listAddresses.get(0).getLongitude());
            SessionManager.save_Blong(prefs, longs);
            String address = listAddresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = listAddresses.get(0).getLocality();
            String state = listAddresses.get(0).getAdminArea();
            String country = listAddresses.get(0).getCountryName();
            String postalCode = listAddresses.get(0).getPostalCode();
            String knownName = listAddresses.get(0).getFeatureName();
            et_address.setText(address);
            Log.i("Address", address);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private boolean checkLocation() {
        int courcelocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (courcelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_ALL);
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
                getActivity().finish();
        }
    }

    @SuppressLint("MissingPermission")
    private void lastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations getActivity() can be null.
                        if (location != null) {
                            getAddress(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    private void getAddress(double lat, Double longs) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, longs, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            et_address.setText(address);
            getLat = String.valueOf(lat);
            getlongs = String.valueOf(longs);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.iv_back, R.id.iv_location})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_location) {
            getcheckLocation();
        } else if (view.getId() == R.id.iv_back) {
            getActivity().onBackPressed();
        }

    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }
}
