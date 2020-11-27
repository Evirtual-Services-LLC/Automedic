package com.evs.automedic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evs.automedic.R;
import com.evs.automedic.adapter.NotificationAdapter;
import com.evs.automedic.adapter.PaymentListAdapter;
import com.evs.automedic.utils.Global;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;

public class PaymentList extends Fragment {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.rv_payment_list)
    RecyclerView rv_payment_list;
    private String text;
    PaymentListAdapter adapter;
    ACProgressFlower progressDialog;
    UserViewModel userViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_payment_list, container, false);
        ButterKnife.bind(this, view);
        intilaize(view);

        return view;
    }

    private void intilaize(View view) {
        adapter = new PaymentListAdapter(getActivity());
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        progressDialog = Global.getProgress(getActivity(), "Please Wait...");
        getList();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rv_payment_list.setLayoutManager(manager);
        rv_payment_list.setAdapter(adapter);
    }
    private void getList() {
        if (Global.isOnline(getActivity())) {
        //    userViewModel.callNotificationList(this);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("PAYMENT DETAILS");

    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }

}
