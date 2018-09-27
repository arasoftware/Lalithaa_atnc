package arasoftwares.in.PayRoll.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.fragment.DeliveryFragment;
import arasoftwares.in.PayRoll.fragment.FragmentAssigningWork;
import arasoftwares.in.PayRoll.fragment.FragmentCheckIn;

public class AttendanceScreen extends AppCompatActivity {
    private static final String TAG = "AttendanceScreen";
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        initializeViews();
    }

    private void initializeViews() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        fragment = new FragmentCheckIn();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FragmentCheckIn();
                        break;
                    case 1:
                        fragment = new FragmentAssigningWork();
                        break;
                    case 2:
                        fragment = new DeliveryFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AttendanceScreen.this, MainScreen.class));
        finish();
        super.onBackPressed();
    }
}
