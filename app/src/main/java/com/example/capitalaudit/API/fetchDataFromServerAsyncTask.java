package com.example.capitalaudit.API;


import android.os.AsyncTask;
import android.util.Log;

import com.example.capitalaudit.models.payment_class;

import java.io.IOException;

public class fetchDataFromServerAsyncTask extends AsyncTask<payment_class, Void, api_response> {
    private api_class api;
    private FetchResult fetchResult;

    public fetchDataFromServerAsyncTask(api_class api, FetchResult fetchResult) {
        this.api = api;
        this.fetchResult = fetchResult;
    }

    @Override
    protected api_response doInBackground(payment_class... params) {
        try {
            Log.d("Test", "get data api function executed");
            return api.fetchDataFromServer();
        } catch (IOException e) {
            Log.e("Test", "Exception in payment_api", e);
            return new api_response(false, e.getMessage());
        }
    }

    public interface FetchResult{
        void FetchDataResult(api_response response);
    }

    @Override
    protected void onPostExecute(api_response result) {
        // Handle the result as needed
        fetchResult.FetchDataResult(result);
    }
}

