package com.evs.automedic.ui.activity;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.evs.automedic.R;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.Utills;
import com.evs.automedic.viewModels.UserViewModel;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.cloudist.acplibrary.ACProgressFlower;

public class AppointmentActivity extends AppCompatActivity {
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.confirm_dateandTime)
    TextView confirm_dateandTime;
    SharedPreferences prefs;
    UserViewModel dataViewModel;
    ACProgressFlower progress;
    private CaldroidFragment caldroidFragment;
    int dayh;
    String DATE_FORMAT = "yyyy MM dd";
    SimpleDateFormat format;
    String selecteddate = "", showingdate = "", offDays = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appiointment);
        ButterKnife.bind(this);
        intialize();
    }

    private void intialize() {
        Utills.StatusBarColour(AppointmentActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.text_toolbar);
        mTitle.setText("APPOINTMENT DETAILS".toUpperCase());
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
        dataViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        progress = Global.getProgress(this, "Please wait...");
        caldroidFragment = new CaldroidFragment();
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarLayout, caldroidFragment);
        t.commit();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        caldroidFragment.setArguments(args);
        caldroidFragment.setMinDate(dt);

        String[] separatedhr = offDays.split(",");
        for (int i = 0; i < separatedhr.length; i++) {
            breakDays(separatedhr[i]);
        }

        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat showingDf = new SimpleDateFormat("MMM d, yyyy");

            @Override
            public void onSelectDate(Date date, View view) {
                selecteddate = df.format(date);
                showingdate = showingDf.format(date);
//
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDate(date);
                caldroidFragment.refreshView();
                confirm_dateandTime.setText("Selected  Date : " + showingdate);
               Global.showToast(AppointmentActivity.this, showingdate);
//                dataViewModel.getTimeSlot(userId, selecteddate, SelectDate_time.this);
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Global.showToast(AppointmentActivity.this, showingdate);

            }
        });
        if (Global.isOnline(this)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat showingDf = new SimpleDateFormat("MMM d, yyyy");
//            Calendar ca = Calendar.getInstance();
//            ca.setTime(dt);
////            ca.add(Calendar.DATE, 1);
//            dt = ca.getTime();
////            String date = df.format(dt);
            Date today = new Date();
            String date = df.format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));
            String ssss = showingDf.format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));
            selecteddate = date;
            showingdate = ssss;

//            confirm_dateandTime.setText("Selected Chore Date : "+selecteddate);
            confirm_dateandTime.setText("Selected  Date : " + ssss);

        } else Global.showDialog(this);


    }

    @OnClick({R.id.tv_time, R.id.confirm})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_time) {
            selectTime();
        } else if (view.getId() == R.id.confirm) {
            if (selecteddate.equalsIgnoreCase("")) {
                Toast.makeText(this, "Select Date First", Toast.LENGTH_SHORT).show();
            } else if (tv_time.getText().toString().equalsIgnoreCase("Select Time")) {
                Toast.makeText(this, "Select Time First", Toast.LENGTH_SHORT).show();
            } else {
//                   dataViewModel.calladdBooking(SessionManager.get_user_id(prefs),selectedServicesId,
//                           selecteddate,tv_time.getText().toString(),address, ""+latitude,
//                           ""+longitude,
//                            s_businessType, SelectDate_time.this);
            }
        }
    }

    public void breakDays(String day) {
        switch (day) {
            case "Sun":
                dayh = Calendar.SUNDAY;
                break;
            case "Mon":
                dayh = Calendar.MONDAY;
                break;
            case "Tue":
                dayh = Calendar.TUESDAY;
                break;
            case "Wed":
                dayh = Calendar.WEDNESDAY;
                break;
            case "Thu":
                dayh = Calendar.THURSDAY;
                break;
            case "Fri":
                dayh = Calendar.FRIDAY;
                break;
            case "Sat":
                dayh = Calendar.SATURDAY;
                break;
        }
        format = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance(); // today

        String y = sdf.format(c1.getTime());
        String years = y.substring(0, 4);
        int year = Integer.parseInt(years);
        //Connection con=null;
        Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
        for (int i = 0, inc = 1; i < 366 && cal.get(Calendar.YEAR) == year; i += inc) {
            if (cal.get(Calendar.DAY_OF_WEEK) == dayh) {
//                &&cal.getTime()>new Date()
                String frm = "";
                frm = format.format(cal.getTime());
                //System.out.println("From  :"+frm);
//                arrayList_day.add(format.format(cal.getTime()));
//                Date date=format.format(cal.getTime());
                caldroidFragment.setBackgroundDrawableForDate(getResources().getDrawable(R.color.orange), cal.getTime());

                cal.add(Calendar.DAY_OF_MONTH, 7);
            } else {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
//        System.out.println("the value of the "+day+" is " + arrayList_day);

    }

    private void selectTime() {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                lunchtime_end.setText(hourOfDay + ":" + minute+" "+"");
                        String AM_PM = " AM";
                        String mm_precede = "";
                        if (hourOfDay >= 12) {
                            AM_PM = " PM";
                            if (hourOfDay >= 13 && hourOfDay < 24) {
                                hourOfDay -= 12;
                            } else {
                                hourOfDay = 12;
                            }
                        } else if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }
                        if (minute < 10) {
                            mm_precede = "0";
                        }
                        tv_time.setText(hourOfDay + ":" + mm_precede + minute + " " + AM_PM);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    

}
}
