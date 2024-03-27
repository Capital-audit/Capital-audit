package com.example.capitalaudit.button_classes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.capitalaudit.Async.PaymentApiAsyncTask;
import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.API.api_response;
import com.example.capitalaudit.R;
import com.example.capitalaudit.Utility.json_class;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


/**
 * Class to handle the add payment button
 * @params context The context to use for the request
 * @params api The API class to use for the request
 *
 */
public class Add_payment_button_class implements PaymentApiAsyncTask.onPaymentAddResult
{

    private Context context;
    private api_class api;
    public Add_payment_button_class(Context context, api_class api)
    {
        this.context = context;
        this.api = api;
    }


    /**
     * Method to add a transaction button
     * This function will add a listener to the button that will get the data inputted by the user and send it to the API
     * @param button
     */
    public void addTransactionButton(Button button)
    {
        if(context == null)
        {
            Log.d("TransactionButton", "Error context is null");
        }

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Getting all data inputted from user: Price, Category, Debit/credit, isCleared, and date.
                TextInputEditText editTextTransactionPrice = ((Activity) context).findViewById(R.id.editTransactionPricee);
                String transactionPrice = Objects.requireNonNull(editTextTransactionPrice.getText()).toString();
                int transactionPriceInt = passTransactionToInt(transactionPrice);

                Spinner category = ((Activity) context).findViewById(R.id.category);
                String selectedCategory = category.getSelectedItem().toString();

                Spinner spinnerDebitCredit = ((Activity) context).findViewById(R.id.debit_credit);
                String selectedDebitCredit = spinnerDebitCredit.getSelectedItem().toString();
                boolean debCred;
                debCred = selectedDebitCredit == "Debit card";

                CheckBox checkBox = ((Activity) context).findViewById(R.id.checkBox);
                boolean isCleared = checkBox.isChecked();

                EditText editTextDate = ((Activity) context).findViewById(R.id.editTextDate);
                String enteredDate = editTextDate.getText().toString();
                String json = json_class.payment_to_json(transactionPriceInt, selectedCategory, isCleared,debCred, enteredDate);
                Log.d("paymenttoJson", json);
                new PaymentApiAsyncTask(api, Add_payment_button_class.this, json).execute();
                }
        });
    }

    /**
     * Method to handle the result of the add payment request
     * @param result
     */
    @Override
    public void onPaymentAddResult(api_response result)
    {
        if (result.isSuccess())
        {

            Log.d("Test", "Success.. Added task");

        }
        else
        {
            // Error response
            Log.e("Test", "Failed to add task " + result.getResponse());
        }
    }

    /**
     * Method to convert the transaction price to an integer
     * @params transactionPriceString
     * @return
     */
    private int passTransactionToInt(String transactionPriceString)
    {
        int transactionPrice;
        try
        {
            return transactionPrice = Integer.parseInt(transactionPriceString);
        }
        catch (NumberFormatException e)
        {
            // Handle the case where the input is not a valid integer
            e.printStackTrace(); // Print the error for debugging purposes
            // Optionally, show a message to the user indicating that the input is invalid
            return -3000; // Exit the method or handle the error appropriately
        }
    }
}
