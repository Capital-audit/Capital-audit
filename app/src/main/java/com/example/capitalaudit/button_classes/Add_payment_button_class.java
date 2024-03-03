package com.example.capitalaudit.button_classes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.capitalaudit.API.PaymentApiAsyncTask;
import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.API.api_response;

public class Add_payment_button_class implements PaymentApiAsyncTask.onPaymentAddResult{

    private Context context;
    private api_class api;
    Add_payment_button_class(Context context, api_class api)
    {
        this.context = context;
        this.api = api;
    }


    public boolean addTransactionButton(Button button, Context context)
    {
        //Adding a new transaction needs updating. Need to get user input create new object.
        //transform to json and then send to db. Right now what works is:
        //Api_Class, json_Class, Async and this needs adjusting to get userinput and then convert
        //to Json.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PaymentApiAsyncTask(api, Add_payment_button_class.this);
            }
        });
        return false;
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
}
