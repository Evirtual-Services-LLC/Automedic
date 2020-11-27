package com.evs.automedic.ui.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.evs.automedic.R;
import com.evs.automedic.utils.Global;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpScreen extends Fragment {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    ProgressDialog progressDialog;
    UserViewModel userViewModel;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_help, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        progressDialog = Global.getProgressDialog(getActivity(), "Please Wait...");
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (Global.isOnline(getActivity())) {
           // userViewModel.callHelpList(this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("HELP");
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }
//
//    @Override
//    public void onStarted() {
//        progressDialog.show();
//    }
//
//    @Override
//    public void onSuccess(LiveData<HelpResponse> data) {
//        progressDialog.dismiss();
//        data.observe(getActivity(), new Observer<HelpResponse>() {
//            @Override
//            public void onChanged(HelpResponse helpResponse) {
//                if (data.getValue().getStatus().equalsIgnoreCase("success")) {
//                    tv_email.setText(data.getValue().getModel().getEmail());
//                    tv_phone.setText(data.getValue().getModel().getPhone());
//                }
//            }
//        });
//    }
//
//
//    @Overrideiss();
//        Global.msgDialog(ge
//        public void onFailure(String message) {
//            progressDialog.dismtActivity(), message);
//    }
}
