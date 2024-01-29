package com.example.capitalaudit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapitalAudit capitalAudit = (CapitalAudit) getApplication();
        button_class buttonClass = capitalAudit.getButtonClass();
        buttonClass.refreshPayments();

        setUpNavBar();
    }




    private boolean setUpNavBar()
    {
        setContentView(R.layout.activity_add_payment);
        setContentView(R.layout.activity_dataset);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.New);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home) {
                // Handle Home click
                startActivity(new Intent(AddPaymentActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.Graph) {
                startActivity(new Intent(AddPaymentActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            } else if (itemId == R.id.New) {
                // Handle New click
                startActivity(new Intent(AddPaymentActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.View) {
                return true;
            } else if (itemId == R.id.Settings) {
                // Handle Settings click
                startActivity(new Intent(AddPaymentActivity.this, SettingsActivity.class));
                finish();
                return true;
            }

            return false;

        });
        overridePendingTransition(0, 0);
        return false;
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
