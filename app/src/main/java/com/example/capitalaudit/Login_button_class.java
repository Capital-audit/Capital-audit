package com.example.capitalaudit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_button_class implements LoginAsyncTask.LoginCallback {
    private Context context;
    private api_class api;

    Login_button_class(Context context, api_class api)
    {
        this.context = context;
        this.api = api;
    }


    public void login_button(Button button, Context login_context) {

        if(login_context == null)
        {
            Log.d("test", "No context");
            return;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) v.getRootView().findViewById(R.id.username_input)).getText().toString();
                CapitalAudit.setUsername(username);
                String password = ((EditText) v.getRootView().findViewById(R.id.password_input)).getText().toString();
                // Call the AsyncTask to perform login in the background
                new LoginAsyncTask(login_context, Login_button_class.this, api).execute(username, password);

            }
        });
    }

    @Override
    public void onLoginResult(api_response result) {
        if (result.isSuccess()) {
            Log.d("Test", "Success logged in");
            if (context == null)
            {
                Log.d("test", "OnLogginResult context null");
            }
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        } else {
            // Error response
            Log.e("Test", "Failed logged in: " + result.getResponse());
        }
    }
}
