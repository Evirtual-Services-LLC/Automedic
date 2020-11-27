package com.evs.automedic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evs.automedic.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalenderFragment extends Fragment {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appiointment_fragment, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
    }
    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("CALENDER");

    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }
}
