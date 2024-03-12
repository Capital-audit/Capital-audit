package com.example.capitalaudit.ActivityFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.capitalaudit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpNavBar();
    }


    private boolean setUpNavBar()
    {
        setContentView(R.layout.activity_settings);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home) {
                // Handle Home click
                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.Graph) {
                startActivity(new Intent(SettingsActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            } else if (itemId == R.id.New) {
                // Handle New click
                startActivity(new Intent(SettingsActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.View) {
                return true;
            } else if (itemId == R.id.Settings) {
                // Handle Settings click
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                finish();
                return true;
            } else
            {
                return false;
            }
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
