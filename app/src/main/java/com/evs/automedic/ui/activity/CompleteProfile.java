package com.evs.automedic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.evs.automedic.R;
import com.evs.automedic.utils.Utills;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteProfile extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        ButterKnife.bind(this);
        intialize();
    }

    private void intialize() {
        Utills.StatusBarColour(CompleteProfile.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.text_toolbar);
        mTitle.setText("COMPLETE PROFILE".toUpperCase());
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
    }

    @OnClick({R.id.btn_save_vehicle_information,R.id.tv_add_vechile})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save_vehicle_information) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (view.getId() == R.id.tv_sign_in) {
            startActivity(new Intent(this, LoginScreen.class));
        }
    }
}