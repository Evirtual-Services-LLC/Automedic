package com.evs.automedic.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.evs.automedic.R;
import com.evs.automedic.android.Stripe;
import com.evs.automedic.android.TokenCallback;
import com.evs.automedic.android.model.Card;
import com.evs.automedic.android.model.Token;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.SessionManager;
import com.evs.automedic.viewModels.UserViewModel;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.stripe.model.Charge;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentFrag extends Fragment  {
   // public static final String PUBLISHABLE_KEY = "pk_live_QGTfLSgglNBaXXN6It2vuzd0";
    public static String PUBLISHABLE_KEY = "pk_test_P0sek3JBlehtPxunz63NZgoc00Pl3WDYs1";
    String TokenIds = "";
    String expiryval,cvv,cardNumber;
    public static final String BUNDLE_KEY_ADDRESS_DETAILS = "addressDetails";
    EditText creditCardNumberEdit, cardExpireMonth, et_cvv, et_cardNumber, et_expDate;
    TextView sch_laterUser, tv_amountTitle;
    boolean isValid = true;
    UserViewModel userViewModel;
    SharedPreferences prefs;
    ProgressDialog progressDialog;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
  //  AddressModel addressModel;
    String userId, totalValue;
 //   CartModel cartModel;
  //  ArrayList<CartModel> list_product = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    String getLat, getlongs;
    int PERMISSION_ALL = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_payment, container, false);
        ButterKnife.bind(this, view);
        intilaize(view);

        return view;
    }

    private void intilaize(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
//            String json = bundle.getString(BUNDLE_KEY_DETAILS);
//            list_product = bundle.getParcelableArrayList(BUNDLE_KEY_DETAILS_CART);
//            String json2 = bundle.getString(BUNDLE_KEY_ADDRESS_DETAILS);
//            addressModel = new Gson().fromJson(json2, AddressModel.class);
//            totalValue = bundle.getString(BUNDLE_KEY_TOTAL_PRICE);


        }
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        progressDialog = Global.getProgressDialog(getActivity(), "Please wait...");
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        userId = SessionManager.get_user_id(prefs);

        tv_amountTitle = (TextView) view.findViewById(R.id.tv_amountTitle);
        creditCardNumberEdit = view.findViewById(R.id.creditCardNumberEdit);
        cardExpireMonth = view.findViewById(R.id.cardExpireMonth);
        et_cvv = view.findViewById(R.id.et_cvv);
        et_cardNumber = view.findViewById(R.id.et_cardNumber);
        et_expDate = view.findViewById(R.id.et_expDate);
        sch_laterUser = (TextView) view.findViewById(R.id.sch_laterUser);

        tv_amountTitle.setText("Total Payable Amount : $"+totalValue);


        creditCardNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_cardNumber.setText(s);
