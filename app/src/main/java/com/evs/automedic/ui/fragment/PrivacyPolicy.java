package com.evs.automedic.ui.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
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
import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.viewModels.ParentAuthListner;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyPolicy extends Fragment implements ParentAuthListner {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    ProgressDialog progressDialog;
    UserViewModel userViewModel;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacy_policy, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        progressDialog = Global.getProgressDialog(getActivity(), "Please Wait...");
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (Global.isOnline(getActivity())) {
       //     userViewModel.callpolicyList(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("PRIVACY POLICY");
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onStarted() {
        progressDialog.show();
    }

    @Override
    public void onOrderSuccess(LiveData<MsgResponse> user) {
        progressDialog.dismiss();
        user.observe(getActivity(), new Observer<MsgResponse>() {
            @Override
            public void onChanged(MsgResponse msgResponse) {
                if (user.getValue().getStatus().equalsIgnoreCase("success")) {
                    tv_privacy.setText(Html.fromHtml(user.getValue().getMsg()));
                }
            }
        });
    }


    @Override
    public void onFailure(String message) {
        progressDialog.dismiss();
        Global.msgDialog(getActivity(), message);
    }
}
