package com.example.capitalaudit.API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.capitalaudit.Utility.Util;
import com.example.capitalaudit.Utility.json_class;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class LoginAsyncTask extends AsyncTask<String, Void, api_response> {
    private WeakReference<Context> contextRef;
    private LoginCallback loginCallback;

    private api_class api;


    public interface LoginCallback {
        void onLoginResult(api_response result);

    }
    public LoginAsyncTask(Context context,LoginCallback callback, api_class api) {
        contextRef = new WeakReference<>(context);
        loginCallback = callback;
        this.api = api;

    }

    @Override
    protected api_response doInBackground(String... params) {
        Context context = contextRef.get();

        if (context == null) {
            // Context is no longer available, return an appropriate result
            return new api_response(false, "Context is null");
        }

        try {
            Log.d("Test", "login_button activated");
            String hashedUsr = Util.cred_hasher(params[0]);

            String hashedPwd = Util.cred_hasher(params[1]);
            String jsonString = json_class.login_to_json(hashedUsr, hashedPwd);
            return api.login_request(jsonString);
        } catch (IOException e) {
            Log.e("Test", "Exception in login_button", e);
            return new api_response(false, e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(api_response result) {
        // Handle the result as needed
        loginCallback.onLoginResult(result);
    }
}
