package com.evs.automedic.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import com.evs.automedic.R;
import com.evs.automedic.adapter.NavigationListAdapter;
import com.evs.automedic.ui.fragment.CalenderFragment;
import com.evs.automedic.ui.fragment.ChatFragment;
import com.evs.automedic.ui.fragment.DashboardFragment;
import com.evs.automedic.ui.fragment.EditProfile;
import com.evs.automedic.ui.fragment.HelpScreen;
import com.evs.automedic.ui.fragment.NotesFragment;
import com.evs.automedic.ui.fragment.NotificationFragment;
import com.evs.automedic.ui.fragment.PaymentList;
import com.evs.automedic.ui.fragment.PrivacyPolicy;
import com.evs.automedic.ui.fragment.QuoteFragment;
import com.evs.automedic.ui.fragment.TermsOfUse;
import com.evs.automedic.utils.Global;
import com.evs.automedic.utils.SessionManager;
import com.evs.automedic.utils.Utills;
import com.evs.automedic.viewModels.UserViewModel;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mainToolbar;
    FragmentManager manager;
    ListView list_nav;
    NavigationListAdapter list_adapter;
    ActionBarDrawerToggle toggle;
    SharedPreferences prefs;
    //   ProgressDialog progress;
    TextView edit_profile;
    TextView txt_username, txt_userphone;
    UserViewModel userViewModel;
    private ProgressDialog progressDialog;
    RoundedImageView userImg;
    String username_Str, userphone_Str;
    private String currentFragName;
    @BindView(R.id.iv_notifcation)
    ImageView iv_notifcation;
    @BindView(R.id.cric91_bnv_tab)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        progressDialog = Global.getProgressDialog(MainActivity.this, "Please wait...");
        Utills.StatusBarColour(MainActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userViewModel = ViewModelProviders.of(MainActivity.this).get(UserViewModel.class);
        txt_username = (TextView) findViewById(R.id.txt_username);
        iv_notifcation.setVisibility(View.VISIBLE);
        userImg = findViewById(R.id.userImg);
        loadFragment(new DashboardFragment(), false);
//        queue = Volley.newRequestQueue(MainActivity.this);

        /*progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Loading");
        progress.setCancelable(true);*/

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        System.out.println("User Name == " + SessionManager.get_name(prefs) + " Contact Number == " + SessionManager.get_mobile(prefs) + ", And ID == " + SessionManager.get_user_id(prefs));

        list_nav = (ListView) findViewById(R.id.list_nav);
        nevigationlist();

        list_nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updatedisplay(position);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DashboardFragment()).commit();
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                SessionManager.save_device_token(prefs, newToken);
//                Log.e("newToken", newToken);
//
//            }
//        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment;
                    fragment = new DashboardFragment();
                    loadFragment(fragment, true);

                    break;
                case R.id.navigation_payment:
                    fragment = new PaymentList();
                    loadMainFragment(fragment, true);

                    break;
                case R.id.navigation_calender:
                    fragment = new CalenderFragment();
                    loadMainFragment(fragment, true);
                    break;
                case R.id.navigation_quote:
                    fragment = new QuoteFragment();
                    loadMainFragment(fragment, true);
                    break;
                case R.id.navigation_chat:
                    fragment = new ChatFragment();
                    loadMainFragment(fragment, true);
                    break;
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        username_Str = SessionManager.get_name(prefs);
        txt_username.setText(username_Str);
        //   userphone_Str = SessionManager.get_mobile(prefs);
        System.out.println("User Name == " + SessionManager.get_name(prefs) + " Contact Number == " + SessionManager.get_mobile(prefs));
        Glide.with(MainActivity.this).load(SessionManager.get_image(prefs)).placeholder(R.drawable.user).into(userImg);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int backStackSize = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backStackSize == 1) {
            showBottomDialog();
        } else {
            if (backStackSize == 2) {
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                mainToolbar.setVisibility(View.VISIBLE);
            }
            super.onBackPressed();
        }
    }

    private void showBottomDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("");
        alertDialog.setMessage("Do you really want to Exit?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }


    public void updatedisplay(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        switch (position) {
            case 0:
                fragment = new DashboardFragment();
                loadFragment(fragment, true);
                break;
            case 1:
                fragment = new EditProfile();
                loadMainFragment(fragment, true);
                break;
            case 2:
                fragment = new ChangePassword();
                loadMainFragment(fragment, true);
                break;

            case 3:
                fragment = new HelpScreen();
                loadMainFragment(fragment, true);
                break;
            case 4:
                fragment = new PrivacyPolicy();
                loadMainFragment(fragment, true);
                break;
            case 5:
                fragment = new TermsOfUse();
                loadMainFragment(fragment, true);
                break;
            case 6:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("");
                alertDialog.setMessage("Do you really want to Sign out?");
                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                logout();

                                boolean b = SessionManager.get_remember(prefs);
                                String s = SessionManager.get_emailid(prefs);
                                SessionManager.dataclear(prefs);
                                SessionManager.save_check_login(prefs, false);
                                SessionManager.save_check_agreement(prefs, true);
                                SessionManager.save_remember(prefs, b);
                                SessionManager.save_emailid(prefs, s);
                                Intent intent = new Intent(MainActivity.this, Welcome.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                                //       userViewModel.logoutUser(SessionManager.get_user_id(prefs), MainActivity.this);

                            }
                        });
                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();
                break;
        }
        if (fragment != null) {
            manager.beginTransaction().replace(R.id.frame_container, fragment)
                    .addToBackStack(null).commit();
        }
    }

    private void nevigationlist() {
        LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
        data.put(getResources().getString(R.string.home), R.drawable.home);
        data.put(getResources().getString(R.string.EditProfile), R.drawable.ic_edit);
        data.put(getResources().getString(R.string.changePassword), R.drawable.lock);
        data.put(getResources().getString(R.string.help), R.drawable.help);
        data.put(getResources().getString(R.string.privacy_policy), R.drawable.ic_privacy);
        data.put(getResources().getString(R.string.terms_of_use), R.drawable.ic_terms);
        data.put(getResources().getString(R.string.logout), R.drawable.logout);
        list_adapter = new NavigationListAdapter(MainActivity.this, data);
        list_nav.setAdapter(list_adapter);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView customToolbarTitleTxt = (TextView) findViewById(R.id.text_toolbar);
        customToolbarTitleTxt.setText(title);
    }

    public void loadFragment(@NonNull Fragment fragment, boolean clearTillHome) {
        String fragmentName = fragment.getClass().getSimpleName();
        currentFragName = fragmentName;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isFragmentInBackStack(fragmentManager, fragment.getClass().getSimpleName())) {
            // Fragment exists, go back to that fragment
            fragmentManager.popBackStack(fragment.getClass().getSimpleName(), 0);
        } else {
            if (clearTillHome)
                fragmentManager.popBackStack(MainActivity.class.getSimpleName(), 0);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment, fragmentName)
                    .addToBackStack(fragmentName)
                    .commit();
        }
        mainToolbar.setVisibility(View.VISIBLE);
    }

    public void loadMainFragment(@NonNull Fragment fragment, boolean clearTillHome) {
        String fragmentName = fragment.getClass().getSimpleName();
        currentFragName = fragmentName;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isFragmentInBackStack(fragmentManager, fragment.getClass().getSimpleName())) {
            // Fragment exists, go back to that fragment
            fragmentManager.popBackStack(fragment.getClass().getSimpleName(), 0);
        } else {
            if (clearTillHome)
                fragmentManager.popBackStack(MainActivity.class.getSimpleName(), 0);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment, fragmentName)
                    .addToBackStack(fragmentName)
                    .commit();
        }
        mainToolbar.setVisibility(View.GONE);
    }

    public static boolean isFragmentInBackStack(final FragmentManager fragmentManager, final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    public void replaceFragment(Fragment fragment, String backstack_name) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_container, fragment, backstack_name);
        fragmentTransaction.addToBackStack(backstack_name);
        fragmentTransaction.commit();
    }
    @OnClick(R.id.iv_notifcation)
    public void onClick(){
        Fragment fragment = new NotificationFragment();
        loadMainFragment(fragment, true);
    }
}
