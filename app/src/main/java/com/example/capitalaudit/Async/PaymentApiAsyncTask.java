package com.example.capitalaudit.Async;


import android.os.AsyncTask;
import android.util.Log;

import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.API.api_response;
import com.example.capitalaudit.Utility.json_class;
import com.example.capitalaudit.models.payment_class;

import java.io.IOException;

/**
 * AsyncTask to handle payment requests
 * @params api The API class to use for the request
 * @params paymentCallBack The callback to trigger when the request is complete
 * @params jsonString The JSON string to send in the request
 * @return The result of the request
 * @see api_response
 */

public class PaymentApiAsyncTask extends AsyncTask<payment_class, Void, api_response>
{
    private api_class api;
    private onPaymentAddResult paymentCallBack;
    private String json;

    public PaymentApiAsyncTask(api_class api, onPaymentAddResult paymentCallBack, String jsonString)
    {
        this.api = api;
        this.paymentCallBack = paymentCallBack;
        this.json = jsonString;
    }

    @Override
    protected api_response doInBackground(payment_class... params)
    {
        Log.d("Test", "payment_api activated");
        try {
            Log.d("Test", "payment_api activated");
            return api.add_payment_api(json);
        }
        catch (IOException e)
        {
            Log.e("Test", "Exception in payment_api", e);
            return new api_response(false, e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(api_response result)
    {
        Log.d("Test", "payment_api activated");
        // Handle the result as needed
        paymentCallBack.onPaymentAddResult(result);
    }

    public interface onPaymentAddResult
    {
        void onPaymentAddResult(api_response response);
    }
}

