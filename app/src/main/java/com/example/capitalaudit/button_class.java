package com.example.capitalaudit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class button_class implements LoginAsyncTask.LoginCallback, PaymentApiAsyncTask.onPaymentAddResult, RefreshPaymentsAsyncTask.refreshResult {
    private static button_class instance;
    private api_class api;
    Context context;
    PaymentStorage paymentStorage;
    public button_class()
    {
        api = new api_class();
        setApi(api);
        new PaymentStorage();
    }

    public api_class getApi()
    {
        return api;
    }

    public void setApi(api_class api)
    {
        this.api = api;
    }
    public static void login_button(Button button, Context context) {
        if(instance == null){
            instance = new button_class();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) v.getRootView().findViewById(R.id.username_input)).getText().toString();
                String password = ((EditText) v.getRootView().findViewById(R.id.password_input)).getText().toString();

                // Call the AsyncTask to perform login in the background
                new LoginAsyncTask(context, instance).execute(username, password);

            }
        });
    }

    @Override
    public void onLoginResult(api_response result) {
        if (result.isSuccess()) {

            Log.d("Test", "Success logged in");
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        } else {
            // Error response
            Log.e("Test", "Failed logged in: " + result.getResponse());
        }
    }


    public static boolean addTransactionButton(Button button, Context  context)
    {
        //Adding a new transaction needs updating. Need to get user input create new object.
        //transform to json and then send to db. Right now what works is:
        //Api_Class, json_Class, Async and this needs adjusting to get userinput and then convert
        //to Json.
        if(instance == null){
            instance = new button_class();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PaymentApiAsyncTask(getInstance().api, instance);
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




    public static button_class getInstance()
    {
        return instance;
    }


    public void refreshPayments()
    {
        new RefreshPaymentsAsyncTask(getInstance().api, instance).execute();
    }

    @Override
    public void refreshresult(api_response response)
    {
        //Need to go through all JSON and create objects out of response.
        //Then its good to go.
    }

}



