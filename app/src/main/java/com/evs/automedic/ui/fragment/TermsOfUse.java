package com.evs.automedic.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProvider;


import com.evs.automedic.R;
import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.viewModels.AuthListener;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;

public class TermsOfUse extends Fragment implements AuthListener<MsgResponse> {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    private ACProgressFlower progress;
    private SharedPreferences prefs;
    private UserViewModel userViewModel;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_terms, container, false);
        ButterKnife.bind(this, view);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        progress = Global.getProgress(getActivity(), "Loading..");
      //  userViewModel.callTermsCondition(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("TERMS OF USE");
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess(LiveData<MsgResponse> data) {
        data.observe(this, new Observer<MsgResponse>() {
            @Override
            public void onChanged(MsgResponse msgResponse) {
                if (msgResponse.getStatus().equalsIgnoreCase("success")) {
                    tv_privacy.setText(Html.fromHtml(msgResponse.getMsg()));
                }
            }
        });

    }


    @Override
    public void onFailure(String message) {
        progress.dismiss();
        Global.showToast(getActivity(), message);
    }
}
