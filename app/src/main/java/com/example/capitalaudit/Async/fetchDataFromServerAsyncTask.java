package com.example.capitalaudit.Async;


import android.os.AsyncTask;
import android.util.Log;

import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.API.api_response;
import com.example.capitalaudit.Listeners.DataFetchListener;
import com.example.capitalaudit.models.payment_class;

import java.io.IOException;

/**
 * This class is used to fetch data from the server asynchronously.
 * It will call the Api_class.fetchDataFromServer() method in the background and return the result.
 * It will also notify the listener when the data has been fetched.
 */
public class fetchDataFromServerAsyncTask extends AsyncTask<payment_class, Void, api_response>
{
    private api_class api;
    private FetchResult fetchResult;

    private DataFetchListener listener;
    public fetchDataFromServerAsyncTask(api_class api, FetchResult fetchResult, DataFetchListener listner)
    {
        this.api = api;
        this.fetchResult = fetchResult;
        this.listener = listner;
    }

    @Override
    protected api_response doInBackground(payment_class... params)
    {
        try
        {
            return api.fetchDataFromServer();

        }
        catch (IOException e)
        {
            return new api_response(false, e.getMessage());
        }
    }

    private void notifyDataFetched()
    {
        // Notify the listener that data has been fetched
        if (listener != null)
        {
            listener.onDataFetched();
        }
    }

    public interface FetchResult
    {
        void FetchDataResult(api_response response);
    }

    @Override
    protected void onPostExecute(api_response result)
    {
        // Handle the result as needed
        fetchResult.FetchDataResult(result);
        notifyDataFetched();
    }
}

