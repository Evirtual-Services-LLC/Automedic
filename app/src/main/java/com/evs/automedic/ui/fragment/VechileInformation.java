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

public class VechileInformation extends Fragment {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.edttxt_vechile_year)
    EditText edttxt_vechile_year;
    @BindView(R.id.edttxt_vechile_milegae)
    EditText edttxt_vechile_milegae;
    @BindView(R.id.edttxt_vechile_vin)
    EditText edttxt_vechile_vin;
    @BindView(R.id.edttxt_describe)
    EditText edttxt_describe;
    @BindView(R.id.spnr_service)
    Spinner spnr_service;
    private String vechile_year,vechile_mileage,vechile_vin,vechile_describe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vechile_fragment, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
    }
    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("VECHILE INFORMATION");

    }
    @OnClick({R.id.iv_back,R.id.btn_submit})
    public void onClick() {
        getActivity().onBackPressed();
    }
    private boolean validation() {
        vechile_year = edttxt_vechile_year.getText().toString().trim();
        vechile_mileage = edttxt_vechile_milegae.getText().toString().trim();
        vechile_vin = edttxt_vechile_vin.getText().toString().trim();
        vechile_describe = edttxt_describe.getText().toString().trim();
        if (!Global.validatename(vechile_year)) {
            edttxt_vechile_year.setError(Html.fromHtml("<font color='red'>Enter vechile year</font>"));
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
