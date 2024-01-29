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
    private WeakReference<Context> contextRef;
    private LoginCallback loginCallback;

    public interface LoginCallback {
        void onLoginResult(api_response result);
    }
    public PaymentApiAsyncTask(Context context,LoginCallback callback) {
        contextRef = new WeakReference<>(context);
        loginCallback = callback;
    }

    @Override
    protected api_response doInBackground(payment_class... params) {
        Context context = contextRef.get();

        if (context == null) {
            return new api_response(false, "Context is null");
        }

        try {
            Log.d("Test", "payment_api activated");
            api_class api = new api_class();

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
    }
}

