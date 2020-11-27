package com.evs.automedic.ui.fragment;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.evs.automedic.R;
import com.evs.automedic.adapter.NotificationAdapter;
import com.evs.automedic.model.NotifcationModel;
import com.evs.automedic.repository.ListResponse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;


public class NotificationFragment extends Fragment  {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.rv_notification_list)
    RecyclerView rv_notification_list;
    private String text;
    NotificationAdapter adapter;
    ACProgressFlower progressDialog;
    UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        adapter = new NotificationAdapter(getActivity());
        Bundle bundle = getArguments();

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        progressDialog = Global.getProgress(getActivity(), "Please Wait...");
        getList();

//        userViewModel.getNotificationData().observe(getActivity(), new Observer<ArrayList<NotifcationModel>>() {
//            @Override
//            public void onChanged(ArrayList<NotifcationModel> notifcationModels) {
//                adapter.update(notifcationModels);
//            }
//        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rv_notification_list.setLayoutManager(manager);
        rv_notification_list.setAdapter(adapter);
    }

    private void getList() {
        if (Global.isOnline(getActivity())) {
       //     userViewModel.callNotificationList(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("NOTIFICATIONS");

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        getActivity().onBackPressed();
    }

//    @Override
//    public void onStarted() {
//        progressDialog.show();
//    }
//
//    @Override
//    public void onSuccess(LiveData<ListResponse<NotifcationModel>> data) {
//        progressDialog.dismiss();
//        data.observe(getActivity(), new Observer<ListResponse<NotifcationModel>>() {
//            @Override
//            public void onChanged(ListResponse<NotifcationModel> notifcationModelListResponse) {
//                if(data.getValue().getStatus().equalsIgnoreCase("success")) {
//                    adapter.update(data.getValue().getList());
//                }
//            }
//        });
//
//    }
//
//
//    @Override
//    public void onFailure(String message) {
//        progressDialog.dismiss();
//        Global.showToast(getActivity(), message);
//    }
}
