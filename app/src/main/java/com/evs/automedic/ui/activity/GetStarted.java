package com.evs.automedic.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.evs.automedic.R;
import com.evs.automedic.chatActivity.ChatActivity;
import com.evs.automedic.ui.fragment.AppointmentFragment;
import com.evs.automedic.ui.fragment.ChatFragment;
import com.evs.automedic.ui.fragment.QuoteFragment;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.Utills;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetStarted extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_toolbar)
    TextView mTitle;
    SharedPreferences prefs;
    @BindView(R.id.lin_book)
    LinearLayout lin_book;
    @BindView(R.id.lin_request_quote)
    LinearLayout lin_request_quote;
    @BindView(R.id.lin_sign_in)
    LinearLayout lin_sign_in;
    @BindView(R.id.lin_contact)
    LinearLayout lin_contact;
    @BindView(R.id.lin_chat)
    LinearLayout lin_chat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        Utills.StatusBarColour(GetStarted.this);
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

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        intialize();
    }

    private void intialize() {

    }

    @OnClick({R.id.lin_book, R.id.lin_request_quote, R.id.lin_sign_in,R.id.lin_contact,R.id.lin_chat})
    public void onClick(View view) {
        if (view.getId() == R.id.lin_book) {
            startActivity(new Intent(GetStarted.this, AppointmentActivity.class));
        } else if (view.getId() == R.id.lin_request_quote) {
            startActivity(new Intent(GetStarted.this, QuoteActivity.class));

        } else if (view.getId() == R.id.lin_sign_in) {
            startActivity(new Intent(GetStarted.this, Welcome.class));
        }else if (view.getId() == R.id.lin_contact) {
            startActivity(new Intent(GetStarted.this, ContactUsActivity.class));
        }else if (view.getId() == R.id.lin_chat) {
            startActivity(new Intent(GetStarted.this, ChatActivity.class));
        }
    }
}
