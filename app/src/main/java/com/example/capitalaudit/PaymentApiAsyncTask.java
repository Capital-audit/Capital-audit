package com.example.capitalaudit;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.capitalaudit.Util;
import com.example.capitalaudit.api_class.*;
import com.example.capitalaudit.api_response.*;
import com.example.capitalaudit.json_class.*;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class PaymentApiAsyncTask extends AsyncTask<payment_class, Void, api_response> {
    private api_class api;
    private onPaymentAddResult paymentCallBack;


    public PaymentApiAsyncTask(api_class api, onPaymentAddResult paymentCallBack) {
        this.api = api;
        this.paymentCallBack = paymentCallBack;
    }

    @Override
    protected api_response doInBackground(payment_class... params) {



        try {
            Log.d("Test", "payment_api activated");


            String jsonString = json_class.payement_to_json(params[0]);
            return api.add_payment_api(jsonString);
        } catch (IOException e) {
            Log.e("Test", "Exception in payment_api", e);
            return new api_response(false, e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(api_response result) {
        // Handle the result as needed
        paymentCallBack.onPaymentAddResult(result);
    }

    public interface onPaymentAddResult {
        void onPaymentAddResult(api_response response);
    }
}

