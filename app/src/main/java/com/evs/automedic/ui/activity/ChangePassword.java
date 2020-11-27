package com.evs.automedic.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.evs.automedic.R;
import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.SessionManager;
import com.evs.automedic.viewModels.AuthListener;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassword extends Fragment implements AuthListener<MsgResponse> {

    @BindView(R.id.edttxt_password)
    EditText old_pass;
    @BindView(R.id.edttxt_new_password)
    EditText new_pass;
    @BindView(R.id.edttxt_conf_password)
    EditText conf_pass;
    private ProgressDialog progressDialog;
    private Button change_pass;
    private SharedPreferences prefs;
    private UserViewModel userViewModel;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_forgot_password, container, false);
        ButterKnife.bind(this, view);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        progressDialog = Global.getProgressDialog(getActivity(), "Please Wait...");
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("CHANGE PASSWORD");
    }
    public boolean changeMethod() {
        if (old_pass.getText().toString().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter Current Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (new_pass.getText().toString().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter new Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (conf_pass.getText().toString().length() < 1) {
            Toast.makeText(getActivity(), "Please Re-Enter new Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (old_pass.getText().toString().equals(new_pass.getText().toString())) {
            Toast.makeText(getActivity(), "Passwords Must Not Be Same As Current Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!conf_pass.getText().toString().equalsIgnoreCase(new_pass.getText().toString())) {
            Toast.makeText(getActivity(), "Passwords Must Be Same", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.btn_update)
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            // startActivity(new Intent(this, LoginScreen.class));
            if (Global.isOnline(getActivity())) {
                if (changeMethod()) {
                    userViewModel.callChagePassword(SessionManager.get_user_id(prefs), old_pass.getText().toString(), conf_pass.getText().toString(), this);

                }
            }
        }

    }


    @Override
    public void onStarted() {
        progressDialog.show();
    }

    @Override
    public void onSuccess(LiveData<MsgResponse> data) {
        progressDialog.dismiss();
        try {
            if (data.getValue().getStatus().equalsIgnoreCase("success")) {
                Global.msgDialog(getActivity(), data.getValue().getMsg());
                old_pass.setText("");
                new_pass.setText("");
                conf_pass.setText("");
                startActivity(new Intent(getActivity(), LoginScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            } else if (data.getValue().getStatus().equalsIgnoreCase("Fails")) {
                Global.msgDialog(getActivity(), data.getValue().getMsg());
            } else {
                Global.msgDialog(getActivity(), "Error in server");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String message) {
        progressDialog.dismiss();
        Global.msgDialog(getActivity(), message);
    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }
}