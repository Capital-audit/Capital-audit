package com.example.capitalaudit.ActivityFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.R;
import com.example.capitalaudit.button_classes.Add_payment_button_class;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.List;

public class AddPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpNavBar();
        CategorySpinner();
        debitCreditSpinner();
        enterButton();

    }
    private boolean setUpNavBar()
    {
        setContentView(R.layout.activity_add_payment);
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

    private void CategorySpinner()
    {
        String[] options = {"Bills", "Food", "Clothing", "Rent", "Miscellaneous"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.category);
        spinner.setAdapter(adapter);

    }

    private void debitCreditSpinner()
    {
        String[] options = {"Debit card", "Credit card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.debit_credit);
        spinner.setAdapter(adapter);
    }


    private void enterButton()
    {
        CapitalAudit capitalAudit = CapitalAudit.getInstance();
        api_class api = capitalAudit.getApi();
        Add_payment_button_class paymentButtonClass = new Add_payment_button_class(AddPaymentActivity.this, api);
        Button addButton = findViewById(R.id.AddPayment);
        paymentButtonClass.addTransactionButton(addButton);

    }

}
