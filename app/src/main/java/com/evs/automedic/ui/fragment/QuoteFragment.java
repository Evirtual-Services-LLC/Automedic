package com.evs.automedic.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evs.automedic.R;
import com.evs.automedic.utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuoteFragment extends Fragment {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.edttxt_name)
    EditText edttxt_name;
     @BindView(R.id.edttxt_email)
    EditText edttxt_email;
     @BindView(R.id.edttxt_phone)
    EditText edttxt_phone;
    @BindView(R.id.edttxt_vechile_model)
    EditText edttxt_vechile_model;
    @BindView(R.id.edttxt_vechile_milegae)
    EditText edttxt_vechile_milegae;
    @BindView(R.id.edttxt_vechile_vin)
    EditText edttxt_vechile_vin;
    @BindView(R.id.edttxt_describe)
    EditText edttxt_describe;

    private String full_name, email, phone,vechile_year,vechile_mileage,vechile_vin,vechile_describe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quote_fragment, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
    }
    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("REQUEST A QUOTE");

    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }
    private boolean validation() {
        full_name = edttxt_name.getText().toString().trim();
        email = edttxt_email.getText().toString().trim();
        phone = edttxt_phone.getText().toString().trim();
        vechile_mileage = edttxt_vechile_milegae.getText().toString().trim();
        vechile_vin = edttxt_vechile_vin.getText().toString().trim();
        vechile_describe = edttxt_describe.getText().toString().trim();
        if (!Global.validatename(full_name)) {
            edttxt_name.setError(Html.fromHtml("<font color='red'>Enter Full Name</font>"));
            return false;
        }else if (!Global.validatename(email)) {
            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter E-mail Address</font>"));
            return false;
        }else if (!Global.validateEmail(email)) {
            edttxt_email.setError(Html.fromHtml("<font color='red'>Enter Correct E-mail Address</font>"));
            return false;
        }else if (!Global.validatename(phone)) {
            edttxt_phone.setError(Html.fromHtml("<font color='red'>Enter Mobile Number</font>"));
            return false;
        }else if (phone.length()<10) {
            edttxt_phone.setError(Html.fromHtml("<font color='red'>Enter Correct Mobile Number</font>"));
            return false;
        } else if (!Global.ValidEmail(vechile_mileage)) {
            edttxt_vechile_milegae.setError(Html.fromHtml("<font color='red'>Enter vechile mileage</font>"));
            return false;
        }else if (!Global.validatename(vechile_vin)) {
            edttxt_vechile_vin.setError(Html.fromHtml("<font color='red'>Enter vechile Vin Number</font>"));
            return false;
        }else if (!Global.validatename(vechile_describe)) {
            edttxt_describe.setError(Html.fromHtml("<font color='red'>Please Describe</font>"));
            return false;
        }

        return true;
    }
}
