package com.example.capitalaudit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    CapitalAudit capitalAudit;
    api_class api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAudit();
        FetchData();
        menuBarSetup();
        TextView displayTextView = findViewById(R.id.displayTextView);
        displayTextView.setText(welcomeNameColor());


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
    private void FetchData()
    {
        FetchData fetchData = new FetchData(api,  HomeActivity.this);
        fetchData.FetchDataFromServer();
    }

    private void setUpAudit()
    {
        capitalAudit = CapitalAudit.getInstance();
        api = capitalAudit.getApi();
    }

    private SpannableString welcomeNameColor()
    {
        String username = CapitalAudit.getUsername();

        SpannableString spannableString = new SpannableString("Welcome " + username);

        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4BB73A")), 7, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}