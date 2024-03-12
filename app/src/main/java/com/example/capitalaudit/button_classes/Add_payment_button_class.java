package com.example.capitalaudit.button_classes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.capitalaudit.API.PaymentApiAsyncTask;
import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.API.api_response;
import com.example.capitalaudit.R;
import com.example.capitalaudit.Utility.json_class;
import com.example.capitalaudit.models.payment_class;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Add_payment_button_class implements PaymentApiAsyncTask.onPaymentAddResult{

    private Context context;
    private api_class api;
    public Add_payment_button_class(Context context, api_class api)
    {
        this.context = context;
        this.api = api;
    }


    public void addTransactionButton(Button button)
    {
        //Adding a new transaction needs updating. Need to get user input create new object.
        //transform to json and then send to db. Right now what works is:
        //Api_Class, json_Class, Async and this needs adjusting to get userinput and then convert
        //to Json.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting all data inputted from user: Price, Category, Debit/credit, isCleared, and date.
                TextInputEditText editTextTransactionPrice = ((Activity) context).findViewById(R.id.editTransactionPricee);
                String transactionPrice = Objects.requireNonNull(editTextTransactionPrice.getText()).toString();
                int transactionPriceInt = passTransactionToInt(transactionPrice);

                Spinner category = ((Activity) context).findViewById(R.id.category);
                String selectedCategory = category.getSelectedItem().toString();

                Spinner spinnerDebitCredit = ((Activity) context).findViewById(R.id.debit_credit);
                String selectedDebitCredit = spinnerDebitCredit.getSelectedItem().toString();

                CheckBox checkBox = ((Activity) context).findViewById(R.id.checkBox);
                boolean isCleared = checkBox.isChecked();

                EditText editTextDate = ((Activity) context).findViewById(R.id.editTextDate);
                String enteredDate = editTextDate.getText().toString();
                String json = json_class.payment_to_json(transactionPriceInt, selectedCategory, isCleared,selectedDebitCredit, enteredDate);

                new PaymentApiAsyncTask(api, Add_payment_button_class.this, json);
                }
        });
    }

    @Override
    public void onPaymentAddResult(api_response result) {
        if (result.isSuccess()) {

            Log.d("Test", "Success.. Added task");

        } else {
            // Error response
            Log.e("Test", "Failed to add task " + result.getResponse());
        }
    }

    private int passTransactionToInt(String transactionPriceString)
    {
        int transactionPrice;
        try {
            return transactionPrice = Integer.parseInt(transactionPriceString);
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            e.printStackTrace(); // Print the error for debugging purposes
            // Optionally, show a message to the user indicating that the input is invalid
            return -3000; // Exit the method or handle the error appropriately
        }
    }
}
