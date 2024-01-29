package com.example.capitalaudit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.capitalaudit.api_response.*;

public class button_class implements LoginAsyncTask.LoginCallback {
    private static button_class instance;
    Context context;
    public button_class(Context context)
    {
        this.context = context;
    }
    public static void login_button(Button button, Context context) {
        if(instance == null){
            instance = new button_class(context);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) v.getRootView().findViewById(R.id.username_input)).getText().toString();
                String password = ((EditText) v.getRootView().findViewById(R.id.password_input)).getText().toString();

                // Call the AsyncTask to perform login in the background
                new LoginAsyncTask(context, instance).execute(username, password);

            }
        });
    }

    @Override
    public void onLoginResult(api_response result) {
        // Handle the login result here
        if (result.isSuccess()) {

            Log.d("Test", "Success logged in");
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        } else {
            // Error response
            Log.e("Test", "Failed logged in: " + result.getResponse());
        }
    }


    public static boolean addTransactionButton(Button button, Context  context)
    {
        if(instance == null){
            instance = new button_class(context);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Call the AsyncTask to perform login in the background
                new RefreshPaymentsAsyncTask(instance.context).execute();
            }
        });
        return false;
    }

    public static button_class getInstance() {
        return instance;
    }
}

