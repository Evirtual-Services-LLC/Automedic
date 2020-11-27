package com.evs.automedic.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.evs.automedic.R;
import com.evs.automedic.utils.Utills;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Welcome extends AppCompatActivity {
    SharedPreferences prefs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_toolbar)
    TextView mTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Utills.StatusBarColour(Welcome.this);
        mTitle.setText("GET STARTED NOW".toUpperCase());
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

        prefs = PreferenceManager.getDefaultSharedPreferences(Welcome.this);
    }

    @OnClick({R.id.btn_Login, R.id.btn_create_account})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_Login) {
            startActivity(new Intent(this, LoginScreen.class));
        } else if (view.getId() == R.id.btn_create_account) {
            startActivity(new Intent(this, RegisterScreen.class));

        }
    }
}
