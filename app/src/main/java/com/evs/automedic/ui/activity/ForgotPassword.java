package com.evs.automedic.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.evs.automedic.R;
import com.evs.automedic.model.MsgResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.Utills;
import com.evs.automedic.viewModels.AuthListener;
import com.evs.automedic.viewModels.UserViewModel;


public class ForgotPassword extends AppCompatActivity implements AuthListener<MsgResponse> {
    SharedPreferences prefs;
    UserViewModel userViewModel;
    ProgressDialog progressDialog;
    Button submit;
    EditText edttxt_email;
    String email_string;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        prefs = PreferenceManager.getDefaultSharedPreferences(ForgotPassword.this);
        progressDialog = Global.getProgressDialog(ForgotPassword.this, "Please wait..");
        userViewModel = ViewModelProviders.of(ForgotPassword.this).get(UserViewModel.class);
        Utills.StatusBarColour(ForgotPassword.this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.text_toolbar);
        mTitle.setText("FORGOT PASSWORD".toUpperCase());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edttxt_email = findViewById(R.id.edttxt_email);
        submit = findViewById(R.id.submit);
        backToLogin = findViewById(R.id.backToLogin);


        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_string = edttxt_email.getText().toString().trim();
                if (!Global.ValidEmail(email_string)) {
                    edttxt_email.setError(Html.fromHtml("<font color='red'>Enter correct E-mail Address</font>"));
                }/*else if (!Global.ValidEmail(edttxt_username.getText().toString())) {
                        edttxt_email.setError(Html.fromHtml("<font color='red'>Enter correct EmailId</font>"));
                }*/ else {
                    edttxt_email.setError(null);
                    if (Global.isOnline(ForgotPassword.this)) {
                     //   userViewModel.callForgotPassword(email_string, ForgotPassword.this);
                    } else {
                        Toast.makeText(ForgotPassword.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    @Override
    public void onStarted() {
        progressDialog.show();
    }

    @Override
    public void onSuccess(LiveData<MsgResponse> data) {
        data.observe(this, new Observer<MsgResponse>() {
            @Override
            public void onChanged(MsgResponse msgResponse) {
                progressDialog.dismiss();
                try {
                    if (msgResponse.getStatus().equalsIgnoreCase("success")) {
                        Global.msgDialog(ForgotPassword.this, msgResponse.getMsg());
                    } else if (msgResponse.getStatus().equalsIgnoreCase("Fails")) {
                        Global.msgDialog(ForgotPassword.this, msgResponse.getMsg());
                    } else {
                        Global.msgDialog(ForgotPassword.this, "Error in server");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onFailure(String message) {
        progressDialog.dismiss();
        Global.msgDialog(ForgotPassword.this, message);
    }
}
