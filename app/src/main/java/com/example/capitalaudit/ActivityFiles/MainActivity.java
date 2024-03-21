package com.example.capitalaudit.ActivityFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.button_classes.Login_button_class;
import com.example.capitalaudit.R;
import com.example.capitalaudit.API.api_class;

/**
 * MainActivity class is the first activity that is launched when the app is opened.
 * It contains the login button which is used to login to the app.
 * It uses the CapitalAudit class to get the instance of the API class.
 * It uses the Login_button_class to set the onClickListener for the login button.
 * It uses the api_class to get the instance of the API class.
 * It extends AppCompatActivity class.
 * @return Nothing.
 */
public class MainActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CapitalAudit capitalAudit = CapitalAudit.getInstance();
        api_class api = capitalAudit.getApi();

        Login_button_class loginButtonClass = new Login_button_class(MainActivity.this, api);
        Button loginButton = findViewById(R.id.login_btn);
        loginButtonClass.login_button(loginButton, MainActivity.this);

    }
}