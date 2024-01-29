package com.example.capitalaudit;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class RefreshPaymentsAsyncTask extends AsyncTask<payment_class, Void, api_response> {
    private api_class api;
    private refreshResult refreshCallBack;

    public RefreshPaymentsAsyncTask(api_class api, refreshResult refreshCallBack) {
        this.api = api;
        this.refreshCallBack = refreshCallBack;
    }

    @Override
    protected api_response doInBackground(payment_class... params) {
        try {
            Log.d("Test", "payment_api activated");
            api_class api = new api_class();


            return api.refresh_data();
        } catch (IOException e) {
            Log.e("Test", "Exception in payment_api", e);
            return new api_response(false, e.getMessage());
        }
    }

    public interface refreshResult{
        void refreshresult(api_response response);
    }

    @Override
    protected void onPostExecute(api_response result) {
        // Handle the result as needed
        refreshCallBack.refreshresult(result);
    }
}

