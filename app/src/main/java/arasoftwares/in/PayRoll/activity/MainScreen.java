package arasoftwares.in.PayRoll.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.Utils.Utils;

import static arasoftwares.in.PayRoll.Utils.Constants.MODE;
import static arasoftwares.in.PayRoll.Utils.Constants.PREF;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Kumar_Main_Screen";
    private RelativeLayout container;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigation;
    private CardView cardAttendance, cardNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        initializeViews();
    }

    private void initializeViews() {

        container = (RelativeLayout) findViewById(R.id.mainScreen_layout);
        if (!Utils.isNetworkAvailable(this)) {
            Utils.showSnack(container, "network not available");
        }
        cardAttendance = (CardView) findViewById(R.id.attendance_onclick);
        cardNotification = (CardView) findViewById(R.id.notification_onclick);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainScreen.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        cardAttendance.setOnClickListener(this);
        cardNotification.setOnClickListener(this);
        initInstancesOfNavBar();
        if (!isStoragePermissionGranted()) {
            Log.e(TAG, "Permission Denied");
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission is granted");
                return true;
            } else {
                Log.e(TAG, "Permission is revoked");
                Utils.showToast(this, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.e(TAG, "Permission is granted");
            return true;
        }
    }


    private void initInstancesOfNavBar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.attendance:
                        startActivity(new Intent(MainScreen.this, AttendanceScreen.class));
                        finish();
                        break;
                    case R.id.action_logout_id:
                        logout();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navmenu, menu);
        return true;
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF, MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Utils.showToast(this, "Logout Succesfully");
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout_id:
                logout();
                break;
            case R.id.attendance:
                startActivity(new Intent(MainScreen.this, AttendanceScreen.class));
                finish();
                break;
            case R.id.notification:
                startActivity(new Intent(MainScreen.this, NotificationScreen.class));
                finish();
                break;
            default:
                break;
        }
        return drawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attendance_onclick:
                startActivity(new Intent(MainScreen.this, AttendanceScreen.class));
                finish();
                break;
            case R.id.notification_onclick:
                startActivity(new Intent(MainScreen.this, NotificationScreen.class));
                finish();
                break;
            default:
                break;
        }
    }
}
