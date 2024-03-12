package com.example.capitalaudit.API;

import android.content.Context;
import android.util.Log;

public class FetchData implements fetchDataFromServerAsyncTask.FetchResult {
    private api_class api;
    Context context;
    public FetchData(api_class api, Context context)
    {
        this.api = api;
        this.context = context;
    }
    public void FetchDataFromServer()
    {
        Log.d("Test", "Fetched data!");
        new fetchDataFromServerAsyncTask(api, FetchData.this);
    }

    @Override
    public void FetchDataResult(api_response response)
    {
        Log.d("test", "fetch all data response: " + response);
    }

}



