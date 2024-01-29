package com.example.capitalaudit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    CapitalAudit capitalAudit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapitalAudit capitalAudit = (CapitalAudit) getApplication();
        button_class buttonClass = capitalAudit.getButtonClass();
        buttonClass.refreshPayments();
        menuBarSetup();
    }

    private boolean menuBarSetup()
    {
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home) {

                return true;
            } else if (itemId == R.id.Graph) {
                startActivity(new Intent(HomeActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            } else if (itemId == R.id.New) {
                // Handle New click
                startActivity(new Intent(HomeActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.View) {
                // Handle View click
                startActivity(new Intent(HomeActivity.this, DatasetActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.Settings) {
                // Handle Settings click
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                finish();
                return true;
            }

            return false;

        });
        return false;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }
}