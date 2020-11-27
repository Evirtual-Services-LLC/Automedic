package com.evs.automedic.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evs.automedic.R;
import com.evs.automedic.adapter.HistoryAdapter;
import com.evs.automedic.adapter.NotificationAdapter;
import com.evs.automedic.ui.activity.MainActivity;
import com.evs.automedic.utils.Global;
import com.evs.automedic.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;

public class CleintHistoryFragment extends Fragment {
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    private String text;
    HistoryAdapter adapter;
    ACProgressFlower progressDialog;
    UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        adapter = new HistoryAdapter(getActivity());
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
        rv_history.setLayoutManager(manager);
        rv_history.setAdapter(adapter);
    }
    private void getList() {
        if (Global.isOnline(getActivity())) {
            //     userViewModel.callNotificationList(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        text_toolbar.setText("BILL HISTORY");

    }

    @OnClick({R.id.iv_back,R.id.btn_pay_now})
    public void onClick(View view) {
        if(view.getId()==R.id.iv_back){
            getActivity().onBackPressed();
        }else if(view.getId()==R.id.btn_pay_now){
            Fragment vechileInformation = new PaymentFrag();
            goNext(vechileInformation);
        }

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
@SuppressLint("WrongConstant")
public void replaceFragment(Fragment fragment, String backstack_name) {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(R.id.frame_container, fragment, backstack_name);
    fragmentTransaction.addToBackStack(backstack_name);
    fragmentTransaction.commit();
}

    public void goNext(Fragment fragment) {
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).loadMainFragment(fragment, true);
        } else {
            replaceFragment(fragment, fragment.getClass().getSimpleName());
        }
    }
}
