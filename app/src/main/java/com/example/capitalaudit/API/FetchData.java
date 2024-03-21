package com.example.capitalaudit.API;

import android.content.Context;
import android.util.Log;

import com.example.capitalaudit.Async.fetchDataFromServerAsyncTask;
import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.Listeners.DataFetchListener;
import com.example.capitalaudit.models.PaymentStorage;
import com.example.capitalaudit.models.payment_class;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.Vector;

/**
 * FetchData class is used to fetch data from the server.
 * It uses the fetchDataFromServerAsyncTask class to fetch data from the server.
 * Functions:
 * 1. FetchDataFromServer: This function is used to fetch data from the server.
 * 2. setListener: This function is used to set the DataFetchListener.
 * 3. FetchDataResult: This function is used to get the result of the data fetch from the server.
 */
public class FetchData implements fetchDataFromServerAsyncTask.FetchResult
{
    private api_class api;
    Context context;
    private DataFetchListener listener;

    /**
     * Constructor for FetchData class.
     * @param api
     * @param context
     */
    public FetchData(api_class api, Context context)
    {
        this.api = api;
        this.context = context;
    }

    /**
     * FetchDataFromServer function is used to fetch data from the server.
     * It uses the fetchDataFromServerAsyncTask class to fetch data from the server.
     * It also calls the onDataFetched function of the DataFetchListener.
     * @return Nothing.
     * @params Nothing.
     */
    public void FetchDataFromServer()
    {
        new fetchDataFromServerAsyncTask(api, FetchData.this, listener).execute();
        if(listener != null)
        {
            listener.onDataFetched();
        }
    }

    /**
     * setListener function is used to set the DataFetchListener.
     * @param listener
     */
    public void setListener(DataFetchListener listener) {
        this.listener = listener;
    }

    /**
     * FetchDataResult function is used to get the result of the data fetch from the server.
     * @param response
     * @return Nothing.
     */
    @Override
    public void FetchDataResult(api_response response)
    {
        Log.d("data request", response.getResponse());
        if (response.getResponse() != null && response.isSuccess())
        {

                Gson gson = new Gson();
                JsonObject jsonObject = JsonParser.parseString(response.getResponse()).getAsJsonObject();
                if (jsonObject.has("paymentDataList"))
                {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("paymentDataList");
                    payment_class[] paymentArray = gson.fromJson(jsonArray, payment_class[].class);

                    if (paymentArray != null)
                    {
                        CapitalAudit ca = CapitalAudit.getInstance();
                        PaymentStorage ps = ca.getStorage();
                        ps.clearVector();

                        Vector<payment_class> paymentClassVector = new Vector<>(Arrays.asList(paymentArray));

                        for (payment_class payment : paymentClassVector)
                        {
                            ps.addPaymentToList(payment);
                        }
                    }
                    else
                    {
                        Log.e("data request", "Error paymentVector null");
                    }
                }
        }
        else if (!response.isSuccess())
        {
            if(response.getResponse() != null)
            {
                Log.e("Fetch data result", response.getResponse());
            }
            else
            {
                Log.e("Fetch data result", "Unknown error");
            }
        }
        else
        {
            Log.e("Fetch data result", "Response from server was null");
        }

    }
}



