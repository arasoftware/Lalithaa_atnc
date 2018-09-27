package arasoftwares.in.PayRoll.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import arasoftwares.in.PayRoll.R;

public class NotificationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_screen);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NotificationScreen.this,MainScreen.class));
        finish();
    }
}
