package com.example.capitalaudit.button_classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capitalaudit.Async.LoginAsyncTask;
import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.API.api_response;
import com.example.capitalaudit.ActivityFiles.HomeActivity;
import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.R;

/**
 * This class is used to handle the login button click event.
 * It will call the AsyncTask to perform login in the background.
 * It will also handle the result of the login.
 * If the login is successful, it will start the HomeActivity.
 * If the login is unsuccessful, it will log the error.
 * @params context The context of the activity that the login button is in.
 *                This is used to start the HomeActivity.
 * @params api The api_class object that is used to make the API calls.
 *
 */
public class Login_button_class implements LoginAsyncTask.LoginCallback
{
    private Context context;
    private api_class api;

    public Login_button_class(Context context, api_class api)
    {
        this.context = context;
        this.api = api;
    }


    /**
     * This function is used to handle the login button click event.
     * @param button
     * @param login_context
     */
    public void login_button(Button button, Context login_context)
    {

        if(login_context == null)
        {
            Log.d("test", "No context");
            return;
        }

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = ((EditText) v.getRootView().findViewById(R.id.username_input)).getText().toString();
                CapitalAudit.setUsername(username);
                String password = ((EditText) v.getRootView().findViewById(R.id.password_input)).getText().toString();
                // Call the AsyncTask to perform login in the background
                new LoginAsyncTask(login_context, Login_button_class.this, api).execute(username, password);

            }
        });
    }

    /**
     * This function is called when the AsyncTask is finished.
     * It will start the HomeActivity if the login is successful.
     * It is a callback function for the AsyncTask.
     * @param result
     */
    @Override
    public void onLoginResult(api_response result)
    {
        if (result.isSuccess())
        {
            Log.d("Test", "Success logged in");
            if (context == null)
            {
                Log.d("test", "OnLogginResult context null");
            }
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
        else
        {
            // Error response
            Log.e("Test", "Failed logged in: " + result.getResponse());
        }
    }
}