//                if(CardValidator.validateCardNumber(s.toString())){}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        TextWatcher mDateEntryWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String working = s.toString();
                isValid = true;
                if (working.length() == 2 && before == 0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                        isValid = false;
                    } else {
                        working += "/";
                        cardExpireMonth.setText(working);
                        cardExpireMonth.setSelection(working.length());


                    }
                } else if (working.length() == 5 && before == 0) {
                    String enteredYear = working.substring(3);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    currentYear = Integer.parseInt(String.valueOf(currentYear).substring(2));
                    if (Integer.parseInt(enteredYear) < currentYear) {
                        isValid = false;
                    }
                } else if (working.length() != 5) {
                    isValid = false;
                }

                if (!isValid) {
                    cardExpireMonth.setError("Enter a valid date: MM/YY");
                } else {
                    cardExpireMonth.setError(null);
                }
                et_expDate.setText(working);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        };
        cardExpireMonth.addTextChangedListener(mDateEntryWatcher);
        getcheckLocation();

    }
    private void getcheckLocation() {
        if (checkLocation()) {
            if (!Global.GpsEnable(getActivity())) {
                Global.showGPSDisabledAlertToUser(getActivity());
            } else {
                lastLocation();
            }
        } else Global.showSettingsDialog(getActivity());

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
            getLat = String.valueOf(lat);
            getlongs = String.valueOf(longs);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    private void lastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
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
    }    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("PAYMENT");
    }

    @OnClick({R.id.iv_back, R.id.paymentBtn})
    public void onBack(View view) {
        if (view.getId() == R.id.paymentBtn) {
            if (validate()) {
                String[] datesplit = expiryval.split("/");
                String monthval = datesplit[0].trim();
                String yearval = datesplit[1].trim();
                paynow(et_cardNumber.getText().toString(),Integer.parseInt(monthval),Integer.parseInt(yearval), cvv);

            }


        } else if (view.getId() == R.id.iv_back) {
            getActivity().onBackPressed();

        }
    }

    private boolean validate() {
        cardNumber=et_cardNumber.getText().toString();
        expiryval=cardExpireMonth.getText().toString();
        cvv=et_cvv.getText().toString();
        if(cardNumber.isEmpty()){
            Global.showToast(getActivity(),"Enter Card Number");
            return false;
        }else if(expiryval.isEmpty()){
            Global.showToast(getActivity(),"Enter month and year");
            return false;
        }if(cvv.isEmpty()){
            Global.showToast(getActivity(),"Enter Card CVV Number");
            return false;
        }
        return true;
    }

    private void paynow(String cardNumber, int expiremonth,int year, String cvcval) {

        progressDialog.dismiss();
        Card card = new Card(
                cardNumber,
                expiremonth,
                year,
                cvcval);
        card.setCurrency("USD");
        boolean validation = card.validateCard();
        if (validation) {
            new Stripe().createToken(card, PUBLISHABLE_KEY, new TokenCallback() {
                public void onSuccess(Token token) {
                    try {
                     //  com.stripe.Stripe.apiKey = "sk_live_eRkTaPVBQPQLSYMX6usfNcFA";
                        com.stripe.Stripe.apiKey = "sk_test_roDXMdY54wIDtVLwtI87klHD000vZKzZvl";
                        TokenIds = token.getId();
                        Map<String, Object> chargeParams = new HashMap<String, Object>();

                        Double usd = Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(totalValue)));

//                        chargeParams.put("amount", usd);
                        chargeParams.put("amount", (int) Math.round(usd * 100));
                        chargeParams.put("currency", "USD");
                        chargeParams.put("source", token.getId());
                        Map<String, String> initialMetadata = new HashMap<String, String>();
                        initialMetadata.put("order_id", "" + System.currentTimeMillis() / 1000);
                        chargeParams.put("metadata", initialMetadata);


                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);


                        try {
                            Charge.create(chargeParams);
                          //  hitproductApi(TokenIds);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.print("" + e.getMessage());
                            Global.msgDialog(getActivity(), e.getMessage());
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                public void onError(Exception error) {
                    handleError(error.getLocalizedMessage());
                }
            });

        } else if (!card.validateNumber()) {

            handleError("The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {

            handleError("The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {

            handleError("The CVC code that you entered is invalid");
        } else {

            handleError("The card details that you entered are invalid");
        }


    }
    private void handleError(String error) {
    //    DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
    //    fragment.show(getActivity().getSupportFragmentManager(), "error");
    }

//    private void hitproductApi(String tokenIds) {
//        List<CartModel> model = new ArrayList<>();
//        model.add(cartModel);
//        String totalAmount = totalValue;
//        String ShippingName = addressModel.getFirstName();
//        String ShippingAddress = addressModel.getAddress();
//        String ShippingCity = addressModel.getCity();
//        String ShippingState = addressModel.getState();
//        String ShippingZipcode = addressModel.getZipcode();
//        String ShippingPhone = addressModel.getMobile();
//
//
//        JSONArray jsonArray = new JSONArray();
//
//        for (int i = 0; i < model.size(); i++) {
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("productId", list_product.get(i).getProductId());
//                jsonObject.put("price", list_product.get(i).getProductPrice());
//                jsonObject.put("Quantity", list_product.get(i).getQuantity());
//                jsonArray.put(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        if (jsonArray.length() > 0) {
//            if (Global.isOnline(getActivity())) {
//                Log.e("Created Item : ", jsonArray.toString());
//                userViewModel.hitAddPurchase(userId, jsonArray.toString(), totalAmount, ShippingName, ShippingAddress, ShippingCity, ShippingState, ShippingZipcode
//                        , ShippingPhone, tokenIds, getLat,getlongs,this);
//            } else {
//                Global.showDialog(getActivity());
//            }
//        } else {
////                    viewModel.addInvoice(SessionManager.get_user_id(prefs),
////                            bookingId, adapter.getTotalPrice(models), jsonArray.toString(), CreateInvoiceActivity.this);
//            Global.showToast(getActivity(), "Please Enter Any Data");
//        }
//
//
//    }
//
//    @Override
//    public void onStarted() {
//        progressDialog.show();
//    }
//
//    @Override
//    public void onUpdateSuccess(LiveData<UserResponse> user) {
//        progressDialog.dismiss();
//        if (user.getValue().getStatus().equalsIgnoreCase("success")) {
//            SessionManager.save_cart_value(prefs, user.getValue().getTotalCartItem());
//            Global.showToast(getActivity(), "Payment SuccessFully");
//            Fragment paymentCart = new UserDashBoard();
//            Activity activity = getActivity();
//            if (activity instanceof UserMainActivity) {
//                ((UserMainActivity) activity).loadFragment(paymentCart, false);
//            } else {
//                replaceFragment(paymentCart, paymentCart.getClass().getSimpleName());
//            }
//        }
//
//    }
//
//    @Override
//    public void onSuccess(LiveData<ListResponse<OrderModel>> data) {
//        progressDialog.dismiss();
//        data.observe(getActivity(), new Observer<ListResponse<OrderModel>>() {
//            @Override
//            public void onChanged(ListResponse<OrderModel> orderModels) {
//                if (data.getValue().getList() != null && data.getValue().getList().size() > 0) {
//                    updateCartApi();
//
//                }
//            }
//        });
//
//    }

    private void updateCartApi() {
      //  userViewModel.hitCartUpdate(userId, this);

    }

    @SuppressLint("WrongConstant")
    public void replaceFragment(Fragment fragment, String backstack_name) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_container, fragment, backstack_name);
        fragmentTransaction.addToBackStack(backstack_name);
        fragmentTransaction.commit();
    }

//    @Override
//    public void onFailure(String message) {
//        progressDialog.dismiss();
//        Global.showToast(getActivity(), message);
//
//    }


    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }
}