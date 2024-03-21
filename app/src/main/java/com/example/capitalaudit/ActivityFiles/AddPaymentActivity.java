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

/**
AddPaymentActivity class is an activity, its used to add a new transaction to the database.
It has the following functions:
1. setUpNavBar() - Sets up the bottom navigation bar
2. CategorySpinner() - Creates a drop-down menu for the category of the transaction
3. debitCreditSpinner() - Creates a drop-down menu for the type of card used in the transaction
4. enterButton() - Adds the transaction to the database when the AddPayment button is clicked
 */

public class AddPaymentActivity extends AppCompatActivity
{


    /**
    onCreate() - This method is called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setUpNavBar();
        CategorySpinner();
        debitCreditSpinner();
        enterButton();
    }



    /**
    setUpNavBar() - Sets up the bottom navigation bar
     */
    private boolean setUpNavBar()
    {
        setContentView(R.layout.activity_add_payment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.New);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home)
            {
                // Handle Home click
                startActivity(new Intent(AddPaymentActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.Graph)
            {
                startActivity(new Intent(AddPaymentActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            } else if (itemId == R.id.New)
            {
                // Handle New click
                startActivity(new Intent(AddPaymentActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.View)
            {
                startActivity(new Intent(AddPaymentActivity.this, DatasetActivity.class));
            } else if (itemId == R.id.Settings)
            {
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



    /**
    startActivity() - Overrides the startActivity method to remove the transition animation
     * @param intent - The intent to start the activity
     */
    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }



    /**
    CategorySpinner() - Creates a drop-down menu for the category of the transaction
     */
    private void CategorySpinner()
    {
        String[] options = {"Bills", "Food", "Clothing", "Rent", "Miscellaneous"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.category);
        spinner.setAdapter(adapter);
    }



    /**
    debitCreditSpinner() - Creates a drop-down menu for the type of card used in the transaction
     */
    private void debitCreditSpinner()
    {
        String[] options = {"Debit card", "Credit card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.debit_credit);
        spinner.setAdapter(adapter);
    }



    /**
    enterButton() - Adds the transaction to the database when the AddPayment button is clicked.
    Will call the addTransactionButton method from the Add_payment_button_class.
    will pass the AddPaymentActivity and the api_class object as parameters.
     */
    private void enterButton()
    {
        CapitalAudit capitalAudit = CapitalAudit.getInstance();
        api_class api = capitalAudit.getApi();
        Add_payment_button_class paymentButtonClass = new Add_payment_button_class(AddPaymentActivity.this, api);
        Button addButton = findViewById(R.id.AddPayment);
        paymentButtonClass.addTransactionButton(addButton);

    }

}
