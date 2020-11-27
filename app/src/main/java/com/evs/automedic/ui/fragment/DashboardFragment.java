package com.evs.automedic.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.evs.automedic.R;
import com.evs.automedic.ui.activity.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardFragment extends Fragment {
    @BindView(R.id.textDashboardOne)
    TextView textDashboardOne;
    @BindView(R.id.textDashboardTwo)
    TextView textDashboardTwo;
    @BindView(R.id.textDashboardThree)
    TextView textDashboardThree;
    @BindView(R.id.textDashboardFour)
    TextView textDashboardFour;
    @BindView(R.id.textDashboardFive)
    TextView textDashboardFive;
    @BindView(R.id.textDashboardSix)
    TextView textDashboardSix;
    @BindView(R.id.textDashboardSeven)
    TextView textDashboardSeven;
    @BindView(R.id.textDashboardEight)
    TextView textDashboardEight;
    @BindView(R.id.dashboardImageOne)
    ImageView dashboardImageOne;
    @BindView(R.id.dashboardImageTwo)
    ImageView dashboardImageTwo;
    @BindView(R.id.dashboardImageThree)
    ImageView dashboardImageThree;
    @BindView(R.id.dashboardImageFour)
    ImageView dashboardImageFour;
    @BindView(R.id.dashboardImageFive)
    ImageView dashboardImageFive;
    @BindView(R.id.dashboardImageSix)
    ImageView dashboardImageSix;
    @BindView(R.id.dashboardImageSeven)
    ImageView dashboardImageSeven;
    @BindView(R.id.dashboardImageEight)
    ImageView dashboardImageEight;
    LinearLayout layoutHomeOne, layoutHomeTwo, layoutHomeThree, layoutHomeFour, layoutHomeFive, layoutHomeSix, layoutHomeSeven;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_new, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }
    private void intialize() {
        textDashboardOne.setText("Book an\nAppointment");
        textDashboardTwo.setText("Request a Quote");
        textDashboardThree.setText("Client\nHistory");
        textDashboardFour.setText("Chat");
        textDashboardFive.setText("Payment");
        textDashboardSix.setText("Calender");
        textDashboardSeven.setText("Notes");
        textDashboardEight.setText("Client/Vechile\nInformation");
    }

    @OnClick({R.id.layoutHomeOne, R.id.layoutHomeTwo, R.id.layoutHomethree, R.id.layoutHomeFour, R.id.layoutHomeFive, R.id.layoutHomeSix, R.id.layoutHomeSeven, R.id.layoutHomeEight})
    public void Onclick(View view) {
        if (view.getId() == R.id.layoutHomeOne) {
            Fragment appointmentFragment = new AppointmentFragment();
            goNext(appointmentFragment);
        } else if (view.getId() == R.id.layoutHomeTwo) {
            Fragment quoteFragment = new QuoteFragment();
            goNext(quoteFragment);
        } else if (view.getId() == R.id.layoutHomethree) {
            Fragment cleintHistoryFragment = new CleintHistoryFragment();
            goNext(cleintHistoryFragment);
        } else if (view.getId() == R.id.layoutHomeFour) {
            Fragment chatFragment = new ChatFragment();
            goNext(chatFragment);
        } else if (view.getId() == R.id.layoutHomeFive) {
            Fragment paymentFragment = new PaymentList();
            goNext(paymentFragment);
        } else if (view.getId() == R.id.layoutHomeSix) {
            Fragment calenderFragment = new CalenderFragment();
            goNext(calenderFragment);
        } else if (view.getId() == R.id.layoutHomeSeven) {
            Fragment notesFragment = new EsignFragment();
            goNext(notesFragment);
        } else if (view.getId() == R.id.layoutHomeEight) {
            Fragment vechileInformation = new VechileInformation();
            goNext(vechileInformation);
        }
    }

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
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("DASHBOARD");
    }
}
